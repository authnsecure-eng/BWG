-- Safety-net migration: creates the bwg_survey table from scratch when it
-- does not already exist.
--
-- Why this is needed: no migration in this project has ever actually
-- created bwg_survey. On the current production database this was never a
-- problem because that database already had the table (created outside of
-- Flyway, before Flyway was introduced here) and Flyway's
-- "baseline-on-migrate" setting quietly accepted that pre-existing schema
-- as the starting point. But on a brand-new, completely empty database
-- (e.g. a fresh install on a new server) Flyway has nothing to baseline
-- against, so it actually tries to run every migration from V1 onward -
-- and V2 (add_bwg_survey_indexes) immediately fails with
-- "relation bwg_survey does not exist", because nothing before it ever
-- created the table.
--
-- This migration is versioned 1.1 (i.e. it runs right after V1 and before
-- V2) so that a fresh database gets the table in time for V2's indexes to
-- succeed. It is deliberately a no-op (CREATE TABLE IF NOT EXISTS) on any
-- database - like the current production one - where bwg_survey already
-- exists, so it cannot affect or duplicate anything there.
--
-- Because its version number (1.1) is lower than migrations already
-- applied on the existing production database (up to V9), Flyway's
-- "out-of-order" setting must be enabled (see application.yml) for this
-- migration to be picked up and run there too - safely, since it is a
-- pure no-op in that case.
--
-- The zone_id -> zones(id) foreign key is added separately in
-- V3_1__add_bwg_survey_zone_fk.sql, once the zones table exists (it is
-- created later, in V3).

CREATE TABLE IF NOT EXISTS bwg_survey (
    id                                  BIGSERIAL PRIMARY KEY,
    application_no                      VARCHAR(30)   NOT NULL,
    category                            VARCHAR(30)   NOT NULL,
    status                              VARCHAR(30)   NOT NULL,
    contact_person_name                 VARCHAR(150)  NOT NULL,
    designation                         VARCHAR(100)  NOT NULL,
    full_address                        VARCHAR(255)  NOT NULL,
    mobile_no                           VARCHAR(15)   NOT NULL,

    acceptance_information_correct      BOOLEAN,
    acceptance_inspection_consent       BOOLEAN,
    acceptance_swm_rules                BOOLEAN,
    agency_document_photo_path          VARCHAR(500),
    bin_infrastructure                  VARCHAR(20),
    biogas_by_product_usage             VARCHAR(255),
    biogas_capacity                     VARCHAR(50),
    biogas_capacity_unit                VARCHAR(50),
    biogas_operational_status           VARCHAR(50),
    biogas_photo_path                   VARCHAR(500),
    biogas_remarks                      TEXT,
    biogas_space_available_sq_m         VARCHAR(50),
    biomedical_waste_kg_day             NUMERIC(12,2),
    building_permission_doc_path        VARCHAR(500),
    building_permission_ref_no          VARCHAR(100),
    building_remarks                    TEXT,
    built_up_area_sq_m                  NUMERIC(12,2),
    chs_reg_no                          VARCHAR(100),
    construction_waste_kg_day           NUMERIC(12,2),
    cpcb_ack_number                     VARCHAR(100),
    cpcb_completed                      BOOLEAN,
    cpcb_submission_date                VARCHAR(50),
    created_at                          TIMESTAMPTZ,
    declarant_designation               VARCHAR(100),
    declarant_name                      VARCHAR(150),
    declarant_organization              VARCHAR(255),
    declaration_date                    DATE,
    declaration_form_path               VARCHAR(255),
    declaration_place                   VARCHAR(150),
    dry_waste_channelized_to            VARCHAR(100),
    dry_waste_kg_day                    NUMERIC(12,2),
    e_waste_kg_month                    NUMERIC(12,2),
    ebwgr_certificate_no                VARCHAR(100),
    ebwgr_certificate_photo_path        VARCHAR(255),
    ebwgr_required                      BOOLEAN,
    ebwgr_valid_until                   DATE,
    electoral_ward_name                 VARCHAR(100),
    eligibility_floor_area              BOOLEAN,
    eligibility_solid_waste             BOOLEAN,
    eligibility_water_consumption       BOOLEAN,
    email                               VARCHAR(255),
    establishment_name                  VARCHAR(255),
    establishment_type                  VARCHAR(150),
    factory_registration_no             VARCHAR(100),
    garden_horticulture_waste_kg_day    NUMERIC(12,2),
    geofence_latitude                   VARCHAR(50),
    geofence_longitude                  VARCHAR(50),
    geofence_photo_path                 VARCHAR(500),
    geofence_radius_meters              VARCHAR(50),
    gstin                                VARCHAR(100),
    has_biogas_plant                    BOOLEAN,
    industry_name                       VARCHAR(255),
    industry_type                       VARCHAR(150),
    institution_type                    VARCHAR(150),
    latitude                            NUMERIC(10,7),
    location_address                    VARCHAR(255),
    longitude                           NUMERIC(10,7),
    mobile_survey_id                    VARCHAR(50),
    mobile_total_floors                 VARCHAR(20),
    mobile_total_units                  VARCHAR(20),
    mou_validity                        VARCHAR(100),
    municipal_water_connection          BOOLEAN,
    no_of_buildings                     INTEGER,
    no_of_floors                        INTEGER,
    no_of_water_meters                  INTEGER,
    onsite_processing_available         BOOLEAN,
    organization_name                   VARCHAR(255),
    overall_disposal_mode                VARCHAR(100),
    pin_code                            VARCHAR(10),
    plot_area_sq_m                      NUMERIC(12,2),
    premises_photo_path                 VARCHAR(255),
    private_vendor_details              VARCHAR(500),
    processing_actual_kg_day            NUMERIC(12,2),
    processing_capacity_kg_day          NUMERIC(12,2),
    processing_destination              VARCHAR(255),
    processing_facility_photo_path      VARCHAR(255),
    processing_method                   VARCHAR(100),
    product_end_usage                   VARCHAR(100),
    ptin                                 VARCHAR(100),
    registration_cin_no                 VARCHAR(100),
    sanitary_waste_kg_day               NUMERIC(12,2),
    signage_photo_path                  VARCHAR(500),
    site_condition                      VARCHAR(20),
    site_overall_photo_path             VARCHAR(255),
    society_name                        VARCHAR(255),
    sub_category_type                   VARCHAR(150),
    submitted_at                        TIMESTAMPTZ,
    survey_officer_id                   BIGINT,
    survey_officer_name                 VARCHAR(150),
    survey_remarks                      VARCHAR(255),
    surveyor_gps                        VARCHAR(255),
    surveyor_name                       VARCHAR(150),
    surveyor_selfie_path                VARCHAR(500),
    surveyor_timestamp                  VARCHAR(100),
    surveyor_verified                   BOOLEAN,
    total_waste_kg_day                  NUMERIC(12,2),
    trade_license_no                    VARCHAR(100),
    updated_at                          TIMESTAMPTZ,
    vendor_name                         VARCHAR(255),
    ward                                 VARCHAR(50),
    waste_given_to_other_agency         VARCHAR(255),
    waste_handling_photo_path           VARCHAR(255),
    waste_segregated_at_source          VARCHAR(20),
    waste_storage_photo_path            VARCHAR(255),
    water_bill_doc_path                 VARCHAR(500),
    water_billing_period                VARCHAR(100),
    water_consumer_no                   VARCHAR(100),
    water_consumption_lpd               NUMERIC(12,2),
    water_meter_photo_path              VARCHAR(255),
    water_units_consumed                VARCHAR(100),
    wet_waste_kg_day                    NUMERIC(12,2),
    year_established                    INTEGER,
    zone_id                             BIGINT,
    zone_name_snapshot                  VARCHAR(50),

    CONSTRAINT uq_bwg_survey_application_no UNIQUE (application_no),
    CONSTRAINT bwg_survey_status_check CHECK (status IN ('SUBMITTED', 'COMPLETED', 'REJECTED'))
);
