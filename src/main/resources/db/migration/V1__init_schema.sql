-- PCMC BWG initial schema: admins, agencies, users, otp_verifications

CREATE TABLE admins (
    id              BIGSERIAL PRIMARY KEY,
    username        VARCHAR(100)  NOT NULL,
    password_hash   VARCHAR(255)  NOT NULL,
    full_name       VARCHAR(150),
    active          BOOLEAN       NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP     NOT NULL DEFAULT now(),
    updated_at      TIMESTAMP     NOT NULL DEFAULT now(),
    CONSTRAINT uq_admins_username UNIQUE (username)
);

CREATE TABLE agencies (
    id                    BIGSERIAL PRIMARY KEY,
    agency_name           VARCHAR(150)  NOT NULL,
    agency_code           VARCHAR(50)   NOT NULL,
    contact_person_name   VARCHAR(150)  NOT NULL,
    mobile_no             VARCHAR(15)   NOT NULL,
    email                 VARCHAR(150)  NOT NULL,
    address               VARCHAR(500)  NOT NULL,
    pin_code              VARCHAR(10)   NOT NULL,
    status                VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE',
    created_at            TIMESTAMP     NOT NULL DEFAULT now(),
    updated_at            TIMESTAMP     NOT NULL DEFAULT now(),
    CONSTRAINT uq_agencies_agency_name UNIQUE (agency_name),
    CONSTRAINT uq_agencies_agency_code UNIQUE (agency_code)
);

CREATE TABLE users (
    id                      BIGSERIAL PRIMARY KEY,
    agency_id               BIGINT        NOT NULL REFERENCES agencies(id),
    full_name                VARCHAR(150)  NOT NULL,
    mobile_no                VARCHAR(15)   NOT NULL,
    email                     VARCHAR(150)  NOT NULL,
    aadhaar_no_encrypted      VARCHAR(255)  NOT NULL,
    aadhaar_no_hash           VARCHAR(64)   NOT NULL,
    address                   VARCHAR(500)  NOT NULL,
    pin_code                  VARCHAR(10)   NOT NULL,
    photo_path                VARCHAR(500),
    password_hash             VARCHAR(255)  NOT NULL,
    role                      VARCHAR(30)   NOT NULL DEFAULT 'SURVEY_OFFICER',
    status                    VARCHAR(20)   NOT NULL DEFAULT 'INACTIVE',
    mobile_verified           BOOLEAN       NOT NULL DEFAULT FALSE,
    aadhaar_verified          BOOLEAN       NOT NULL DEFAULT FALSE,
    created_at                TIMESTAMP     NOT NULL DEFAULT now(),
    updated_at                TIMESTAMP     NOT NULL DEFAULT now(),
    CONSTRAINT uq_users_email UNIQUE (email),
    CONSTRAINT uq_users_mobile_no UNIQUE (mobile_no),
    CONSTRAINT uq_users_aadhaar_no_hash UNIQUE (aadhaar_no_hash)
);

CREATE INDEX idx_users_agency_id ON users(agency_id);

CREATE TABLE otp_verifications (
    id                BIGSERIAL PRIMARY KEY,
    user_id            BIGINT        NOT NULL REFERENCES users(id),
    otp_hash           VARCHAR(255)  NOT NULL,
    purpose            VARCHAR(30)   NOT NULL,
    expires_at          TIMESTAMP     NOT NULL,
    attempt_count       INT           NOT NULL DEFAULT 0,
    resend_count        INT           NOT NULL DEFAULT 0,
    verified            BOOLEAN       NOT NULL DEFAULT FALSE,
    last_sent_at         TIMESTAMP     NOT NULL DEFAULT now(),
    created_at           TIMESTAMP     NOT NULL DEFAULT now(),
    updated_at           TIMESTAMP     NOT NULL DEFAULT now()
);

CREATE INDEX idx_otp_user_id ON otp_verifications(user_id);
