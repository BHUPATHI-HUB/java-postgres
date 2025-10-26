# Setup Guide

## Prerequisites

- Java Development Kit (JDK) 11 or higher
- Maven 3.6+
- PostgreSQL 12 or higher
- Git (for cloning the repository)

## Database Setup

### 1. Install PostgreSQL

Download and install PostgreSQL from [https://www.postgresql.org/download/](https://www.postgresql.org/download/)

### 2. Create Database

```sql
-- Connect to PostgreSQL and create database
CREATE DATABASE mydb;

-- Connect to the database
\c mydb

-- Create users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 3. Configure Database Connection

Edit `src/main/java/com/example/postgres/DatabaseConnection.java` and update:

- `DB_URL`: Your PostgreSQL connection URL (default: jdbc:postgresql://localhost:5432/mydb)
- `DB_USER`: Your PostgreSQL username (default: postgres)
- `DB_PASSWORD`: Your PostgreSQL password

## Application Setup

### 1. Clone the Repository

```bash
git clone https://github.com/BHUPATHI-HUB/java-postgres.git
cd java-postgres
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run Tests

```bash
mvn test
```

## Usage Examples

### Create a User

```java
UserRepository repo = new UserRepository();
int userId = repo.createUser("John Doe", "john@example.com");
System.out.println("Created user with ID: " + userId);
```

### Get a User

```java
UserRepository.User user = repo.getUserById(userId);
System.out.println("User: " + user);
```

### Get All Users

```java
List<UserRepository.User> users = repo.getAllUsers();
for (UserRepository.User user : users) {
    System.out.println(user);
}
```

### Update a User

```java
repo.updateUser(userId, "John Smith", "john.smith@example.com");
```

### Delete a User

```java
repo.deleteUser(userId);
```

## Troubleshooting

### Connection Refused Error

If you get a connection refused error, ensure:
- PostgreSQL server is running
- The database exists
- Your credentials are correct
- The port (default 5432) is accessible

### Driver Not Found Error

Run `mvn clean install` to download all dependencies including the PostgreSQL JDBC driver.

## Additional Resources

- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Maven Documentation](https://maven.apache.org/guides/)
- [Java JDBC Tutorial](https://docs.oracle.com/javase/tutorial/jdbc/)
