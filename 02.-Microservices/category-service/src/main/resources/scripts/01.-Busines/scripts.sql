CREATE SEQUENCE seq_category
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 20;

CREATE TABLE category (
    category_id  BIGINT NOT NULL DEFAULT nextval('seq_category'),
    name         VARCHAR(60) NOT NULL,
    description  VARCHAR(400) NOT NULL,
    state        VARCHAR(1) NOT NULL,

    CONSTRAINT pk_category PRIMARY KEY (category_id),
    CONSTRAINT uq_category_name UNIQUE (name)
);

CREATE INDEX idx_category_name ON category(name);

INSERT INTO category (name, description, state) VALUES
('Bebidas', 'Todo tipo de bebidas y refrescos', '1'),
('Lácteos', 'Leches, quesos y derivados', '1'),
('Panadería', 'Panes, pasteles y bollería', '1'),
('Limpieza', 'Productos de aseo y hogar', '1'),
('Carnes', 'Carnes rojas, pollo y embutidos', '1')
ON CONFLICT (name) DO NOTHING;

ALTER SEQUENCE seq_category RESTART WITH 6;
