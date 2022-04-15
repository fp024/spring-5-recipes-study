DROP TABLE IF EXISTS USER_REGISTRATION;

CREATE TABLE IF NOT EXISTS USER_REGISTRATION (
  ID            BIGINT       GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
  FIRST_NAME    VARCHAR(255) NOT NULL,
  LAST_NAME     VARCHAR(255) NOT NULL,
  COMPANY       VARCHAR(255) NOT NULL,
  ADDRESS       VARCHAR(255) NOT NULL,
  CITY          VARCHAR(255) NOT NULL,
  STATE         VARCHAR(255) NOT NULL,
  ZIP           VARCHAR(255) NOT NULL,
  COUNTY        VARCHAR(255) NOT NULL,
  URL           VARCHAR(255) NOT NULL,
  PHONE_NUMBER  VARCHAR(255) NOT NULL,
  FAX           VARCHAR(255) NOT NULL
);