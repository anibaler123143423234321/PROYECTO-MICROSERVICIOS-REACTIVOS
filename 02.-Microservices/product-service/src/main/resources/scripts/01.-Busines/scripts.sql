CREATE SEQUENCE seq_product
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 20;

CREATE TABLE product (
        product_id BIGINT NOT NULL DEFAULT nextval('seq_product'),
        name         VARCHAR(60) NOT NULL,
        description  VARCHAR(400)  NOT NULL,
        state        VARCHAR(1)   NOT NULL,
        category_id  BIGINT       NOT NULL,

        CONSTRAINT pk_product PRIMARY KEY (product_id),
        CONSTRAINT uq_product_name UNIQUE (name)
);

CREATE INDEX idx_product_name  ON product(name);

INSERT INTO product (name, description, state) VALUES
('Latop HP','Latop HP 15" i7','1'),
('Mouse Gamer','Mouse Logitech G502','1')
ON CONFLICT (name) DO NOTHING;

ALTER SEQUENCE seq_product RESTART WITH 3;
