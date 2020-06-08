CREATE TABLE users
(
    id          BIGSERIAL PRIMARY KEY,
    user_name   CHARACTER VARYING(50)  NOT NULL
        CONSTRAINT uq_user_user_name UNIQUE,
    full_name   CHARACTER VARYING(100) NOT NULL,
    password    CHARACTER VARYING(128) NOT NULL,
    permissions CHARACTER VARYING(1000)
);


