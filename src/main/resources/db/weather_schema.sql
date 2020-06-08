CREATE TABLE weather_log
(
    id                BIGSERIAL PRIMARY KEY,
    city              CHARACTER VARYING(255)      NOT NULL,
    country           CHARACTER VARYING(255)      NOT NULL,
    temperature       DOUBLE PRECISION,
    weather_condition CHARACTER VARYING(255)      NOT NULL,
    calculation_time  TIMESTAMP WITHOUT TIME ZONE NOT NULL
);