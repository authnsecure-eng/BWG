-- Seeds 50 more test bwg_survey rows (TEST-RND-*), each assigned a
-- RANDOMLY picked zone_id (not round-robin) from the zones shown in the
-- Reports filter dropdown. Zone IDs are looked up by name from the zones
-- table right now (nothing hardcoded).
--
-- Run manually (this is test/sample data, not a schema migration):
--   psql -U postgres -d pcmc_bwg -f scripts/seed_test_surveys_random_zone.sql

WITH series AS (
    SELECT generate_series(1, 50) AS i
)
INSERT INTO bwg_survey (
    application_no, status, category, contact_person_name, designation,
    mobile_no, email, full_address, pin_code, zone_id, ward, submitted_at,
    created_at, updated_at,
    premises_photo_path, water_meter_photo_path, waste_storage_photo_path,
    ebwgr_certificate_photo_path, processing_facility_photo_path,
    declaration_form_path, site_overall_photo_path, waste_handling_photo_path
)
SELECT
    'TEST-RND-' || lpad(s.i::text, 4, '0'),
    (ARRAY['SUBMITTED', 'COMPLETED', 'REJECTED'])[1 + floor(random() * 3)::int],
    (ARRAY['Residential Bulk Generator', 'Commercial Establishment', 'Hospitality (Hotel/Restaurant)',
           'Institutional (School/College)', 'Industrial Unit'])[1 + floor(random() * 5)::int],
    'Test Contact ' || s.i,
    'Owner',
    '91000' || lpad(s.i::text, 5, '0'),
    'test.random' || s.i || '@example.com',
    'Test Address ' || s.i || ', Pune',
    '4110' || lpad((s.i % 10)::text, 2, '0'),
    rz.id,
    'Ward ' || (1 + floor(random() * 20)::int),
    now() - ((floor(random() * 720) || ' hours')::interval),
    now(),
    now(),
    rp.photo_url, rp.photo_url, rp.photo_url, rp.photo_url,
    rp.photo_url, rp.photo_url, rp.photo_url, rp.photo_url
FROM series s
CROSS JOIN LATERAL (
    SELECT z.id
    FROM zones z
    WHERE z.name IN (
        'Devgiri Zone', 'Lohagad Zone', 'Pratapgad Zone', 'Pune',
        'Purandar Zone', 'Raigad Zone', 'Rajgad Zone', 'Shivneri Zone',
        'Sinhagad Zone', 'Torna Zone', 'Vijaydurg Zone'
    )
    ORDER BY random() + (s.i * 0.0)  -- reference s.i so Postgres can't hoist this out of the per-row LATERAL evaluation
    LIMIT 1
) AS rz(id)
CROSS JOIN LATERAL (
    SELECT (ARRAY[
        '/files/photos/survey-sample-1.jpg',
        '/files/photos/survey-sample-2.jpg',
        '/files/photos/survey-sample-3.jpg'
    ])[1 + floor(random() * 3)::int] AS photo_url
    ORDER BY (s.i * 0.0) + random()
) AS rp;
