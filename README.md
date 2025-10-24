
## Prerequisites

Before running the project, make sure you have the following installed:

- [Java JDK 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven 3.8+](https://maven.apache.org/download.cgi)
- [PostgreSQL]
- IDE (IntelliJ, Eclipse, VSCode) or terminal access
- Optional: Docker (if using Docker for database or app)

---

## 1. Clone the Repository

```bash
git clone https://github.com/ryune12/IDMProject.git
cd IDMProject/springboot-jpa-demo
```

---

## 2. Configure Application Properties

Update src/main/resources/application.properties (or application.yml) with your database and environment settings:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8080
```

---
## 3. Build the Project
```
mvn clean install
```
---
## 4. Run the Application
```
mvn spring-boot:run
```
---
## 5. Run with Docker (Optional)

If you want to run the project inside Docker:

1.Build the Docker image:
```
docker build -t springboot-jpa-demo
```

2. Run the docker container:
```
docker run -p 8080:8080 springboot-app
```

---
## 6. Run the data seeder query in your postgresql:
```
cd springboot-jpa-demo\src\main\java\com\example\seeder\data.sql
```

