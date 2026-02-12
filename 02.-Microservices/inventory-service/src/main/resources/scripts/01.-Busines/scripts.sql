CREATE SEQUENCE seq_inventory
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 20;

CREATE TABLE inventory (
        inventory_id BIGINT NOT NULL DEFAULT nextval('seq_inventory'),
        name                VARCHAR(60) NOT NULL,
        description         VARCHAR(120)  NOT NULL,
        sku                 VARCHAR(60)  NOT NULL,
        location            VARCHAR(60)  NOT NULL,
        provider_id         INT  NOT NULL,
        state               VARCHAR(1)   NOT NULL,

        CONSTRAINT pk_inventory PRIMARY KEY (inventory_id),
        CONSTRAINT uq_inventory_sku UNIQUE (sku)
);

CREATE INDEX idx_inventory_state ON inventory(state);
CREATE INDEX idx_inventory_name  ON inventory(name);

INSERT INTO inventory (name, description, sku, location, provider_id, state) VALUES
('STOCK ARROZ', 'ARROZ COSTEÑO EXTRA 750GR', 'SKU-001', 'ALMACEN A1', 5, '1'),
('STOCK AZUCAR', 'AZUCAR RUBIA 1KG', 'SKU-002', 'ALMACEN B2', 10, '1');


ALTER SEQUENCE seq_inventory RESTART WITH 3;

SELECT last_value FROM seq_inventory;

SELECT *
    FROM inventory
    WHERE
    	(UPPER(name) LIKE UPPER(CONCAT('%', 'A', '%'))
    	 or UPPER(description) LIKE UPPER(CONCAT('%', '', '%')))
      AND state = '1';
