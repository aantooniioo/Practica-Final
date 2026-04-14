# Practica Final - Gestión de Empleados y Proyectos

Sistema web de gestión de empleados y proyectos empresariales desarrollado como práctica final del ciclo de Desarrollo de Aplicaciones Web.

## Descripción

Aplicación que permite gestionar empleados, proyectos y asignaciones entre ellos. Proporciona una interfaz intuitiva para visualizar datos en tiempo real mediante un dashboard con KPIs, tablas dinámicas y gráficas estadísticas.

## Requisitos Funcionales

- Gestión de empleados (alta, edición, eliminación)
- Gestión de proyectos (alta, edición, eliminación)
- Asignación de empleados a proyectos
- Visualización de datos en tiempo real
- Dashboard con KPIs e indicadores
- Interfaz responsiva y multiidioma
- Gráficas estadísticas por proyecto

## Tecnologías

### Frontend
- Vue.js 3
- Vuetify 3 (componentes UI)
- Vite (herramienta de compilación)
- i18n (internacionalización: Español, Inglés)

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

El frontend hace peticiones HTTP al backend mediante una API REST. El backend procesa la lógica de negocio y accede a la base de datos. La comunicación se realiza con JSON.

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
│   ├── service/        # Lógica de negocio
│   ├── repository/     # Acceso a datos (JPA)
│   ├── entity/         # Modelos (Empleado, Proyecto)
│   └── resources/      # Configuración (application.properties)
└── pom.xml
```

## Instalación y Uso

### Requisitos Previos
- Docker y Docker Compose
- Git

### Levantar la Aplicación

```bash
git clone https://github.com/aantooniioo/Practica-Final.git
cd Practica-Final
docker compose up
```

La aplicación estará disponible en:
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

### Gestión de Empleados
- Formulario de alta con validaciones
- Tabla con búsqueda y filtrado
- Opciones de edición y eliminación
- Visualización de datos personales (NIF, nombre, apellidos, fecha nacimiento, teléfonos, email)

### Gestión de Proyectos
- Formulario de alta con fecha inicio/fin
- Tabla de proyectos activos
- Edición de datos del proyecto
- Eliminación de proyectos

### Asignaciones
- Vincular empleados con proyectos
- Ver empleados asignados por proyecto
- Desvinculación de empleados

### Estadisticas
- Gráficas de empleados por proyecto
- Duración de proyectos
- Distribución de proyectos por estado

### Idiomas
- Interfaz disponible en Español e Inglés
- Cambio dinámico de idioma en tiempo real

## Despliegue

La aplicación está completamente containerizada con Docker. Para desplegar:

```bash
docker compose up -d
```

Utiliza Docker Compose para orquestar:
- Contenedor del backend (Spring Boot)
- Contenedor del frontend (nginx)
- Contenedor de MySQL

## Autor

Antonio Ángeles Blázquez

## Fecha

Abril 2026

## Licencia

Este proyecto es de uso educativo.
