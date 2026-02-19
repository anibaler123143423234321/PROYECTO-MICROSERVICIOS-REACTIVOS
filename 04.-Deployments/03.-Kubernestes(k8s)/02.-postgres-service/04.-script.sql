CREATE SEQUENCE seq_jobs
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 20;

CREATE TABLE jobs (
        job_id BIGINT NOT NULL DEFAULT nextval('seq_jobs'),
        name         VARCHAR(60) NOT NULL,
        description  VARCHAR(400)  NOT NULL,
        state        VARCHAR(1)   NOT NULL,

        CONSTRAINT pk_jobs PRIMARY KEY (job_id),
        CONSTRAINT uq_jobs_name UNIQUE (name)
);

CREATE INDEX idx_jobs_name  ON jobs(name);

INSERT INTO jobs (name, description, state) VALUES
('CEO','CChief Executive Officer','1'),
('VP','Vice President','1')
ON CONFLICT (name) DO NOTHING;

INSERT INTO jobs (name, description, state) VALUES
('ADM','General Admin','1'),
('CIF','Execute Financial','1')
ON CONFLICT (name) DO NOTHING;

ALTER SEQUENCE seq_jobs RESTART WITH 3;
