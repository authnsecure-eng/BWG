-- Adds a proper foreign-key relationship from bwg_survey to zones so that
-- renaming a zone in the Zone Master is reflected everywhere the zone name
-- is shown, instead of relying on a de-normalized text copy.

-- Keep the original text as a historical snapshot (audit trail) rather than
-- dropping it outright.
--
-- Guarded (not a plain ALTER) because on databases baselined from a schema
-- snapshot taken after this rename already happened, "zone" no longer exists
-- and "zone_name_snapshot"/"zone_id" are already present - running the plain
-- statements there would fail with "column zone does not exist" / duplicate
-- column errors.
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'bwg_survey' AND column_name = 'zone'
    ) THEN
        ALTER TABLE bwg_survey RENAME COLUMN zone TO zone_name_snapshot;
    END IF;
END $$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'bwg_survey' AND column_name = 'zone_id'
    ) THEN
        ALTER TABLE bwg_survey ADD COLUMN zone_id BIGINT REFERENCES zones(id) ON DELETE SET NULL;
    END IF;
END $$;

-- Backfill zone_id for existing rows by matching the snapshot text against
-- current zone names (case/whitespace insensitive). Rows with no match are
-- left with zone_id = NULL and keep displaying their zone_name_snapshot
-- text until an admin reconciles them manually.
UPDATE bwg_survey s
SET zone_id = z.id
FROM zones z
WHERE s.zone_id IS NULL
  AND s.zone_name_snapshot IS NOT NULL
  AND lower(trim(s.zone_name_snapshot)) = lower(trim(z.name));

DROP INDEX IF EXISTS idx_bwg_survey_zone;
CREATE INDEX IF NOT EXISTS idx_bwg_survey_zone_name_snapshot ON bwg_survey(zone_name_snapshot);
CREATE INDEX IF NOT EXISTS idx_bwg_survey_zone_id ON bwg_survey(zone_id);
