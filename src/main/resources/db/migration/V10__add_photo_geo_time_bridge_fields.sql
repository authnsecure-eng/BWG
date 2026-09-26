-- The mobile app's survey form captures a GPS geo-tag and a capture
-- timestamp alongside each of its premises/signage/building-permission/
-- declaration photo uploads (proof the evidence was captured on-site at
-- survey time), but V9 only carried the photo *path* for these four items
-- across the mobile->admin bridge - the geo-tag and timestamp were silently
-- dropped in transit. Additive/nullable, same pattern as V9.

ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS premises_photo_geo VARCHAR(255);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS premises_photo_time VARCHAR(100);

ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS signage_photo_geo VARCHAR(255);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS signage_photo_time VARCHAR(100);

ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS building_permission_geo VARCHAR(255);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS building_permission_time VARCHAR(100);

ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS declaration_file_geo VARCHAR(255);
ALTER TABLE bwg_survey ADD COLUMN IF NOT EXISTS declaration_file_time VARCHAR(100);
