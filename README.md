# SpaceApps

SpaceApps es una aplicación Android que permite explorar de forma sencilla y visual la flota de cohetes de SpaceX.

## Funcionalidades

- Listado de cohetes de SpaceX con imagen y datos básicos
- Pantalla de detalle con información técnica del cohete
- Búsqueda por nombre
- Filtro para mostrar solo cohetes activos
- Soporte offline mediante base de datos local (Room)
- Gestión de estados de carga, vacío y error

## Tecnologías utilizadas

- Kotlin
- Jetpack Compose
- Arquitectura MVVM
- Retrofit (API pública de SpaceX)
- Room (almacenamiento local)
- Coroutines y StateFlow

## Acceso (credenciales de prueba)

- **Email:** `admin@lasalle.es`
- **Contraseña:** `admin1234`

## Pruebas

La aplicación incluye pruebas de interfaz con Jetpack Compose para verificar:

- Navegación correcta tras un login válido
- Manejo de errores de red y reintento

## Build

La aplicación está preparada para generar un Android App Bundle (AAB) firmado para una publicación ficticia.
