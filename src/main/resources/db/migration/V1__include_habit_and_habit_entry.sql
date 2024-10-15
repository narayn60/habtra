CREATE TABLE habit
(
    id   CHAR(36) NOT NULL,
    name VARCHAR(255),
    CONSTRAINT habit_pkey PRIMARY KEY (id)
);

CREATE TABLE habit_entry
(
    end_time   TIMESTAMP WITHOUT TIME ZONE,
    start_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    habit_id   CHAR(36) NOT NULL,
    id         CHAR(36) NOT NULL,
    CONSTRAINT habit_entry_pkey PRIMARY KEY (id)
);

ALTER TABLE habit_entry
    ADD CONSTRAINT fktqjhi1tbi34iul2fv6qby3rtf FOREIGN KEY (habit_id) REFERENCES habit (id) ON DELETE NO ACTION;