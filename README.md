# Practica Final - Gestion de Empleados y Proyectos

Sistema web de gestion de empleados y proyectos empresariales desarrollado como practica final del ciclo de Desarrollo de Aplicaciones Web.

## Descripcion

Aplicacion que permite gestionar empleados, proyectos y asignaciones entre ellos. Proporciona una interfaz intuitiva para visualizar datos en tiempo real mediante un dashboard con KPIs, tablas dinamicas y graficas estadisticas.

## Requisitos Funcionales

- Gestion de empleados (alta, edicion, eliminacion)
- Gestion de proyectos (alta, edicion, eliminacion)
- Asignacion de empleados a proyectos
- Visualizacion de datos en tiempo real
- Dashboard con KPIs e indicadores
- Interfaz responsiva y multiidioma
- Graficas estadisticas por proyecto

## Tecnologias

### Frontend
- Vue.js 3
- Vuetify 3 (componentes UI)
- Vite (herramienta de compilacion)
- i18n (internacionalizacion: Español, Ingles)

### Backend
- Spring Boot 3.3.5
- Java 25 LTS
- JPA/Hibernate (mapeo objeto-relacional)
- Maven

### Base de Datos
- MySQL 8.4
- Modelo relacional normalizado

### API
- REST con JSON
- CORS habilitado

### DevOps
- Docker
- Docker Compose
- nginx

## Arquitectura

```
Cliente (Vue.js)
      |
      | HTTP/JSON
      |
   API REST
      |
Servidor (Spring Boot)
      |
   Base de Datos (MySQL)
```

El frontend hace peticiones HTTP al backend mediante una API REST. El backend procesa la logica de negocio y accede a la base de datos. La comunicacion se realiza con JSON.

## Estructura del Proyecto

```
Frontend/
├── src/
│   ├── views/          # Paginas principales
│   ├── components/     # Componentes reutilizables
│   ├── services/       # Servicios HTTP
│   ├── locales/        # Archivos i18n (ES, EN)
│   └── assets/         # Imagenes y recursos
├── vite.config.js
└── package.json

Backend/
├── src/main/java/com/angeles/backend/
│   ├── controller/     # REST endpoints
│   ├── service/        # Logica de negocio
│   ├── repository/     # Acceso a datos (JPA)
│   ├── entity/         # Modelos (Empleado, Proyecto)
│   └── resources/      # Configuracion (application.properties)
└── pom.xml
```

## Instalacion y Uso

### Requisitos Previos
- Docker y Docker Compose
- Git

### Levantar la Aplicacion

```bash
git clone https://github.com/aantooniioo/Practica-Final.git
cd Practica-Final
docker compose up
```

La aplicacion estara disponible en:
- Frontend: http://localhost:5173
- Backend API: http://localhost:8080
- MySQL: localhost:3307

### Variables de Entorno

Las credenciales de MySQL se configuran en `docker-compose.yml`:
- Usuario: root
- Contraseña: root
- Base de datos: practica

## Funcionalidades

### Dashboard
- Contador de empleados activos
- Contador de proyectos activos
- Contador de asignaciones
- Accesos directos a crear empleados, proyectos y asignaciones

### Gestion de Empleados
- Formulario de alta con validaciones
- Tabla con busqueda y filtrado
- Opciones de edicion y eliminacion
- Visualizacion de datos personales (NIF, nombre, apellidos, fecha nacimiento, telefonos, email)

### Gestion de Proyectos
- Formulario de alta con fecha inicio/fin
- Tabla de proyectos activos
- Edicion de datos del proyecto
- Eliminacion de proyectos

### Asignaciones
- Vincular empleados con proyectos
- Ver empleados asignados por proyecto
- Desvinculacion de empleados

### Estadisticas
- Graficas de empleados por proyecto
- Duracion de proyectos
- Distribucion de proyectos por estado

### Idiomas
- Interfaz disponible en Español e Ingles
- Cambio dinamico de idioma en tiempo real

## Despliegue

La aplicacion esta completamente containerizada con Docker. Para desplegar:

```bash
docker compose up -d
```

Utiliza Docker Compose para orquestar:
- Contenedor del backend (Spring Boot)
- Contenedor del frontend (nginx)
- Contenedor de MySQL

## Documentacion

La presentacion de la practica esta disponible en:
- `/docs/Presentacion_Practica_Final.pptx`

Incluye:
- Requisitos funcionales
- Tecnologias utilizadas
- Arquitectura del proyecto
- Estructura del codigo
- Demostracion de funcionalidades
- Pantallazos de la aplicacion

## Autor

Antonio Angeles Blazquez

## Fecha

Abril 2026

## Licencia

Este proyecto es de uso educativo.
