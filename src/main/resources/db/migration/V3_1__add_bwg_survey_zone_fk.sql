-- Adds the bwg_survey.zone_id -> zones(id) foreign key on a database where
-- bwg_survey was just created by V1_1 (a brand-new, empty database).
--
-- This has to be its own migration, versioned to run after V3 (which is
-- where the zones table is created), because V1_1 runs right after V1 -
-- before zones exists - and so could not add the foreign key itself.
--
-- On the existing production database this is a no-op: bwg_survey already
-- existed before Flyway was introduced there, and V5 (add_bwg_survey_zone_id)
-- already added this same foreign key relationship when it ran for real.
-- The guard below checks for any existing foreign key on this column before
-- adding one, so it never creates a duplicate constraint.

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints tc
        JOIN information_schema.key_column_usage kcu
          ON tc.constraint_name = kcu.constraint_name
         AND tc.table_schema = kcu.table_schema
        WHERE tc.table_schema = 'public'
          AND tc.table_name = 'bwg_survey'
          AND tc.constraint_type = 'FOREIGN KEY'
          AND kcu.column_name = 'zone_id'
    ) AND EXISTS (
        SELECT 1 FROM information_schema.tables
        WHERE table_schema = 'public' AND table_name = 'zones'
    ) THEN
        ALTER TABLE public.bwg_survey
            ADD CONSTRAINT fk_bwg_survey_zone_id FOREIGN KEY (zone_id) REFERENCES public.zones(id) ON DELETE SET NULL;
    END IF;
END $$;
