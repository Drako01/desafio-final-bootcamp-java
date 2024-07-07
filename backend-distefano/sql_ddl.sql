CREATE DATABASE IF NOT EXISTS  educacionit;
USE educacionit;

-- Insertar las categorías
INSERT INTO categoria (nombre, descripcion) VALUES 
('Pastas', 'Productos de pasta alimenticia'),
('Cereales', 'Productos derivados de granos'),
('Lácteos', 'Productos lácteos'),
('Panadería', 'Productos de panadería'),
('Huevos', 'Productos derivados de huevos'),
('Azúcar', 'Productos derivados de azúcar'),
('Aceites', 'Productos de aceites'),
('Harinas', 'Productos derivados de harina');

-- Insertar los productos
INSERT INTO producto (nombre, descripcion, precio, imagen, stock, categoria_id) VALUES 
('Fideos', 'Fideos de Sémola', 1050.50, 'https://statics.dinoonline.com.ar/imagenes/full_600x600_ma/2540661_f.jpg', 100, 1),
('Arroz', 'Arroz Blanco', 850.00, 'https://media.f2h.shop/media/catalog/product/cache/ab45d104292f1bb63d093e6be8310c97/a/r/arroz.png', 150, 2),
('Leche', 'Lecclientelevelhe Entera', 650.00, 'https://acdn.mitiendanube.com/stores/093/780/products/serenisima-clasica-751-95fea92d1a27f8e9ab15710914346750-480-0.png', 200, 3),
('Pan', 'Pan Blanco', 1200.00, 'https://laroussecocina.mx/wp-content/uploads/2023/01/Pan-viene%E2%95%A0us-de-chipotle.jpg.webp', 80, 4),
('Huevos', 'Docena de Huevos', 1500.00, 'https://www.res.com.ar/media/catalog/product/cache/6c63de560a15562fe08de38c3c766637/h/u/huevos_blancos.jpg', 50, 5),
('Azúcar', 'Azúcar Blanca', 950.00, 'https://alberdisa.vteximg.com.br/arquivos/ids/174707-1000-1000/Azucar-Ledesma-x-1-Kg.png?v=638187109975370000', 120, 6),
('Aceite', 'Aceite de Oliva', 1800.00, 'https://www.cocinista.es/download/bancorecursos/ingredientes/ingrediente-aceite-oliva-2.jpg', 90, 7),
('Harina', 'Harina 0000', 750.00, 'https://acdn.mitiendanube.com/stores/001/267/442/products/7792180139313_02-photoroom1-c91ec989b6b8ea789216894436656599-1024-1024.png', 180, 8);


INSERT INTO roles (`name`) VALUES 
				("ROLE_ADMIN"), 
				("ROLE_USER"), 
				("ROLE_SELLER"), 
				("ROLE_VISITOR");



select * from producto;

select * from categoria;


select * from carrito;
select * from roles;
select * from user;
select * from users_roles;
select * from historial_compras;
SET SQL_SAFE_UPDATES = 0;
DELETE FROM user;
SET SQL_SAFE_UPDATES = 1;

ALTER TABLE producto
DROP FOREIGN KEY categoria_id;

