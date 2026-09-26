-- Master data tables for the admin Masters module (Role, Location/Jurisdiction,
-- Organizational, BWG Classification, Waste & Infrastructure masters, plus
-- Officer and Electoral-Ward-wise User Mapping).

-- ---------------------------------------------------------------------------
-- Flat name+status lookup masters
-- ---------------------------------------------------------------------------

CREATE TABLE role_masters (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(150)  NOT NULL,
    status      VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE',
    created_at  TIMESTAMP     NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP     NOT NULL DEFAULT now(),
    CONSTRAINT uq_role_masters_name UNIQUE (name)
);
CREATE INDEX idx_role_masters_status ON role_masters(status);

CREATE TABLE zones (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(150)  NOT NULL,
    status      VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE',
    created_at  TIMESTAMP     NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP     NOT NULL DEFAULT now(),
    CONSTRAINT uq_zones_name UNIQUE (name)
);
CREATE INDEX idx_zones_status ON zones(status);

CREATE TABLE departments (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(150)  NOT NULL,
    status      VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE',
    created_at  TIMESTAMP     NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP     NOT NULL DEFAULT now(),
    CONSTRAINT uq_departments_name UNIQUE (name)
);
CREATE INDEX idx_departments_status ON departments(status);

CREATE TABLE designations (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(150)  NOT NULL,
    status      VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE',
    created_at  TIMESTAMP     NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP     NOT NULL DEFAULT now(),
    CONSTRAINT uq_designations_name UNIQUE (name)
);
CREATE INDEX idx_designations_status ON designations(status);

CREATE TABLE bwg_categories (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(150)  NOT NULL,
    status      VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE',
    created_at  TIMESTAMP     NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP     NOT NULL DEFAULT now(),
    CONSTRAINT uq_bwg_categories_name UNIQUE (name)
);
CREATE INDEX idx_bwg_categories_status ON bwg_categories(status);

CREATE TABLE property_types (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(150)  NOT NULL,
    status      VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE',
    created_at  TIMESTAMP     NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP     NOT NULL DEFAULT now(),
    CONSTRAINT uq_property_types_name UNIQUE (name)
);
CREATE INDEX idx_property_types_status ON property_types(status);

CREATE TABLE ownership_types (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(150)  NOT NULL,
    status      VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE',
    created_at  TIMESTAMP     NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP     NOT NULL DEFAULT now(),
    CONSTRAINT uq_ownership_types_name UNIQUE (name)
);
CREATE INDEX idx_ownership_types_status ON ownership_types(status);

CREATE TABLE unit_types (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(150)  NOT NULL,
    status      VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE',
    created_at  TIMESTAMP     NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP     NOT NULL DEFAULT now(),
    CONSTRAINT uq_unit_types_name UNIQUE (name)
);
CREATE INDEX idx_unit_types_status ON unit_types(status);

CREATE TABLE waste_types (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(150)  NOT NULL,
    status      VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE',
    created_at  TIMESTAMP     NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP     NOT NULL DEFAULT now(),
    CONSTRAINT uq_waste_types_name UNIQUE (name)
);
CREATE INDEX idx_waste_types_status ON waste_types(status);

CREATE TABLE processing_infrastructure_types (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(150)  NOT NULL,
    status      VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE',
    created_at  TIMESTAMP     NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP     NOT NULL DEFAULT now(),
    CONSTRAINT uq_processing_infrastructure_types_name UNIQUE (name)
);
CREATE INDEX idx_processing_infrastructure_types_status ON processing_infrastructure_types(status);

-- ---------------------------------------------------------------------------
-- Cascading masters: Zone -> Administrative Ward -> Electoral Ward -> Beat,
-- and BWG Category -> BWG Sub-Category. ON DELETE CASCADE mirrors the
-- prototype's "deleting a parent removes everything under it" behavior.
-- ---------------------------------------------------------------------------

CREATE TABLE administrative_wards (
    id          BIGSERIAL PRIMARY KEY,
    parent_id   BIGINT        NOT NULL REFERENCES zones(id) ON DELETE CASCADE,
    name        VARCHAR(150)  NOT NULL,
    status      VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE',
    created_at  TIMESTAMP     NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP     NOT NULL DEFAULT now(),
    CONSTRAINT uq_administrative_wards_parent_name UNIQUE (parent_id, name)
);
CREATE INDEX idx_administrative_wards_parent_id ON administrative_wards(parent_id);
CREATE INDEX idx_administrative_wards_status ON administrative_wards(status);

CREATE TABLE electoral_wards (
    id          BIGSERIAL PRIMARY KEY,
    parent_id   BIGINT        NOT NULL REFERENCES administrative_wards(id) ON DELETE CASCADE,
    name        VARCHAR(150)  NOT NULL,
    status      VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE',
    created_at  TIMESTAMP     NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP     NOT NULL DEFAULT now(),
    CONSTRAINT uq_electoral_wards_parent_name UNIQUE (parent_id, name)
);
CREATE INDEX idx_electoral_wards_parent_id ON electoral_wards(parent_id);
CREATE INDEX idx_electoral_wards_status ON electoral_wards(status);

CREATE TABLE beats (
    id          BIGSERIAL PRIMARY KEY,
    parent_id   BIGINT        NOT NULL REFERENCES electoral_wards(id) ON DELETE CASCADE,
    name        VARCHAR(150)  NOT NULL,
    status      VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE',
    created_at  TIMESTAMP     NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP     NOT NULL DEFAULT now(),
    CONSTRAINT uq_beats_parent_name UNIQUE (parent_id, name)
);
CREATE INDEX idx_beats_parent_id ON beats(parent_id);
CREATE INDEX idx_beats_status ON beats(status);

CREATE TABLE bwg_sub_categories (
    id          BIGSERIAL PRIMARY KEY,
    parent_id   BIGINT        NOT NULL REFERENCES bwg_categories(id) ON DELETE CASCADE,
    name        VARCHAR(150)  NOT NULL,
    status      VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE',
    created_at  TIMESTAMP     NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP     NOT NULL DEFAULT now(),
    CONSTRAINT uq_bwg_sub_categories_parent_name UNIQUE (parent_id, name)
);
CREATE INDEX idx_bwg_sub_categories_parent_id ON bwg_sub_categories(parent_id);
CREATE INDEX idx_bwg_sub_categories_status ON bwg_sub_categories(status);

-- ---------------------------------------------------------------------------
-- Officer Master
-- ---------------------------------------------------------------------------

CREATE TABLE officers (
    id                        BIGSERIAL PRIMARY KEY,
    full_name                 VARCHAR(150)  NOT NULL,
    mobile_no                 VARCHAR(15)   NOT NULL,
    email                     VARCHAR(150),
    department_id             BIGINT        NOT NULL REFERENCES departments(id) ON DELETE RESTRICT,
    designation_id            BIGINT        NOT NULL REFERENCES designations(id) ON DELETE RESTRICT,
    zone_id                   BIGINT        REFERENCES zones(id) ON DELETE SET NULL,
    administrative_ward_id    BIGINT        REFERENCES administrative_wards(id) ON DELETE SET NULL,
    status                    VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE',
    created_at                TIMESTAMP     NOT NULL DEFAULT now(),
    updated_at                TIMESTAMP     NOT NULL DEFAULT now(),
    CONSTRAINT uq_officers_mobile_no UNIQUE (mobile_no)
);
CREATE INDEX idx_officers_department_id ON officers(department_id);
CREATE INDEX idx_officers_designation_id ON officers(designation_id);
CREATE INDEX idx_officers_zone_id ON officers(zone_id);
CREATE INDEX idx_officers_administrative_ward_id ON officers(administrative_ward_id);
CREATE INDEX idx_officers_status ON officers(status);

-- ---------------------------------------------------------------------------
-- Electoral Ward wise User Mapping
-- ---------------------------------------------------------------------------

CREATE TABLE electoral_ward_user_mappings (
    id                   BIGSERIAL PRIMARY KEY,
    electoral_ward_id    BIGINT     NOT NULL REFERENCES electoral_wards(id) ON DELETE CASCADE,
    app_user_id          BIGINT     NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at           TIMESTAMP  NOT NULL DEFAULT now(),
    updated_at           TIMESTAMP  NOT NULL DEFAULT now(),
    CONSTRAINT uq_electoral_ward_user_mappings UNIQUE (electoral_ward_id, app_user_id)
);
CREATE INDEX idx_ew_user_mappings_ward_id ON electoral_ward_user_mappings(electoral_ward_id);
CREATE INDEX idx_ew_user_mappings_user_id ON electoral_ward_user_mappings(app_user_id);
