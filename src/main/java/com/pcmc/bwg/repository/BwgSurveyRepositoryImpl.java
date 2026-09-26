package com.pcmc.bwg.repository;

import com.pcmc.bwg.dto.dashboard.LabelCount;
import com.pcmc.bwg.dto.dashboard.WasteSummary;
import com.pcmc.bwg.dto.dashboard.ZoneWardRow;
import com.pcmc.bwg.dto.report.AdminReportResponse;
import com.pcmc.bwg.entity.BwgSurvey;
import com.pcmc.bwg.entity.Zone;
import com.pcmc.bwg.entity.enums.SurveyStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Repository
public class BwgSurveyRepositoryImpl implements BwgSurveyRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<AdminReportResponse> search(SurveyFilter filter, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<AdminReportResponse> query = cb.createQuery(AdminReportResponse.class);
        Root<BwgSurvey> root = query.from(BwgSurvey.class);
        query.select(buildProjection(cb, root))
                .where(buildPredicates(cb, root, filter))
                .orderBy(cb.desc(root.get("submittedAt")));

        TypedQuery<AdminReportResponse> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<AdminReportResponse> content = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<BwgSurvey> countRoot = countQuery.from(BwgSurvey.class);
        countQuery.select(cb.count(countRoot)).where(buildPredicates(cb, countRoot, filter));
        long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<AdminReportResponse> searchForExport(SurveyFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AdminReportResponse> query = cb.createQuery(AdminReportResponse.class);
        Root<BwgSurvey> root = query.from(BwgSurvey.class);
        query.select(buildProjection(cb, root))
                .where(buildPredicates(cb, root, filter))
                .orderBy(cb.desc(root.get("submittedAt")));
        return entityManager.createQuery(query).getResultList();
    }

    private Selection<AdminReportResponse> buildProjection(CriteriaBuilder cb, Root<BwgSurvey> root) {
        Expression<String> industryName = root.get("industryName");
        Expression<String> organizationName = root.get("organizationName");
        Expression<String> establishmentName = root.get("establishmentName");
        Expression<String> bwgName = cb.coalesce(cb.nullif(industryName, ""),
                cb.coalesce(cb.nullif(organizationName, ""), cb.nullif(establishmentName, "")));

        return cb.construct(AdminReportResponse.class,
                root.get("id"), root.get("applicationNo"), bwgName, root.get("contactPersonName"),
                root.get("mobileNo"), zoneNameExpression(cb, root), root.get("ward"), root.get("category"),
                root.get("status"), root.get("submittedAt"));
    }

    /**
     * Always prefers the zone's current name from the zones table (live JOIN) so
     * that renaming a zone in Zone Master is reflected immediately everywhere.
     * Falls back to the historical zone_name_snapshot text only for legacy rows
     * that have no zone_id match (e.g. not yet backfilled, or the zone was deleted).
     */
    private Expression<String> zoneNameExpression(CriteriaBuilder cb, Root<BwgSurvey> root) {
        Join<BwgSurvey, Zone> zoneJoin = getOrCreateZoneJoin(root);
        return cb.coalesce(zoneJoin.get("name"), root.get("zoneNameSnapshot"));
    }

    // Projection and predicates are built against the same Root within a single
    // query (e.g. search()); reuse the existing join instead of adding a second
    // redundant LEFT JOIN to zones.
    @SuppressWarnings("unchecked")
    private Join<BwgSurvey, Zone> getOrCreateZoneJoin(Root<BwgSurvey> root) {
        for (Join<BwgSurvey, ?> join : root.getJoins()) {
            if ("zoneRef".equals(join.getAttribute().getName())) {
                return (Join<BwgSurvey, Zone>) join;
            }
        }
        return root.join("zoneRef", JoinType.LEFT);
    }

    @Override
    public Map<SurveyStatus, Long> countByStatus(SurveyFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<BwgSurvey> root = query.from(BwgSurvey.class);
        query.multiselect(root.get("status"), cb.count(root))
                .where(buildPredicates(cb, root, filter))
                .groupBy(root.get("status"));

        Map<SurveyStatus, Long> result = new EnumMap<>(SurveyStatus.class);
        for (Object[] row : entityManager.createQuery(query).getResultList()) {
            result.put((SurveyStatus) row[0], (Long) row[1]);
        }
        return result;
    }

    @Override
    public List<LabelCount> countGroupByCategory(SurveyFilter filter) {
        return groupByStringField(filter, "category");
    }

    @Override
    public List<LabelCount> countGroupByZone(SurveyFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<LabelCount> query = cb.createQuery(LabelCount.class);
        Root<BwgSurvey> root = query.from(BwgSurvey.class);
        Expression<String> zoneName = zoneNameExpression(cb, root);

        List<Predicate> predicates = new ArrayList<>(List.of(buildPredicates(cb, root, filter)));
        predicates.add(cb.isNotNull(zoneName));

        query.select(cb.construct(LabelCount.class, zoneName, cb.count(root)))
                .where(predicates.toArray(new Predicate[0]))
                .groupBy(zoneName)
                .orderBy(cb.desc(cb.count(root)));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<LabelCount> countGroupByWard(SurveyFilter filter) {
        return groupByStringField(filter, "ward");
    }

    private List<LabelCount> groupByStringField(SurveyFilter filter, String fieldName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<LabelCount> query = cb.createQuery(LabelCount.class);
        Root<BwgSurvey> root = query.from(BwgSurvey.class);

        List<Predicate> predicates = new ArrayList<>(List.of(buildPredicates(cb, root, filter)));
        predicates.add(cb.isNotNull(root.get(fieldName)));

        query.select(cb.construct(LabelCount.class, root.get(fieldName), cb.count(root)))
                .where(predicates.toArray(new Predicate[0]))
                .groupBy(root.get(fieldName))
                .orderBy(cb.desc(cb.count(root)));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<ZoneWardRow> zoneWardBreakdown(SurveyFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ZoneWardRow> query = cb.createQuery(ZoneWardRow.class);
        Root<BwgSurvey> root = query.from(BwgSurvey.class);
        Expression<String> zoneName = zoneNameExpression(cb, root);

        List<Predicate> predicates = new ArrayList<>(List.of(buildPredicates(cb, root, filter)));
        predicates.add(cb.isNotNull(zoneName));

        query.select(cb.construct(ZoneWardRow.class, zoneName, cb.countDistinct(root.get("ward")), cb.count(root)))
                .where(predicates.toArray(new Predicate[0]))
                .groupBy(zoneName)
                .orderBy(cb.desc(cb.count(root)));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public WasteSummary wasteSummary(SurveyFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<BwgSurvey> root = query.from(BwgSurvey.class);

        Expression<BigDecimal> wetSum = cb.sum(root.get("wetWasteKgDay"));
        Expression<BigDecimal> drySum = cb.sum(root.get("dryWasteKgDay"));
        Expression<BigDecimal> sanitarySum = cb.sum(root.get("sanitaryWasteKgDay"));
        Expression<BigDecimal> eWasteSum = cb.sum(root.get("eWasteKgMonth"));

        query.multiselect(wetSum, drySum, sanitarySum, eWasteSum)
                .where(buildPredicates(cb, root, filter));

        Object[] row = entityManager.createQuery(query).getSingleResult();
        return new WasteSummary((BigDecimal) row[0], (BigDecimal) row[1], (BigDecimal) row[2], (BigDecimal) row[3]);
    }

    private Predicate[] buildPredicates(CriteriaBuilder cb, Root<BwgSurvey> root, SurveyFilter filter) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(filter.search())) {
            String term = "%" + filter.search().toLowerCase() + "%";
            predicates.add(cb.or(
                    cb.like(cb.lower(root.get("applicationNo")), term),
                    cb.like(cb.lower(root.get("industryName")), term),
                    cb.like(cb.lower(root.get("organizationName")), term),
                    cb.like(cb.lower(root.get("establishmentName")), term),
                    cb.like(cb.lower(root.get("contactPersonName")), term),
                    cb.like(cb.lower(root.get("mobileNo")), term)
            ));
        }
        if (StringUtils.hasText(filter.zone())) {
            predicates.add(cb.equal(zoneNameExpression(cb, root), filter.zone()));
        }
        if (StringUtils.hasText(filter.ward())) {
            predicates.add(cb.equal(root.get("ward"), filter.ward()));
        }
        if (StringUtils.hasText(filter.category())) {
            predicates.add(cb.equal(root.get("category"), filter.category()));
        }
        if (filter.status() != null) {
            predicates.add(cb.equal(root.get("status"), filter.status()));
        }
        if (filter.fromDateTime() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("submittedAt"), filter.fromDateTime()));
        }
        if (filter.toDateTime() != null) {
            predicates.add(cb.lessThan(root.get("submittedAt"), filter.toDateTime()));
        }

        return predicates.toArray(new Predicate[0]);
    }
}
