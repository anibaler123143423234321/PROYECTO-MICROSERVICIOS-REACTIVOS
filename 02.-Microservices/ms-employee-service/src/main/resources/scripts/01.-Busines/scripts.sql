CREATE SEQUENCE seq_employees
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 20;

CREATE TABLE employees (
        employee_id BIGINT NOT NULL DEFAULT nextval('seq_employees'),
        first_name          VARCHAR(60) NOT NULL,
        last_name           VARCHAR(120)  NOT NULL,
        phone               VARCHAR(60)  NOT NULL,
        email               VARCHAR(60)  NOT NULL,
        country_id          INT  NOT NULL,
        state               VARCHAR(1)   NOT NULL,

        CONSTRAINT pk_employees PRIMARY KEY (employee_id),
        CONSTRAINT uq_employees_phone UNIQUE (phone),
        CONSTRAINT uq_employees_email UNIQUE (email)
);

CREATE INDEX idx_countries_state ON countries(state);
CREATE INDEX idx_countries_name  ON countries(name);

INSERT INTO employees (first_name, last_name,phone,email,country_id, state) VALUES
('Aristedes','Novoa Arbildo','+51 950 508 789','anovoa@galaxy.edu.pe',5,'1'),
('Martha Joan','Lagos Torres','+52 950 508 000','mlagos@gmail.com',10,'1');


ALTER SEQUENCE seq_employees RESTART WITH 3;

SELECT last_value FROM seq_employees;

SELECT *
    FROM employees
    WHERE
    	(UPPER(first_name) LIKE UPPER(CONCAT('%', 'A', '%'))
    	 or UPPER(last_name) LIKE UPPER(CONCAT('%', '', '%')))
      AND state = '1';
