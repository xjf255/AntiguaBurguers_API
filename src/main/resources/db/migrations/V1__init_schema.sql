CREATE DATABASE ANTIGUABURGERS;

USE ANTIGUABURGERS;

CREATE TABLE cliente (
                         dpi VARCHAR(13) PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL,
                         apellido VARCHAR(100) NOT NULL,
                         direccion VARCHAR(200) NOT NULL,
                         prefijo_telefono VARCHAR(4),
                         telefono VARCHAR(10),
                         email VARCHAR(150),
                         nit VARCHAR(13),
                         created_at DATETIME2 DEFAULT SYSDATETIME(),
                         updated_at DATETIME2
);

CREATE TABLE usuario_cliente (
                                 usuario VARCHAR(50) PRIMARY KEY,
                                 password_hash VARBINARY(64) NOT NULL,
                                 dpi VARCHAR(13) NOT NULL UNIQUE,
                                 created_at DATETIME2 DEFAULT SYSDATETIME(),
                                 updated_at DATETIME2 NULL,
                                 CONSTRAINT fk_usuario_cliente_cliente FOREIGN KEY (dpi) REFERENCES cliente(dpi)
);

CREATE TABLE repartidor (
                            dpi VARCHAR(13) PRIMARY KEY,
                            nombre VARCHAR(100) NOT NULL,
                            apellido VARCHAR(100) NOT NULL,
                            prefijo_telefono VARCHAR(4),
                            telefono VARCHAR(10),
                            email VARCHAR(150),
                            estado BIT DEFAULT 1,
                            created_at DATETIME2 DEFAULT SYSDATETIME(),
                            updated_at DATETIME2
);

CREATE TABLE hamburguesa (
                             nombre VARCHAR(150) PRIMARY KEY,
                             costo DECIMAL(10,2) NOT NULL CHECK (costo >= 0),
                             costo_combo DECIMAL(10,2) NULL,
                             img VARCHAR(500) NULL,
                             existencia BIT DEFAULT 1,
                             created_at DATETIME2 DEFAULT SYSDATETIME()
);

CREATE TABLE bebida (
                        nombre VARCHAR(150) NOT NULL,
                        cantidad VARCHAR(20) NOT NULL,
                        costo DECIMAL(10,2) NOT NULL CHECK (costo >= 0),
                        costo_combo DECIMAL(10,2) NULL,
                        img VARCHAR(500) NULL,
                        existencia BIT DEFAULT 1,
                        created_at DATETIME2 DEFAULT SYSDATETIME(),
                        CONSTRAINT pk_bebida PRIMARY KEY (nombre, cantidad)
);

CREATE TABLE complemento (
                             nombre VARCHAR(150) PRIMARY KEY,
                             img VARCHAR(500) NULL,
                             costo DECIMAL(10,2) NOT NULL CHECK (costo >= 0),
                             costo_combo DECIMAL(10,2) NULL,
                             existencia BIT DEFAULT 1,
                             created_at DATETIME2 DEFAULT SYSDATETIME()
);

CREATE TABLE combo (
                       num_combo VARCHAR(6) PRIMARY KEY,
                       nombre VARCHAR(200) NOT NULL,
                       costo DECIMAL(10,2) NOT NULL CHECK (costo >= 0),
                       descripcion VARCHAR(500),
                       img VARCHAR(500) NULL,
                       existencia BIT DEFAULT 1,
                       created_at DATETIME2 DEFAULT SYSDATETIME()
);

CREATE SEQUENCE seq_combo
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9999;

ALTER TABLE combo
    ADD CONSTRAINT df_combo_num
        DEFAULT ('CB' + FORMAT(NEXT VALUE FOR seq_combo, '0000')) FOR num_combo;

CREATE TABLE combo_hamburguesa (
                                   num_combo VARCHAR(6) NOT NULL,
                                   nombre_hamburguesa VARCHAR(150) NOT NULL,
                                   CONSTRAINT pk_combo_hamburguesa PRIMARY KEY (num_combo, nombre_hamburguesa),
                                   CONSTRAINT fk_ch_combo FOREIGN KEY (num_combo) REFERENCES combo(num_combo),
                                   CONSTRAINT fk_ch_hamb FOREIGN KEY (nombre_hamburguesa) REFERENCES hamburguesa(nombre)
);

CREATE TABLE combo_bebida (
                              num_combo VARCHAR(6) NOT NULL,
                              nombre_bebida VARCHAR(150) NOT NULL,
                              cantidad_bebida VARCHAR(20) NOT NULL,
                              CONSTRAINT pk_combo_bebida PRIMARY KEY (num_combo, nombre_bebida, cantidad_bebida),
                              CONSTRAINT fk_cb_combo FOREIGN KEY (num_combo) REFERENCES combo(num_combo),
                              CONSTRAINT fk_cb_bebida FOREIGN KEY (nombre_bebida, cantidad_bebida) REFERENCES bebida(nombre, cantidad)
);

CREATE TABLE combo_complemento (
                                   num_combo VARCHAR(6) NOT NULL,
                                   nombre_complemento VARCHAR(150) NOT NULL,
                                   CONSTRAINT pk_combo_complemento PRIMARY KEY (num_combo, nombre_complemento),
                                   CONSTRAINT fk_cc_combo FOREIGN KEY (num_combo) REFERENCES combo(num_combo),
                                   CONSTRAINT fk_cc_comp FOREIGN KEY (nombre_complemento) REFERENCES complemento(nombre)
);

CREATE TABLE promocion (
                           num_promocion VARCHAR(6) PRIMARY KEY,
                           num_combo VARCHAR(6) NULL,
                           descuento DECIMAL(5,2) NOT NULL CHECK (descuento >= 0),
                           fecha_inicio DATE,
                           fecha_fin DATE,
                           descripcion VARCHAR(500),
                           CONSTRAINT fk_promocion_combo FOREIGN KEY (num_combo) REFERENCES combo(num_combo)
);

CREATE SEQUENCE seq_promocion
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9999;

ALTER TABLE promocion
    ADD CONSTRAINT df_promocion_num
        DEFAULT ('P' + FORMAT(NEXT VALUE FOR seq_promocion, '0000')) FOR num_promocion;

CREATE TABLE estado_pedido (
                               estado VARCHAR(20) PRIMARY KEY
);

INSERT INTO estado_pedido (estado) VALUES
                                       ('PENDIENTE'),
                                       ('CONFIRMADO'),
                                       ('EN_PREPARACION'),
                                       ('ENTREGADO'),
                                       ('CANCELADO');

CREATE TABLE pedido (
                        num_pedido VARCHAR(10) PRIMARY KEY,
                        fecha DATETIME2 DEFAULT SYSDATETIME(),
                        estado VARCHAR(20) NOT NULL,
                        dpi_cliente VARCHAR(13) NOT NULL,
                        direccion_entrega VARCHAR(400) NULL,
                        requiere_delivery BIT DEFAULT 0,
                        total DECIMAL(12,2) DEFAULT 0 CHECK (total >= 0),
                        created_at DATETIME2 DEFAULT SYSDATETIME(),
                        updated_at DATETIME2,
                        CONSTRAINT fk_pedido_cliente FOREIGN KEY (dpi_cliente) REFERENCES cliente(dpi),
                        CONSTRAINT fk_pedido_estado FOREIGN KEY (estado) REFERENCES estado_pedido(estado)
);

CREATE SEQUENCE seq_pedido
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 999999;

ALTER TABLE pedido
    ADD CONSTRAINT df_pedido_num
        DEFAULT ('PD' + FORMAT(NEXT VALUE FOR seq_pedido, '000000')) FOR num_pedido;

CREATE TABLE pedido_detalle (
                                id_detalle INT IDENTITY(1,1) PRIMARY KEY,
                                num_pedido VARCHAR(10) NOT NULL,
                                num_combo VARCHAR(6) NULL,
                                hamburguesa VARCHAR(150) NULL,
                                bebida_nombre VARCHAR(150) NULL,
                                bebida_cantidad VARCHAR(20) NULL,
                                complemento VARCHAR(150) NULL,
                                cantidad INT NOT NULL CHECK (cantidad > 0),
                                precio_unitario DECIMAL(10,2) NOT NULL CHECK (precio_unitario >= 0),
                                subtotal AS (cantidad * precio_unitario) PERSISTED,
                                CONSTRAINT fk_pd_pedido FOREIGN KEY (num_pedido) REFERENCES pedido(num_pedido) ON DELETE CASCADE,
                                CONSTRAINT fk_pd_combo FOREIGN KEY (num_combo) REFERENCES combo(num_combo),
                                CONSTRAINT fk_pd_hamb FOREIGN KEY (hamburguesa) REFERENCES hamburguesa(nombre),
                                CONSTRAINT fk_pd_bebida FOREIGN KEY (bebida_nombre, bebida_cantidad) REFERENCES bebida(nombre, cantidad),
                                CONSTRAINT fk_pd_comp FOREIGN KEY (complemento) REFERENCES complemento(nombre),
                                CONSTRAINT chk_pd_oneitem CHECK (
                                    num_combo IS NOT NULL
                                        OR hamburguesa IS NOT NULL
                                        OR (bebida_nombre IS NOT NULL AND bebida_cantidad IS NOT NULL)
                                        OR complemento IS NOT NULL
                                    )
);

CREATE TABLE estado_entrega (
                                estado VARCHAR(50) PRIMARY KEY
);

INSERT INTO estado_entrega (estado) VALUES
                                        ('PENDIENTE'),
                                        ('EN_RUTA'),
                                        ('ENTREGADO'),
                                        ('FALLIDA');

CREATE TABLE entrega (
                         num_pedido VARCHAR(10) PRIMARY KEY,
                         estado VARCHAR(50) DEFAULT 'PENDIENTE',
                         dpi_repartidor VARCHAR(13) NULL,
                         direccion_entrega VARCHAR(400) NULL,
                         created_at DATETIME2 DEFAULT SYSDATETIME(),
                         CONSTRAINT fk_entrega_pedido FOREIGN KEY (num_pedido) REFERENCES pedido(num_pedido) ON DELETE CASCADE,
                         CONSTRAINT fk_entrega_repartidor FOREIGN KEY (dpi_repartidor) REFERENCES repartidor(dpi) ON DELETE SET NULL,
                         CONSTRAINT fk_entrega_estado FOREIGN KEY (estado) REFERENCES estado_entrega(estado)
);

CREATE TRIGGER trg_actualizar_total_pedido
    ON pedido_detalle
    AFTER INSERT, DELETE, UPDATE
AS
BEGIN
     SET NOCOUNT ON;

    ;WITH afectados AS (
    SELECT num_pedido FROM inserted
    UNION
    SELECT num_pedido FROM deleted
)
UPDATE p
SET total = ISNULL(t.suma, 0),
    updated_at = SYSDATETIME()
    FROM pedido p
    INNER JOIN afectados a ON a.num_pedido = p.num_pedido
    OUTER APPLY (
    SELECT SUM(subtotal) AS suma
    FROM pedido_detalle pd
    WHERE pd.num_pedido = p.num_pedido
    ) t;
END;

CREATE OR ALTER PROCEDURE sp_agregar_pedido_detalle
    @num_pedido        VARCHAR(10),
    @num_combo         VARCHAR(6)        = NULL,
    @hamburguesa       VARCHAR(150)      = NULL,
    @bebida_nombre     VARCHAR(150)      = NULL,
    @bebida_cantidad   VARCHAR(20)       = NULL,
    @complemento       VARCHAR(150)      = NULL,
    @cantidad          INT,
    @precio_unitario   DECIMAL(10,2)
    AS
BEGIN
    SET NOCOUNT ON;

BEGIN TRY
BEGIN TRAN;

        -- 1) Validaciones básicas de entrada
        IF @num_pedido IS NULL OR LTRIM(RTRIM(@num_pedido)) = ''
            THROW 51000, 'El num_pedido es requerido.', 1;

        IF @cantidad IS NULL OR @cantidad <= 0
            THROW 51001, 'La cantidad debe ser mayor que 0.', 1;

        IF @precio_unitario IS NULL OR @precio_unitario < 0
            THROW 51002, 'Precio unitario debe ser mayor o igual que 0.', 1;

        -- 2) Validar que exactamente UNO de los item identifiers esté presente
        DECLARE @nonNullCount INT =
            (CASE WHEN @num_combo IS NOT NULL THEN 1 ELSE 0 END)
          + (CASE WHEN @hamburguesa IS NOT NULL THEN 1 ELSE 0 END)
          + (CASE WHEN @bebida_nombre IS NOT NULL AND @bebida_cantidad IS NOT NULL THEN 1 ELSE 0 END)
          + (CASE WHEN @complemento IS NOT NULL THEN 1 ELSE 0 END);

        IF @nonNullCount <> 1
            THROW 51003, 'Debe especificar exactamente un tipo de item por linea: combo OR hamburguesa OR bebida(nombre+cantidad) OR complemento.', 1;

        -- 3) Pedido existe y está en estado válido
        IF NOT EXISTS (SELECT 1 FROM pedido WHERE num_pedido = @num_pedido)
            THROW 51004, 'El pedido indicado no existe.', 1;

        DECLARE @pedido_estado VARCHAR(50);
SELECT @pedido_estado = estado FROM pedido WHERE num_pedido = @num_pedido;

IF @pedido_estado IN ('CANCELADO', 'ENTREGADO')
            THROW 51005, 'No se pueden agregar items a un pedido en estado', 1;

        -- 4) Validaciones por tipo y existencia (existencia = 1)
        IF @num_combo IS NOT NULL
BEGIN
            IF NOT EXISTS (SELECT 1 FROM combo WHERE num_combo = @num_combo)
                THROW 51010, 'Combo no existe.', 1;

            IF EXISTS (SELECT 1 FROM combo WHERE num_combo = @num_combo AND existencia = 0)
                THROW 51011, 'Combo no disponible (existencia = 0).', 1;
END

        IF @hamburguesa IS NOT NULL
BEGIN
            IF NOT EXISTS (SELECT 1 FROM hamburguesa WHERE nombre = @hamburguesa)
                THROW 51012, 'Hamburguesa no existe.', 1;

            IF EXISTS (SELECT 1 FROM hamburguesa WHERE nombre = @hamburguesa AND existencia = 0)
                THROW 51013, 'Hamburguesa no disponible (existencia = 0).', 1;
END

        IF @bebida_nombre IS NOT NULL
BEGIN
            IF @bebida_cantidad IS NULL OR LTRIM(RTRIM(@bebida_cantidad)) = ''
                THROW 51014, 'Para bebidas debe enviarse nombre y cantidad.', 1;

            IF NOT EXISTS (SELECT 1 FROM bebida WHERE nombre = @bebida_nombre AND cantidad = @bebida_cantidad)
                THROW 51015, 'Bebida (nombre+cantidad) no existe.', 1;

            IF EXISTS (SELECT 1 FROM bebida WHERE nombre = @bebida_nombre AND cantidad = @bebida_cantidad AND existencia = 0)
                THROW 51016, 'Bebida no disponible (existencia = 0).', 1;
END

        IF @complemento IS NOT NULL
BEGIN
            IF NOT EXISTS (SELECT 1 FROM complemento WHERE nombre = @complemento)
                THROW 51017, 'Complemento no existe.', 1;

            IF EXISTS (SELECT 1 FROM complemento WHERE nombre = @complemento AND existencia = 0)
                THROW 51018, 'Complemento no disponible (existencia = 0).', 1;
END

        -- 5) Insertar el detalle (el trigger trg_actualizar_total_pedido recalcula el total)
INSERT INTO pedido_detalle
(num_pedido, num_combo, hamburguesa, bebida_nombre, bebida_cantidad, complemento, cantidad, precio_unitario)
VALUES
    (@num_pedido, @num_combo, @hamburguesa, @bebida_nombre, @bebida_cantidad, @complemento, @cantidad, @precio_unitario);

DECLARE @new_id INT = SCOPE_IDENTITY();

        -- 6) Leer total ya recalculado por trigger (y aplicar regla de delivery)
        DECLARE @current_total DECIMAL(12,2);
SELECT @current_total = total
FROM pedido
WHERE num_pedido = @num_pedido;

IF @current_total >= 50
BEGIN
UPDATE pedido
SET requiere_delivery = 1,
    updated_at = SYSDATETIME()
WHERE num_pedido = @num_pedido;

IF NOT EXISTS (SELECT 1 FROM entrega WHERE num_pedido = @num_pedido)
BEGIN
INSERT INTO entrega (num_pedido, estado, dpi_repartidor, direccion_entrega, created_at)
VALUES (
           @num_pedido,
           'PENDIENTE',
           NULL,
           (SELECT direccion_entrega FROM pedido WHERE num_pedido = @num_pedido),
           SYSDATETIME()
       );
END
ELSE
BEGIN
UPDATE entrega
SET direccion_entrega = (SELECT direccion_entrega FROM pedido WHERE num_pedido = @num_pedido)
WHERE num_pedido = @num_pedido;
END
END
ELSE
BEGIN
            IF EXISTS (SELECT 1 FROM entrega WHERE num_pedido = @num_pedido)
BEGIN
DELETE FROM entrega WHERE num_pedido = @num_pedido;

UPDATE pedido
SET requiere_delivery = 0,
    updated_at = SYSDATETIME()
WHERE num_pedido = @num_pedido;
END
END

COMMIT TRAN;

SELECT @new_id AS id_detalle, @current_total AS nuevo_total;
END TRY
BEGIN CATCH
IF XACT_STATE() <> 0
            ROLLBACK TRAN;

        DECLARE @ErrMsg NVARCHAR(4000) = ERROR_MESSAGE();
        DECLARE @ErrNum INT = ERROR_NUMBER();
        DECLARE @ErrState INT = ERROR_STATE();

        THROW @ErrNum, @ErrMsg, @ErrState;
END CATCH
END;


CREATE PROCEDURE sp_confirmar_pedido
    @num_pedido VARCHAR(10)
AS
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pedido_detalle WHERE num_pedido = @num_pedido)
BEGIN
        RAISERROR('No se puede confirmar un pedido sin items.', 16, 1);
        RETURN;
END

UPDATE pedido
SET estado = 'CONFIRMADO'
WHERE num_pedido = @num_pedido;
END;

CREATE INDEX IX_Pedido_dpi_cliente ON pedido(dpi_cliente);
CREATE INDEX IX_Pedido_fecha ON pedido(fecha);
CREATE INDEX IX_Combo_nombre ON combo(nombre);