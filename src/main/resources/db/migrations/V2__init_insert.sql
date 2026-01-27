USE ANTIGUABURGERS;
GO

BEGIN TRY
BEGIN TRAN;

    /* =========================================================
       HAMBURGUESAS (nombre PK)
       ========================================================= */
    IF NOT EXISTS (SELECT 1 FROM hamburguesa WHERE nombre = 'CLASICA')
    INSERT INTO hamburguesa (nombre, costo, costo_combo, img, existencia)
    VALUES ('CLASICA', 35.00, 30.00, 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761603645/clasica_ledjhg.png', 1);

    IF NOT EXISTS (SELECT 1 FROM hamburguesa WHERE nombre = 'BACON')
    INSERT INTO hamburguesa (nombre, costo, costo_combo, img, existencia)
    VALUES ('BACON', 45.00, 40.00, 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761603641/bacon_l5sfpu.webp', 1);

    IF NOT EXISTS (SELECT 1 FROM hamburguesa WHERE nombre = 'CRYSPY')
    INSERT INTO hamburguesa (nombre, costo, costo_combo, img, existencia)
    VALUES ('CRYSPY', 42.00, 37.00, 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761603641/cryspy_l8fe1u.png', 1);

    IF NOT EXISTS (SELECT 1 FROM hamburguesa WHERE nombre = 'JALAPEÑO')
    INSERT INTO hamburguesa (nombre, costo, costo_combo, img, existencia)
    VALUES ('JALAPEÑO', 43.00, 38.00, 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761603640/jalape%C3%B1o_hht1dq.webp', 1);

    IF NOT EXISTS (SELECT 1 FROM hamburguesa WHERE nombre = 'HAWAIINA')
    INSERT INTO hamburguesa (nombre, costo, costo_combo, img, existencia)
    VALUES ('HAWAIINA', 40.00, 35.00, 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761603644/hawaiina_qgpqao.png', 1);

    IF NOT EXISTS (SELECT 1 FROM hamburguesa WHERE nombre = 'VEGETARIANA')
    INSERT INTO hamburguesa (nombre, costo, costo_combo, img, existencia)
    VALUES ('VEGETARIANA', 37.00, 32.00, 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761603643/vegetariana_qephdn.png', 1);

    IF NOT EXISTS (SELECT 1 FROM hamburguesa WHERE nombre = 'DOBLECARNE')
    INSERT INTO hamburguesa (nombre, costo, costo_combo, img, existencia)
    VALUES ('DOBLECARNE', 48.00, 42.00, 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761603642/doblecarne_pbzuaf.png', 1);

    IF NOT EXISTS (SELECT 1 FROM hamburguesa WHERE nombre = 'QUESO')
    INSERT INTO hamburguesa (nombre, costo, costo_combo, img, existencia)
    VALUES ('QUESO', 38.00, 33.00, 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761603646/queso_jn1tdx.png', 1);

    -- Asset listado como "BBQ" en burgers (y existe imagen de combo también)
    IF NOT EXISTS (SELECT 1 FROM hamburguesa WHERE nombre = 'BBQ')
    INSERT INTO hamburguesa (nombre, costo, costo_combo, img, existencia)
    VALUES ('BBQ', 46.00, 41.00, 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761609699/bbq_wtnihu.png', 1);


    /* =========================================================
       COMPLEMENTOS (nombre PK)
       ========================================================= */
    IF NOT EXISTS (SELECT 1 FROM complemento WHERE nombre = 'DEDOSQUESO')
    INSERT INTO complemento (nombre, img, costo, costo_combo, existencia)
    VALUES ('DEDOSQUESO', 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761601299/dedosqueso_p8ofm7.png', 20.00, 16.00, 1);

    IF NOT EXISTS (SELECT 1 FROM complemento WHERE nombre = 'PAPASGRANDES')
    INSERT INTO complemento (nombre, img, costo, costo_combo, existencia)
    VALUES ('PAPASGRANDES', 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761601297/papasgrandes_hjzwgr.png', 18.00, 14.00, 1);

    IF NOT EXISTS (SELECT 1 FROM complemento WHERE nombre = 'PAPASMEDIANAS')
    INSERT INTO complemento (nombre, img, costo, costo_combo, existencia)
    VALUES ('PAPASMEDIANAS', 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761601296/papasmedianas_yv8inw.png', 14.00, 11.00, 1);

    IF NOT EXISTS (SELECT 1 FROM complemento WHERE nombre = 'NUGGETS')
    INSERT INTO complemento (nombre, img, costo, costo_combo, existencia)
    VALUES ('NUGGETS', 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761601296/nuggets_kchsuo.png', 22.00, 18.00, 1);

    IF NOT EXISTS (SELECT 1 FROM complemento WHERE nombre = 'AROSCEBOLLA')
    INSERT INTO complemento (nombre, img, costo, costo_combo, existencia)
    VALUES ('AROSCEBOLLA', 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761601295/aroscebolla_aydz6w.png', 18.00, 14.00, 1);


    /* =========================================================
       BEBIDAS (PK compuesto: nombre + cantidad)
       ========================================================= */
    IF NOT EXISTS (SELECT 1 FROM bebida WHERE nombre = 'COCA' AND cantidad = '1L')
    INSERT INTO bebida (nombre, cantidad, costo, costo_combo, img, existencia)
    VALUES ('COCA', '1L', 16.00, 12.00, 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761597491/coca1L_xalmv7.png', 1);

    IF NOT EXISTS (SELECT 1 FROM bebida WHERE nombre = 'COCA' AND cantidad = '500ML')
    INSERT INTO bebida (nombre, cantidad, costo, costo_combo, img, existencia)
    VALUES ('COCA', '500ML', 12.00, 9.00, 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761597491/coca500ml_vfwkya.webp', 1);

    IF NOT EXISTS (SELECT 1 FROM bebida WHERE nombre = 'FANTA' AND cantidad = '500ML')
    INSERT INTO bebida (nombre, cantidad, costo, costo_combo, img, existencia)
    VALUES ('FANTA', '500ML', 12.00, 9.00, 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761597490/fanta500ml_ftknau.png', 1);

    IF NOT EXISTS (SELECT 1 FROM bebida WHERE nombre = 'SPRITE' AND cantidad = '500ML')
    INSERT INTO bebida (nombre, cantidad, costo, costo_combo, img, existencia)
    VALUES ('SPRITE', '500ML', 12.00, 9.00, 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761597169/sprite500ml_brqdtg.webp', 1);

    IF NOT EXISTS (SELECT 1 FROM bebida WHERE nombre = 'PEPSI' AND cantidad = '500ML')
    INSERT INTO bebida (nombre, cantidad, costo, costo_combo, img, existencia)
    VALUES ('PEPSI', '500ML', 11.00, 9.00, 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761597054/pepsi500ml_rfltvq.png', 1);

    IF NOT EXISTS (SELECT 1 FROM bebida WHERE nombre = 'PEPSI' AND cantidad = '1LT')
    INSERT INTO bebida (nombre, cantidad, costo, costo_combo, img, existencia)
    VALUES ('PEPSI', '1LT', 15.00, 12.00, 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761596883/pepsi1lt_sitlbz.png', 1);

    IF NOT EXISTS (SELECT 1 FROM bebida WHERE nombre = 'AGUAPURA' AND cantidad = '600ML')
    INSERT INTO bebida (nombre, cantidad, costo, costo_combo, img, existencia)
    VALUES ('AGUAPURA', '600ML', 8.00, 6.00, 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761678136/aguapura600_qorsnz_e_background_removal_f_png_z8zdij.png', 1);


    /* =========================================================
       COMBOS (num_combo PK)
       Usando imágenes "SIZES / PRESENTATIONS" + BBQ como combo
       ========================================================= */
    IF NOT EXISTS (SELECT 1 FROM combo WHERE num_combo = 'CB0001')
    INSERT INTO combo (num_combo, nombre, costo, descripcion, img, existencia)
    VALUES ('CB0001', 'INDIVIDUAL', 55.00, 'Combo presentación INDIVIDUAL.', 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761609698/individual_lcvzzp.png', 1);

    IF NOT EXISTS (SELECT 1 FROM combo WHERE num_combo = 'CB0002')
    INSERT INTO combo (num_combo, nombre, costo, descripcion, img, existencia)
    VALUES ('CB0002', 'KIDS', 45.00, 'Combo presentación KIDS.', 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761609699/kids_sz7za7.png', 1);

    IF NOT EXISTS (SELECT 1 FROM combo WHERE num_combo = 'CB0003')
    INSERT INTO combo (num_combo, nombre, costo, descripcion, img, existencia)
    VALUES ('CB0003', 'FAMILIAR', 120.00, 'Combo presentación FAMILIAR.', 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761609704/familiar_jhxixz.png', 1);

    -- "BBQ" también lo cargo como combo con su asset de Cloudinary
    IF NOT EXISTS (SELECT 1 FROM combo WHERE num_combo = 'CB0004')
    INSERT INTO combo (num_combo, nombre, costo, descripcion, img, existencia)
    VALUES ('CB0004', 'BBQ', 65.00, 'Combo BBQ.', 'https://res.cloudinary.com/dkshw9hik/image/upload/v1761609699/bbq_wtnihu.png', 1);


COMMIT TRAN;
END TRY
BEGIN CATCH
IF XACT_STATE() <> 0 ROLLBACK TRAN;
    THROW;
END CATCH;
GO
