🏢 Microservicio de Sucursales - Grupo Cordillera
Este microservicio es el encargado de gestionar la información de los puntos de venta físicos y centros de distribución del Grupo Cordillera. Provee los datos maestros de ubicación y estado de las sucursales, fundamentales para la asignación de inventario y el registro de ventas presenciales.

🛠️ Herramientas y Requisitos
Para poner en marcha este servicio, necesitas el siguiente stack técnico:

Java JDK 17: Entorno de ejecución principal.

Apache Maven 3.8+: Para la gestión de dependencias y empaquetado.

PostgreSQL 14+: Base de datos para el almacenamiento de sucursales.

IntelliJ IDEA / VS Code: IDE recomendado para desarrollo Java.

Postman: Herramientas para testear los endpoints de la API.

⚙️ Configuración de Base de Datos
Crea una base de datos en PostgreSQL llamada db_sucursales.

Configura las credenciales en el archivo src/main/resources/application.properties:

Properties
spring.datasource.url=jdbc:postgresql://localhost:5432/db_sucursales
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña

# Hibernate creará las tablas automáticamente
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
🏁 Instrucciones de Ejecución
Clonar y entrar al directorio:

Bash
cd ms-sucursales
Compilar el proyecto:

Bash
mvn clean install
Iniciar el servicio:
Ejecuta el archivo SucursalesApplication.java desde tu IDE o usa:

Bash
mvn spring-boot:run
Este servicio corre por defecto en el puerto 8084.

🧪 Pruebas de la API (Endpoints en Postman)
1. Listar todas las Sucursales
   GET http://localhost:8084/api/sucursales
   Utilizado por el BFF para llenar los selectores en el frontend.

2. Obtener una Sucursal por ID
   GET http://localhost:8084/api/sucursales/{id}
   Retorna el nombre, dirección y estado de una sucursal específica.

3. Crear Nueva Sucursal (Ejemplo de Body)
   POST http://localhost:8084/api/sucursales

JSON
{
"nombre": "Sucursal Santiago Centro",
"direccion": "Alameda 123",
"comuna": "Santiago",
"region": "Metropolitana",
"activo": true
}
🔗 Integración con el Ecosistema
Este microservicio es una pieza clave para:

Microservicio de Ventas: Para validar dónde se realiza la transacción.

BFF (Backend For Frontend): Para enriquecer los reportes de ventas con nombres reales de sucursales en lugar de solo IDs.

Microservicio de Stock: Para gestionar las cantidades disponibles por cada ubicación física.