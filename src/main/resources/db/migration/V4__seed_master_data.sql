-- Seeds the Masters tables with the same sample data used in the admin
-- dashboard prototype. Idempotent (ON CONFLICT DO NOTHING) so it is safe to
-- run against a database that already has some of these rows.

INSERT INTO role_masters (name) VALUES
    ('Admin'),
    ('Municipal Commissioner'),
    ('Addl. Municipal Commissioner'),
    ('Deputy Municipal Commissioner'),
    ('Ward Officer'),
    ('Sanitary Officer'),
    ('Sanitary Inspector'),
    ('Supervisor'),
    ('Agency Operator'),
    ('Sub-Sanitary Inspector'),
    ('Chief Sanitary Office'),
    ('Surveyor'),
    ('Data Entry Operator')
ON CONFLICT (name) DO NOTHING;

INSERT INTO zones (name) VALUES
    ('Rajgad Zone'),
    ('Sinhagad Zone'),
    ('Vijaydurg Zone'),
    ('Devgiri Zone'),
    ('Shivneri Zone'),
    ('Torna Zone'),
    ('Purandar Zone'),
    ('Pratapgad Zone'),
    ('Raigad Zone'),
    ('Lohagad Zone')
ON CONFLICT (name) DO NOTHING;

INSERT INTO departments (name) VALUES
    ('Solid Waste Management Dept.'),
    ('Health Department'),
    ('Environment Department'),
    ('Engineering Department'),
    ('Revenue Department')
ON CONFLICT (name) DO NOTHING;

INSERT INTO designations (name) VALUES
    ('Commissioner'),
    ('Deputy Commissioner'),
    ('Zonal Health Officer'),
    ('Sanitary Inspector'),
    ('Junior Engineer')
ON CONFLICT (name) DO NOTHING;

INSERT INTO bwg_categories (name) VALUES
    ('Residential Bulk Generator'),
    ('Commercial Establishment'),
    ('Hospitality (Hotel/Restaurant)'),
    ('Institutional (School/College)'),
    ('Industrial Unit')
ON CONFLICT (name) DO NOTHING;

INSERT INTO property_types (name) VALUES
    ('Residential'),
    ('Commercial'),
    ('Mixed Use'),
    ('Industrial'),
    ('Institutional')
ON CONFLICT (name) DO NOTHING;

INSERT INTO ownership_types (name) VALUES
    ('Self Owned'),
    ('Rented / Leased'),
    ('Government Owned'),
    ('Trust Owned')
ON CONFLICT (name) DO NOTHING;

INSERT INTO unit_types (name) VALUES
    ('Kilogram (Kg)'),
    ('Metric Ton (MT)'),
    ('Litre (L)'),
    ('Cubic Meter (m3)')
ON CONFLICT (name) DO NOTHING;

INSERT INTO waste_types (name) VALUES
    ('Wet Waste (Biodegradable)'),
    ('Dry Waste (Recyclable)'),
    ('Sanitary Waste'),
    ('Domestic Hazardous Waste'),
    ('E-Waste'),
    ('Construction & Demolition Waste')
ON CONFLICT (name) DO NOTHING;

INSERT INTO processing_infrastructure_types (name) VALUES
    ('Biogas Plant'),
    ('Composting Unit'),
    ('Material Recovery Facility (MRF)'),
    ('Waste-to-Energy Plant'),
    ('Transfer Station')
ON CONFLICT (name) DO NOTHING;

-- Administrative Wards under the first three zones (matches the prototype's
-- sample cascading data).
INSERT INTO administrative_wards (parent_id, name)
SELECT z.id, w.ward_name
FROM (VALUES
    ('Rajgad Zone', 'Ward 13'),
    ('Rajgad Zone', 'Ward 14'),
    ('Rajgad Zone', 'Ward 15'),
    ('Sinhagad Zone', 'Ward 17'),
    ('Sinhagad Zone', 'Ward 18'),
    ('Sinhagad Zone', 'Ward 19'),
    ('Vijaydurg Zone', 'Ward 2'),
    ('Vijaydurg Zone', 'Ward 9'),
    ('Vijaydurg Zone', 'Ward 10')
) AS w(zone_name, ward_name)
JOIN zones z ON z.name = w.zone_name
ON CONFLICT (parent_id, name) DO NOTHING;

-- One Electoral Ward per Administrative Ward (matches the prototype).
INSERT INTO electoral_wards (parent_id, name)
SELECT aw.id, e.ew_name
FROM (VALUES
    ('Ward 13', 'Electoral Ward 1'),
    ('Ward 14', 'Electoral Ward 2'),
    ('Ward 15', 'Electoral Ward 3'),
    ('Ward 17', 'Electoral Ward 4'),
    ('Ward 18', 'Electoral Ward 5'),
    ('Ward 19', 'Electoral Ward 6'),
    ('Ward 2', 'Electoral Ward 7'),
    ('Ward 9', 'Electoral Ward 8'),
    ('Ward 10', 'Electoral Ward 9')
) AS e(ward_name, ew_name)
JOIN administrative_wards aw ON aw.name = e.ward_name
ON CONFLICT (parent_id, name) DO NOTHING;

-- One Beat per Electoral Ward (matches the prototype).
INSERT INTO beats (parent_id, name)
SELECT ew.id, b.beat_name
FROM (VALUES
    ('Electoral Ward 1', 'Beat 1'),
    ('Electoral Ward 2', 'Beat 2'),
    ('Electoral Ward 3', 'Beat 3'),
    ('Electoral Ward 4', 'Beat 4'),
    ('Electoral Ward 5', 'Beat 5'),
    ('Electoral Ward 6', 'Beat 6'),
    ('Electoral Ward 7', 'Beat 7'),
    ('Electoral Ward 8', 'Beat 8'),
    ('Electoral Ward 9', 'Beat 9')
) AS b(ew_name, beat_name)
JOIN electoral_wards ew ON ew.name = b.ew_name
ON CONFLICT (parent_id, name) DO NOTHING;

-- BWG Sub-Categories, one under each BWG Category (the prototype lists
-- these as a flat sample set with no explicit parent - this pairing is a
-- reasonable 1:1 mapping onto the categories above).
INSERT INTO bwg_sub_categories (parent_id, name)
SELECT c.id, s.sub_name
FROM (VALUES
    ('Residential Bulk Generator', 'Apartment Complex > 100 flats'),
    ('Commercial Establishment', 'Shopping Mall'),
    ('Hospitality (Hotel/Restaurant)', '5-Star Hotel'),
    ('Institutional (School/College)', 'Multi-specialty Hospital'),
    ('Industrial Unit', 'Manufacturing Unit')
) AS s(category_name, sub_name)
JOIN bwg_categories c ON c.name = s.category_name
ON CONFLICT (parent_id, name) DO NOTHING;
