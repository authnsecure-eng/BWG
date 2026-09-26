-- Indexes to support Admin Reports filtering/search on the existing bwg_survey table
CREATE INDEX IF NOT EXISTS idx_bwg_survey_status ON bwg_survey(status);
CREATE INDEX IF NOT EXISTS idx_bwg_survey_category ON bwg_survey(category);
CREATE INDEX IF NOT EXISTS idx_bwg_survey_zone ON bwg_survey(zone_id);
CREATE INDEX IF NOT EXISTS idx_bwg_survey_ward ON bwg_survey(ward);
CREATE INDEX IF NOT EXISTS idx_bwg_survey_submitted_at ON bwg_survey(submitted_at);
CREATE INDEX IF NOT EXISTS idx_bwg_survey_mobile_no ON bwg_survey(mobile_no);
