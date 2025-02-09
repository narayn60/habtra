CREATE TABLE "users"
(
    id       UUID         NOT NULL,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    enabled  BOOLEAN DEFAULT true,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE "authorities"
(
    authority VARCHAR(50) NOT NULL,
    user_id   UUID REFERENCES users (id) PRIMARY KEY
);

CREATE UNIQUE INDEX ix_auth_user_id ON authorities (user_id, authority);

CREATE TABLE habits
(
    id      UUID                       NOT NULL PRIMARY KEY,
    name    VARCHAR(255),
    user_id UUID REFERENCES users (id) NOT NULL
);

CREATE TABLE habit_entries
(
    id         UUID                        NOT NULL PRIMARY KEY,
    end_time   TIMESTAMP WITHOUT TIME ZONE,
    start_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    habit_id   UUID REFERENCES habits (id) NOT NULL
);