-- Adds the columns and child table needed to receive survey submissions
-- coming from the mobile app backend (see SurveyIngestController). The
-- mobile app's survey form has several fields/groups that bwg_survey never
-- had a home for (biogas plant, geofencing, surveyor self-verification,
-- CPCB filing, day-wise waste visits, etc). Everything here is additive and
-- nullable/defaulted so existing rows and existing code are unaffected.

ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS mobile_survey_id VARCHAR(50);
CREATE UNIQUE INDEX IF NOT EXISTS uq_bwg_survey_mobile_survey_id ON bwg_survey(mobile_survey_id) WHERE mobile_survey_id IS NOT NULL;

ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS sub_category_type VARCHAR(150);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS society_name VARCHAR(255);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS chs_reg_no VARCHAR(100);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS ptin VARCHAR(100);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS gstin VARCHAR(100);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS mobile_total_floors VARCHAR(20);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS mobile_total_units VARCHAR(20);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS building_remarks TEXT;
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS building_permission_ref_no VARCHAR(100);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS building_permission_doc_path VARCHAR(500);

ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS water_consumer_no VARCHAR(100);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS water_billing_period VARCHAR(100);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS water_units_consumed VARCHAR(100);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS water_bill_doc_path VARCHAR(500);

ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS overall_disposal_mode VARCHAR(100);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS vendor_name VARCHAR(255);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS mou_validity VARCHAR(100);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS processing_destination VARCHAR(255);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS private_vendor_details VARCHAR(500);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS waste_given_to_other_agency VARCHAR(255);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS agency_document_photo_path VARCHAR(500);

ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS has_biogas_plant BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS biogas_capacity VARCHAR(50);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS biogas_capacity_unit VARCHAR(50);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS biogas_space_available_sq_m VARCHAR(50);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS biogas_by_product_usage VARCHAR(255);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS biogas_operational_status VARCHAR(50);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS biogas_photo_path VARCHAR(500);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS biogas_remarks TEXT;

ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS geofence_latitude VARCHAR(50);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS geofence_longitude VARCHAR(50);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS geofence_radius_meters VARCHAR(50);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS geofence_photo_path VARCHAR(500);

ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS signage_photo_path VARCHAR(500);

ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS surveyor_name VARCHAR(150);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS surveyor_selfie_path VARCHAR(500);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS surveyor_gps VARCHAR(255);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS surveyor_timestamp VARCHAR(100);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS surveyor_verified BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS cpcb_completed BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS cpcb_ack_number VARCHAR(100);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS cpcb_submission_date VARCHAR(50);

ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS electoral_ward_name VARCHAR(100);

-- Day-wise waste weighment, mirroring the mobile app's survey_waste_visit
-- table, so that daily detail isn't collapsed into just the aggregate
-- *_kg_day columns above.
CREATE TABLE IF NOT EXISTS bwg_survey_waste_visit (
    id                          BIGSERIAL PRIMARY KEY,
    bwg_survey_id               BIGINT NOT NULL REFERENCES bwg_survey(id) ON DELETE CASCADE,
    day_number                  INTEGER NOT NULL,
    visit_date                  VARCHAR(50),
    wet_waste_kg                NUMERIC(12,2) NOT NULL DEFAULT 0,
    dry_waste_kg                NUMERIC(12,2) NOT NULL DEFAULT 0,
    garden_waste_kg             NUMERIC(12,2) NOT NULL DEFAULT 0,
    total_waste_kg              NUMERIC(12,2) NOT NULL DEFAULT 0,
    wet_waste_photo_path        VARCHAR(500),
    dry_waste_photo_path        VARCHAR(500),
    garden_waste_photo_path     VARCHAR(500),
    weighing_scale_photo_path   VARCHAR(500),
    handover_area_photo_path    VARCHAR(500),
    is_completed                BOOLEAN NOT NULL DEFAULT FALSE,
    created_at                  TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT uq_bwg_survey_waste_visit_survey_day UNIQUE (bwg_survey_id, day_number)
);
CREATE INDEX IF NOT EXISTS idx_bwg_survey_waste_visit_survey_id ON bwg_survey_waste_visit(bwg_survey_id);
