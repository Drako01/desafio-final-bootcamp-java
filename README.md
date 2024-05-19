<p align="center">
  <img src="https://static.educacionit.com/educacionit/assets/imagotype-it-fill-v2-color.svg" alt="Educacion IT" width=500>
</p><br>

# Proyecto Final de Java-Bootcamp

¡Bienvenido a mi repositorio de avances durante mi Bootcamp de Java! Aquí documentaré mi progreso y compartiré el código que estoy desarrollando. Soy Alejandro Daniel Di Stefano, un desarrollador Full Stack con experiencia en JavaScript y Desarrollador BackEnd en PHP y NodeJS.
Estoy emocionado por ampliar mis habilidades al mundo de la programación Java.

## Mi Trayectoria

- 🚀 **Bootcamp en Curso**: Actualmente estoy participando en un Bootcamp de Java para fortalecer mis habilidades de desarrollo.
- 👨‍💻 **Perfil**: Desarrollador Full Stack con experiencia en JavaScript y Desarrollador BackEnd en PHP y NodeJS.

## Estructura del Repositorio

- 📂 **Proyectos**: Contiene los dos proyectos solicitados, tanto el ForntEnd como el BackEnd.

## Contacto

- 📧 **Correo Electrónico**: [addistefano@live.com.ar](mailto:addistefano@live.com.ar)
- 💼 **LinkedIn**: [Alejandro Daniel Di Stefano](https://www.linkedin.com/in/alejandro-daniel-di-stefano/)

---- 


## Explicación del Proyecto

#### Sistema Backoffice de Gestión de Productos

El sistema backoffice de gestión de productos es una parte fundamental de la aplicación que permite a los administradores del sistema administrar el catálogo de productos y categorías de manera eficiente. Aquí están las principales características de esta parte del proyecto:

Puerto: http://localhost:8080/

1. **Catálogo de Productos y Categorías**: Se implementa un catálogo completo de productos y categorías que permite a los administradores agregar, editar y eliminar productos, así como también gestionar las categorías a las que pertenecen.

2. **Gestor de Órdenes de Pedidos**: Además de gestionar los productos, el sistema incluye un gestor de órdenes de pedidos que permite a los administradores visualizar, procesar y actualizar el estado de las órdenes de los clientes.

3. **Sistema de Autenticación de Usuarios**: Se implementa un sistema de autenticación de usuarios que garantiza que solo los usuarios autorizados puedan acceder al sistema backoffice. Esto incluye la gestión de roles y permisos para diferentes tipos de usuarios.

4. **Sistema de Seguridad**: Se incorpora un sistema de seguridad robusto para proteger los datos confidenciales del sistema, como la información de los clientes y los detalles de los pedidos. Esto incluye la encriptación de datos y el manejo adecuado de sesiones de usuario.

5. **Integración de Servicios REST**: El sistema backoffice expone servicios REST que pueden ser consumidos por otras partes de la aplicación, como el frontend de la aplicación web cliente.

6. **Documentación con Swagger**: Se documentan todos los servicios REST utilizando Swagger, lo que facilita la comprensión y el uso de la API por parte de otros desarrolladores.

#### Agrego Script SQL para cargar productos de Prueba:

``` sql
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
('Leche', 'Leche Entera', 650.00, 'https://acdn.mitiendanube.com/stores/093/780/products/serenisima-clasica-751-95fea92d1a27f8e9ab15710914346750-480-0.png', 200, 3),
('Pan', 'Pan Blanco', 1200.00, 'https://laroussecocina.mx/wp-content/uploads/2023/01/Pan-viene%E2%95%A0us-de-chipotle.jpg.webp', 80, 4),
('Huevos', 'Docena de Huevos', 1500.00, 'https://www.res.com.ar/media/catalog/product/cache/6c63de560a15562fe08de38c3c766637/h/u/huevos_blancos.jpg', 50, 5),
('Azúcar', 'Azúcar Blanca', 950.00, 'https://alberdisa.vteximg.com.br/arquivos/ids/174707-1000-1000/Azucar-Ledesma-x-1-Kg.png?v=638187109975370000', 120, 6),
('Aceite', 'Aceite de Girasol', 1800.00, 'https://www.cocinista.es/download/bancorecursos/ingredientes/ingrediente-aceite-oliva-2.jpg', 90, 7),
('Harina', 'Harina 0000', 750.00, 'https://acdn.mitiendanube.com/stores/001/267/442/products/7792180139313_02-photoroom1-c91ec989b6b8ea789216894436656599-1024-1024.png', 180, 8);

```

----


#### Aplicación Web Cliente de Comercialización

La aplicación web cliente de comercialización es la interfaz que utilizan los usuarios finales para explorar y comprar productos desde el catálogo. Aquí están las principales características de esta parte del proyecto:

Puerto: http://localhost:3000/

1. **Exploración y Compra de Productos**: Los usuarios pueden navegar por el catálogo de productos, ver detalles de los productos y agregarlos al carrito de compras. También pueden realizar el proceso de compra completo, incluido el pago y la confirmación del pedido.

2. **Autenticación de Usuarios**: Los usuarios deben autenticarse antes de realizar una compra. Se proporciona un formulario de inicio de sesión seguro, así como también la opción de registrarse como nuevo usuario.

3. **Interfaz Intuitiva y Atractiva**: Se diseña una interfaz de usuario intuitiva y atractiva que ofrece una experiencia de compra fluida y agradable para los usuarios finales. Se presta especial atención a la usabilidad y accesibilidad de la aplicación.

4. **Integración con Servicios REST**: La aplicación web cliente consume los servicios REST proporcionados por el sistema backoffice para obtener información sobre productos, procesar órdenes de pedidos y realizar operaciones relacionadas con la autenticación de usuarios.

5. **Seguridad de la Aplicación**: Se implementan medidas de seguridad en la aplicación web cliente para proteger la información confidencial de los usuarios, como los datos de inicio de sesión y los detalles de los pedidos.

6. **Compatibilidad con Diferentes Dispositivos**: La aplicación web cliente está diseñada para ser compatible con una variedad de dispositivos y tamaños de pantalla, garantizando una experiencia consistente para todos los usuarios, ya sea que accedan desde una computadora de escritorio, una tableta o un dispositivo móvil.

En resumen, el proyecto consiste en dos partes principales: un sistema backoffice de gestión de productos para administradores y una aplicación web cliente de comercialización para usuarios finales. Ambas partes se integran para ofrecer una experiencia completa y satisfactoria tanto para los administradores como para los clientes.

----

<br><p align="center">
<img src="https://1000logos.net/wp-content/uploads/2020/09/Java-Logo.png" alt="Java" width=500></p>

<p align="center"> 
 <a href="#" target="_blank"> 
     <img src="https://cdn.icon-icons.com/icons2/2699/PNG/512/mysql_official_logo_icon_169938.png" alt="sql" height="100"/>
  </a> 
    &nbsp &nbsp &nbsp
 <a href="#" target="_blank"> 
  <img src="https://miro.medium.com/v2/resize:fit:1100/0*5FEJ7emIEAxZRCQF" alt="spring-boot"  height="100"/>
 </a> 
 </p>&nbsp
 <p align="center"> 
 <a href="#" target="_blank"> 
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/html5/html5-original-wordmark.svg" alt="html5" width="120" />
  </a> 
  <a href="#" target="_blank">  
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/css3/css3-original-wordmark.svg" alt="css3" width="120" />
 </a> 
 <a href="#" target="_blank">  
  <img src="https://cdn.icon-icons.com/icons2/2415/PNG/512/javascript_original_logo_icon_146455.png" alt="javascript" width="120" />
 </a> 
 </p>&nbsp
  <p align="center"> 
<a href="#" target="_blank"> 
  <img src="https://www.vectorlogo.zone/logos/git-scm/git-scm-icon.svg" alt="git" width="120" /> 
  </a>
   &nbsp
  <a href="#" target="_blank"> 
  <img src="https://static-00.iconduck.com/assets.00/eclipse-icon-512x479-6ivkqawb.png" alt="Eclipse" width="120" /> 
  </a> 
   &nbsp
  <a href="#" target="_blank"> 
  <img src="https://static.educacionit.com/d/q_80/tecnologias/apache-maven/logo-color.svg" alt="Apache Maven" width="120" /> 
  </a> 
</p>&nbsp

---

<h3 align="center">
Alejandro Daniel Di Stefano
</h3>

---
