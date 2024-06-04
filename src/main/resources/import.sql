-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- use with care https://bcrypt-generator.com/
-- alice user has password alice
-- bob user has password bob
INSERT INTO BLOG_USER (ID, USERNAME, PASSWORD_HASH, ROLE) VALUES (1, 'alice', '$2a$12$2mEZ1CqdXbkhQt2Sa4uGzO.CcvbRsQQfItoHWOzBDK.BeI0I7c.di', 'ROLE_USER') ON CONFLICT DO NOTHING;
INSERT INTO BLOG_USER (ID, USERNAME, PASSWORD_HASH, ROLE) VALUES (2, 'bob', '$2a$12$IC/gYzWsbIXf3Et9vF9EyuPsA6w0AsDWy7m0mwA5ud0dRfGO4BFAy', 'ROLE_USER') ON CONFLICT DO NOTHING;

-- alter sequence myentity_seq restart with 3;