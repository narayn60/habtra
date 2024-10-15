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
    username  VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES users (username)
);

CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);