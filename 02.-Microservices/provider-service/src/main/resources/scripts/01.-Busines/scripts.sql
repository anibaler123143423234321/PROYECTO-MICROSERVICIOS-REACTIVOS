CREATE SEQUENCE seq_provider
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE provider (
    provider_id BIGINT NOT NULL DEFAULT nextval('seq_provider'),
    name        VARCHAR(150) NOT NULL,
    description VARCHAR(150),
    phone       VARCHAR(20),
    email       VARCHAR(100),
    state       VARCHAR(1)   NOT NULL,

    CONSTRAINT pk_provider PRIMARY KEY (provider_id),
    CONSTRAINT uq_provider_name UNIQUE (name),
    CONSTRAINT uq_provider_email UNIQUE (email)
);

CREATE INDEX idx_provider_state ON provider(state);
CREATE INDEX idx_provider_name  ON provider(name);

INSERT INTO provider (name, description, phone, email, state) VALUES
('DISTRIBUIDORA NORTE', 'Distribuidor Mayorista de Abarrotes', '987654321', 'ventas@distnorte.com', '1'),
('AGROINDUSTRIA SOL', 'Proveedor de Frutas y Verduras', '912345678', 'contacto@agrosol.pe', '1'),
('LACTEOS PIURA', 'Distribuidor de Productos Lácteos', '933445566', 'pedidos@lacteospuira.com', '1'),
('CARNICOS SAC', 'Proveedor de Carnes y Embutidos', '955667788', 'ventas@carnicossac.pe', '1'),
('PANIFICADORA TRUJILLO', 'Suministro de Pan y Pastelería', '944889900', 'admin@pantru.com', '1'),
('BEBIDAS DEL SUR', 'Distribuidor de Aguas y Gaseosas', '999111222', 'ventas@bebsur.com', '1')
ON CONFLICT (name) DO NOTHING;

ALTER SEQUENCE seq_provider RESTART WITH 10;