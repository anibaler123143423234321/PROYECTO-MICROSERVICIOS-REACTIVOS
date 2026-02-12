CREATE SEQUENCE seq_countries
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 20;

CREATE TABLE countries (
        country_id BIGINT NOT NULL DEFAULT nextval('seq_countries'),
        name       VARCHAR(150) NOT NULL,
        code       VARCHAR(10)  NOT NULL,
        state      VARCHAR(1)   NOT NULL,

        CONSTRAINT pk_countries PRIMARY KEY (country_id),
        CONSTRAINT uq_countries_code UNIQUE (code)
);

CREATE INDEX idx_countries_state ON countries(state);
CREATE INDEX idx_countries_name  ON countries(name);

INSERT INTO countries (name, code, state) VALUES
('Afghanistan','AF','1'),
('Aland Islands','AX','1'),
('Albania','AL','1'),
('Brazil','BR','1'),
('Peru','PE','1'),
('United States','US','1'),
('United Kingdom','GB','1')
ON CONFLICT (code) DO NOTHING;


ALTER SEQUENCE seq_countries RESTART WITH 10;

SELECT last_value FROM seq_countries;


SELECT name, COUNT(*)
FROM countries
GROUP BY name
HAVING COUNT(*) > 1;

ALTER TABLE countries
ADD CONSTRAINT uq_countries_name UNIQUE (name);