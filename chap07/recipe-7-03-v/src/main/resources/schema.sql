DROP TABLE todo IF EXISTS;
CREATE TABLE todo
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1) PRIMARY KEY,
    owner       VARCHAR(255)          NOT NULL,
    description VARCHAR(255)          NOT NULL,
    completed   BOOLEAN DEFAULT FALSE NOT NULL
);