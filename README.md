# BankSystem – Docker Setup

This document explains how to run the **BankSystem Spring Boot application** with **PostgreSQL using Docker**.

---

## 1. Prerequisites

Make sure the following are installed:

* Docker Desktop
* Java 21
* Maven
* Spring Boot 3.5.3
* PostgreSQL Docker image

Check Docker:

```powershell
docker --version
```

Check Docker is running:

```powershell
docker info
```

---

# 2. Docker Images

Check available Docker images:

```powershell
docker images
```

Expected images:

```text
bank-sys:v2
postgres:16.9
```

---

# 3. PostgreSQL Docker Container

Create the PostgreSQL container:

```powershell
docker run -d `
  --name postgres-bank `
  --platform linux/amd64 `
  -e POSTGRES_DB=BankSystem `
  -e POSTGRES_USER=postgres `
  -e POSTGRES_PASSWORD=root `
  -p 5432:5432 `
  postgres:16.9
```

### Check PostgreSQL container

```powershell
docker ps
```

Expected:

```text
postgres-bank   postgres:16.9   Up
```

Check PostgreSQL logs:

```powershell
docker logs postgres-bank
```

Look for:

```text
database system is ready to accept connections
```

---

# 4. Spring Boot Docker Image

Check whether the BankSystem image exists:

```powershell
docker images
```

Expected:

```text
bank-sys   v2
```

If you need to build the image again:

```powershell
docker build -t bank-sys:v2 .
```

---

# 5. Spring Boot Environment Variables

The `application-dev.properties` file uses environment variables.

Example:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

jwt.secret=${JWT_SECRET}

server.port=8081
```

Because the application uses:

```properties
${DB_URL}
${DB_USERNAME}
${DB_PASSWORD}
${JWT_SECRET}
```

these variables must be available **inside the Docker container**.

---

# 6. Run Spring Boot Docker Container

Run the BankSystem container:

```powershell
docker run -d `
  --name bank-app `
  -p 8081:8081 `
  -e SPRING_PROFILES_ACTIVE=dev `
  -e DB_URL="jdbc:postgresql://host.docker.internal:5432/BankSystem" `
  -e DB_USERNAME="postgres" `
  -e DB_PASSWORD="root" `
  -e JWT_SECRET="your-jwt-secret" `
  bank-sys:v2
```

### Important

The application runs on:

```text
8081
```

because the `dev` profile contains:

```properties
server.port=8081
```

Therefore Docker must use:

```text
-p 8081:8081
```

The format is:

```text
-p HOST_PORT:CONTAINER_PORT
```

---

# 7. Why `host.docker.internal`?

The Spring Boot application is running inside a Docker container.

PostgreSQL is running inside another Docker container.

The PostgreSQL port is published to the Windows host:

```text
5432:5432
```

Therefore the Spring Boot container can connect using:

```text
jdbc:postgresql://host.docker.internal:5432/BankSystem
```

Do **not** use:

```text
jdbc:postgresql://localhost:5432/BankSystem
```

inside the Spring Boot container.

Inside a container, `localhost` refers to that same container.

---

# 8. Verify Running Containers

Run:

```powershell
docker ps
```

Expected:

```text
CONTAINER ID   IMAGE           STATUS       PORTS
xxxxxxxx       bank-sys:v2    Up           0.0.0.0:8081->8081/tcp
xxxxxxxx       postgres:16.9  Up           0.0.0.0:5432->5432/tcp
```

Both containers should show:

```text
Up
```

---

# 9. Check Spring Boot Logs

Run:

```powershell
docker logs bank-app
```

You should see:

```text
The following 1 profile is active: "dev"
```

Then:

```text
Tomcat started on port 8081
```

And finally:

```text
Started BankSystemApplication
```

These messages confirm that Spring Boot started successfully.

---

# 10. Verify PostgreSQL Connection

In the Spring Boot logs, look for:

```text
HikariPool-1 - Added connection
```

and:

```text
HikariPool-1 - Start completed.
```

This confirms that Spring Boot successfully connected to PostgreSQL.

---

# 11. Access the Application

Because Docker publishes port `8081` to the Windows host:

```text
Docker container :8081
        ↓
Windows localhost :8081
```

You can access the application using:

```text
http://localhost:8081
```

If Swagger is enabled:

```text
http://localhost:8081/swagger-ui/index.html
```

---

# 12. Test Using Postman

You can also use Postman.

Example:

```text
http://localhost:8081/api/v1/users
```

Use the appropriate HTTP method and JWT authentication required by your API.

---

# 13. Check Environment Variables Inside Container

To verify that the environment variables were passed to Docker:

```powershell
docker exec bank-app printenv
```

To filter relevant variables:

```powershell
docker exec bank-app printenv | findstr "SPRING DB JWT"
```

You should see:

```text
SPRING_PROFILES_ACTIVE=dev
DB_URL=jdbc:postgresql://host.docker.internal:5432/BankSystem
DB_USERNAME=postgres
DB_PASSWORD=root
JWT_SECRET=...
```

---

# 14. JWT Secret

If `application-dev.properties` contains:

```properties
jwt.secret=${JWT_SECRET}
```

then `JWT_SECRET` must be available inside the Docker container.

You can provide it using:

```powershell
-e JWT_SECRET="your-secret"
```

It does **not** have to be hard-coded into the Docker image.

This is better because the secret stays outside the image.

### Important

Do not commit real JWT secrets to GitHub.

Avoid:

```text
JWT_SECRET=real-secret
```

inside source-controlled files.

Use environment variables or Docker secrets for sensitive values.

---

# 15. Stop the Application

Stop Spring Boot:

```powershell
docker stop bank-app
```

Stop PostgreSQL:

```powershell
docker stop postgres-bank
```

Check:

```powershell
docker ps
```

If nothing is displayed, no Docker containers are currently running.

---

# 16. Start Existing Containers Again

You do not need to create new containers every time.

Start PostgreSQL:

```powershell
docker start postgres-bank
```

Start Spring Boot:

```powershell
docker start bank-app
```

Check:

```powershell
docker ps
```

---

# 17. Remove Containers

If you want to completely remove the containers:

```powershell
docker stop bank-app
docker stop postgres-bank
```

Then:

```powershell
docker rm bank-app
docker rm postgres-bank
```

Removing a container does **not** remove the Docker image.

---

# 18. Useful Docker Commands

### List running containers

```powershell
docker ps
```

### List all containers

```powershell
docker ps -a
```

### List images

```powershell
docker images
```

### View Spring Boot logs

```powershell
docker logs bank-app
```

### View PostgreSQL logs

```powershell
docker logs postgres-bank
```

### Follow Spring Boot logs

```powershell
docker logs -f bank-app
```

### Stop container

```powershell
docker stop bank-app
```

### Start container

```powershell
docker start bank-app
```

### Remove container

```powershell
docker rm bank-app
```

### Remove image

```powershell
docker rmi bank-sys:v2
```

---

# 19. Final Architecture

The current setup is:

```text
                    Windows Machine
                          |
             +------------+------------+
             |                         |
       localhost:8081             localhost:5432
             |                         |
             v                         v
      +-------------+           +-------------+
      |  bank-app   |           | postgres-   |
      |             |           |    bank     |
      | Spring Boot |---------->| PostgreSQL  |
      |    :8081    |           |    :5432    |
      +-------------+           +-------------+
             |
             v
       BankSystem API
             |
             v
       JWT Security
             |
             v
       PostgreSQL DB
```

---

# 20. Complete Startup Procedure

For the next time you want to run the project:

### Start PostgreSQL

```powershell
docker start postgres-bank
```

### Start Spring Boot

```powershell
docker start bank-app
```

### Check containers

```powershell
docker ps
```

### Check Spring Boot

```powershell
docker logs bank-app
```

### Open Swagger

```text
http://localhost:8081/swagger-ui/index.html
```

---

## Important Lessons

1. Docker container port must match the Spring Boot port.
2. `-p 8081:8081` exposes Spring Boot port `8081` to Windows.
3. `localhost` inside a Docker container refers to that container itself.
4. `host.docker.internal` can be used to reach the Windows host from Docker Desktop.
5. Environment variables must be passed into the container if the Spring profile uses `${VARIABLE_NAME}`.
6. `JWT_SECRET` is required only if your configuration expects `${JWT_SECRET}` and no default value is provided.
7. Docker images and Docker containers are different things.
8. Stopping a container does not delete it.
9. Removing a container does not delete its image.
10. `docker ps` shows running containers, while `docker ps -a` shows all containers.
