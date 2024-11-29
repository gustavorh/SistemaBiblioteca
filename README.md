
# Sistema de Gestión de Biblioteca

## Arquitectura y Patrones de Diseño

### Arquitectura
Este proyecto sigue una **Arquitectura por Capas**, que extiende el Modelo-Vista-Controlador (MVC) incorporando capas adicionales para garantizar una mayor modularidad y mantenibilidad. Los beneficios clave de usar una arquitectura por capas son:

1. **Separación de Responsabilidades**: Cada capa es responsable de aspectos específicos de la aplicación, asegurando una clara separación entre la lógica de negocio, el acceso a datos y las capas de presentación.
2. **Escalabilidad**: La arquitectura permite una fácil escalabilidad, ya que se pueden agregar nuevas funcionalidades o modificaciones a capas específicas sin impactar a las demás.
3. **Capacidad de Pruebas**: Las capas aisladas hacen más efectivas las pruebas unitarias al permitir implementaciones simuladas para dependencias.
4. **Reutilización**: La funcionalidad común puede encapsularse en componentes reutilizables, facilitando el mantenimiento y la extensión.
5. **Colaboración Mejorada**: Los desarrolladores pueden trabajar en diferentes capas de manera independiente, permitiendo una colaboración eficiente en un entorno de equipo.

Esta arquitectura incluye las siguientes capas:
- **Capa de Presentación**: Maneja la interfaz de usuario utilizando JSP.
- **Capa de Aplicación**: Gestiona la lógica de negocio y los servicios.
- **Capa de Dominio**: Encapsula la lógica central del dominio y las entidades.
- **Capa de Infraestructura**: Proporciona conectividad a la base de datos e implementaciones técnicas.

### Patrones de Diseño
El proyecto incorpora varios patrones de diseño ampliamente utilizados para mejorar la mantenibilidad, flexibilidad y escalabilidad:

1. **Patrón Facade**:
   - Simplifica la interacción con subsistemas complejos creando interfaces unificadas.
   - Utilizado en este proyecto mediante la implementación de interfaces de servicio genéricas y sus implementaciones concretas usando Genéricos de Java.

2. **Patrón Abstract Factory**:
   - Proporciona una forma de encapsular la lógica de creación de objetos relacionados sin especificar sus clases concretas.
   - Usado para instanciar objetos de servicio y repositorio, asegurando consistencia y flexibilidad en la gestión de dependencias.

3. **Patrón Repositorio**:
   - Separa la lógica de acceso a datos de la lógica de negocio.
   - Aplicado en la capa de acceso a datos para gestionar operaciones CRUD para entidades, haciendo la capa de persistencia más abstracta y reutilizable.

4. **Patrón Singleton**:
   - Asegura que solo se cree una instancia de una clase y proporciona un punto de acceso global a ella.
   - Implementado para gestionar el grupo de conexiones de base de datos, optimizando la utilización de recursos y mejorando el rendimiento.

---

## Tecnologías Utilizadas

### Lenguajes de Programación y Frameworks
- **Java con Jakarta EE**: Lenguaje principal para el desarrollo de aplicaciones.
- **Java JDBC**: Utilizado para implementar la capa de acceso a datos, gestionar conexiones de base de datos y ejecutar consultas.
- **JSP (JavaServer Pages)**: Usado para construir la capa de presentación, permitiendo la generación de contenido dinámico.

### Infraestructura
- **SQL Server Express**: Sistema de Gestión de Bases de Datos (DBMS) usado para almacenar y gestionar datos.
- **Apache Tomcat 10**: Servidor de aplicaciones para desplegar y ejecutar la aplicación.
- **Docker**: Utilizado para levantar instancias de SQL Server de forma rápida y consistente.

---

Esta combinación de arquitectura, patrones de diseño y tecnologías garantiza que el proyecto sea robusto, mantenible y escalable, proporcionando una base sólida para futuras mejoras y despliegues.

## Tabla de Contenidos
1. [Configuración del Espacio de Trabajo](#configuración-del-espacio-de-trabajo)
2. [Construcción de la Aplicación](#construcción-de-la-aplicación)
3. [Despliegue de la Aplicación](#despliegue-de-la-aplicación)
4. [Diagrama ER](#diagrama-er)
5. [Diccionario de Datos](DiccionarioDatos.md)

---

## Configuración del Espacio de Trabajo

### 1. Configurar el Contenedor Docker de SQL Server
Ejecuta los siguientes comandos para descargar e iniciar un contenedor Docker con SQL Server 2022:

```bash
docker pull mcr.microsoft.com/mssql/server:2022-latest

docker run -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=<contraseña>" \
   -p 1433:1433 --name mssql --hostname mssql \
   -d \
   mcr.microsoft.com/mssql/server:2022-latest
```

> Reemplaza `<contraseña>` con una contraseña segura de tu elección.

### 2. Configurar el Servidor Tomcat
1. Descarga una instancia de Tomcat 10 desde el [sitio web oficial de Tomcat](https://tomcat.apache.org/).
2. Navega al directorio `conf` dentro de la carpeta de instalación de Tomcat y edita el archivo **`tomcat-users.xml`**.
3. Agrega la siguiente línea dentro de la etiqueta `<tomcat-users>`:
   ```xml
   <user username="admin" password="12345" roles="admin,manager-gui,manager-script"/>
   ```
   > **Nota:** Reemplaza la contraseña según tus requisitos de seguridad.

### 3. Clonar el Repositorio
Clona el repositorio de GitHub y ábrelo en IntelliJ IDEA:

```bash
git clone https://github.com/gustavorh/SistemaBiblioteca.git
```

- Abre la carpeta del proyecto en IntelliJ IDEA.

### 4. Configurar la Base de Datos
Ejecuta los siguientes scripts SQL encontrados en el repositorio:
1. **`DDL.sql`** - Para crear la estructura de la base de datos.
2. **`INSERTS.sql`** - Para poblar la base de datos con datos iniciales.

---

## Construcción de la Aplicación

Sigue estos pasos para configurar y construir la aplicación en IntelliJ IDEA:

1. **Editar la Configuración de Ejecución:**
   - Haz clic en `Ejecutar > Editar Configuraciones` en el menú de IntelliJ IDEA.
   ![Editar Configuraciones](https://i.imgur.com/wQPlWFc.png)
   - Haz clic en `Agregar Nueva Configuración` y selecciona `Servidor Tomcat (Local)`.
   ![Servidor Tomcat (Local)](https://i.imgur.com/n7z9hOe.png)

2. **Configurar el Servidor de Aplicaciones:**
   - En `Servidor de Aplicaciones`, haz clic en `Configurar`.
   ![Configurar servidor de aplicaciones](https://i.imgur.com/w8OJU2J.png)
   - Agrega tu directorio de inicio de Tomcat.
   ![Directorio de Tomcat](https://i.imgur.com/eRmkbdF.png)

3. **Agregar Artefacto de Despliegue:**
   - Ve a la pestaña `Despliegue`.
   - Haz clic en `Agregar` y selecciona el archivo `.war` generado por el proyecto.

4. **Aplicar y Ejecutar:**
   - Guarda la configuración y haz clic en `Ejecutar` para iniciar la aplicación.

---

## Despliegue de la Aplicación

> **Nota:** Esta sección está actualmente en desarrollo. Las próximas actualizaciones incluirán instrucciones detalladas de despliegue.

---
## Diagrama ER
![Diagrama ER](https://i.imgur.com/uS2HhBA.jpeg)
---

## Contribución
Siéntete libre de contribuir a este proyecto enviando pull requests o reportando issues.

---

## Licencia
Este proyecto está licenciado bajo la [Licencia MIT](LICENSE).
