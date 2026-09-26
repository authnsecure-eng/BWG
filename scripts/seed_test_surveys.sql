-- Seeds 50 test bwg_survey rows, each linked via zone_id to one of the
-- zones shown in the Reports filter dropdown, and with photo path columns
-- populated pointing to real placeholder images under uploads/photos/
-- (served at /files/photos/**). Zone IDs are looked up by name from the
-- zones table right now (nothing hardcoded).
--
-- Run manually (this is test/sample data, not a schema migration):
--   psql -U postgres -d pcmc_bwg -f scripts/seed_test_surveys.sql

WITH target_zones AS (
    SELECT z.id, ROW_NUMBER() OVER (ORDER BY z.id) - 1 AS rn, COUNT(*) OVER () AS total
    FROM zones z
    WHERE z.name IN (
        'Devgiri Zone', 'Lohagad Zone', 'Pratapgad Zone', 'Pune',
        'Purandar Zone', 'Raigad Zone', 'Rajgad Zone', 'Shivneri Zone',
        'Sinhagad Zone', 'Torna Zone', 'Vijaydurg Zone'
    )
),
series AS (
    SELECT generate_series(1, 50) AS i
),
photos AS (
    SELECT (ARRAY[
        '/files/photos/survey-sample-1.jpg',
        '/files/photos/survey-sample-2.jpg',
        '/files/photos/survey-sample-3.jpg'
    ])[1 + (s.i % 3)] AS photo_url, s.i
    FROM series s
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
    'TEST-APP-' || lpad(s.i::text, 4, '0'),
    (ARRAY['SUBMITTED', 'COMPLETED', 'REJECTED'])[1 + (s.i % 3)],
    (ARRAY['Residential Bulk Generator', 'Commercial Establishment', 'Hospitality (Hotel/Restaurant)',
           'Institutional (School/College)', 'Industrial Unit'])[1 + (s.i % 5)],
    'Test Contact ' || s.i,
    'Owner',
    '90000' || lpad(s.i::text, 5, '0'),
    'test.survey' || s.i || '@example.com',
    'Test Address ' || s.i || ', Pune',
    '4110' || lpad((s.i % 10)::text, 2, '0'),
    z.id,
    'Ward ' || (1 + (s.i % 20)),
    now() - ((s.i || ' hours')::interval),
    now(),
    now(),
    p.photo_url, p.photo_url, p.photo_url, p.photo_url,
    p.photo_url, p.photo_url, p.photo_url, p.photo_url
FROM series s
JOIN target_zones z ON z.rn = (s.i - 1) % z.total
JOIN photos p ON p.i = s.i;
