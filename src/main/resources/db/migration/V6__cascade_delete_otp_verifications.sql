-- Deleting a user was failing with a generic "conflicts with existing data"
-- error whenever that user had any otp_verifications row (i.e. almost every
-- user, since OTP is sent during mobile verification onboarding). The FK had
-- no ON DELETE action, so Postgres blocked the delete. Fix it to cascade,
-- matching the pattern already used for electoral_ward_user_mappings.
ALTER TABLE otp_verifications DROP CONSTRAINT otp_verifications_user_id_fkey;
ALTER TABLE otp_verifications ADD CONSTRAINT otp_verifications_user_id_fkey
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
