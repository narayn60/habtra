ALTER TABLE habits
    ADD frequency SMALLINT;

ALTER TABLE habits
    ADD target INTEGER;

ALTER TABLE habit_entries
    ADD note VARCHAR(255);

ALTER TABLE habits
    ALTER COLUMN user_id DROP NOT NULL;