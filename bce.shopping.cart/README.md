# BCE Shopping Cart Application

A Java web application for an online shopping cart system built with **Spring Boot**, Spring MVC, JSP, and MySQL.

## Prerequisites

- **Java JDK 8+** - [Download](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.6+** - [Download](https://maven.apache.org/download.cgi)
- **MySQL 8.0+** - [Download](https://dev.mysql.com/downloads/mysql/)

**Note:** No external Tomcat installation is required! Spring Boot includes an embedded Tomcat server.

## Setup Instructions

### 1. Database Setup

1. Start MySQL server
2. Create the database:
   ```sql
   CREATE DATABASE `bce-shopping-cart`;
   ```

3. Create the required tables. See `src/main/webapp/help/ReadMe.html` for the complete SQL schema, or run these commands:

   ```sql
   USE `bce-shopping-cart`;
   
   CREATE TABLE `book_details` (
       `bookid` INT NOT NULL,
       `categoryid` INT NOT NULL,
       `title` VARCHAR(100) NOT NULL,
       `author` VARCHAR(200) NOT NULL,
       `publisher` VARCHAR(200) NOT NULL,
       `edition` VARCHAR(100) NOT NULL,
       `price` DECIMAL(7, 2) NOT NULL,
       `quantity` INT NOT NULL,
       `description` TEXT NOT NULL,
       PRIMARY KEY (`bookid`)
   ) ENGINE = InnoDB;
   
   CREATE TABLE `category_details` (
       `categoryid` INT(10) NOT NULL,
       `categoryname` VARCHAR(100) NOT NULL,
       PRIMARY KEY (`categoryid`)
   ) ENGINE = InnoDB;
   
   CREATE TABLE `order_details` (
       `orderid` INT NOT NULL,
       `bookid` INT NOT NULL,
       `quantity` INT NOT NULL
   ) ENGINE = InnoDB;
   
   CREATE TABLE `temp_detail` (
       `bookid` INT NOT NULL,
       `categoryid` INT NOT NULL,
       `title` TEXT NOT NULL,
       `author` TEXT NOT NULL,
       `publisher` TEXT NOT NULL,
       `edition` TEXT NOT NULL,
       `price` DECIMAL(7, 2) NOT NULL,
       `quantity` INT NOT NULL,
       `description` TEXT NOT NULL
   ) ENGINE = InnoDB;
   
   CREATE TABLE `user_auth` (
       `username` VARCHAR(50) NOT NULL,
       `password` VARCHAR(15) NOT NULL
   ) ENGINE = InnoDB;
   
   CREATE TABLE `user_profile` (
       `username` VARCHAR(50) NOT NULL,
       `password` VARCHAR(15) NOT NULL,
       `firstname` VARCHAR(25) NOT NULL,
       `middlename` VARCHAR(25) NULL,
       `lastname` VARCHAR(25) NOT NULL,
       `address1` VARCHAR(40) NOT NULL,
       `address2` VARCHAR(40) NULL,
       `city` VARCHAR(20) NOT NULL,
       `state` VARCHAR(20) NOT NULL,
       `pincode` VARCHAR(10) NOT NULL,
       `email` VARCHAR(50) NOT NULL,
       `phone` VARCHAR(12) NOT NULL,
       PRIMARY KEY (`username`)
   ) ENGINE = InnoDB;
   
   CREATE TABLE `order_table` (
       `orderid` INT NOT NULL,
       `userid` VARCHAR(50) NOT NULL,
       `totalamount` DECIMAL(15, 2) NOT NULL,
       `orderdate` VARCHAR(8) NOT NULL,
       PRIMARY KEY (`orderid`)
   ) ENGINE = InnoDB;
   ```

### 2. Configure Database Connection

Edit `src/main/resources/application.properties` and update the database credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bce-shopping-cart?autoReconnect=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password_here
```

### 3. Build and Run the Application

#### Option A: Using Maven (Recommended)

From the project root directory, run:

```bash
mvn clean spring-boot:run
```

This will:
- Compile the project
- Start the embedded Tomcat server
- Make the application available at `http://localhost:8080`

#### Option B: Build JAR and Run

1. Build the JAR file:
   ```bash
   mvn clean package
   ```

2. Run the JAR file:
   ```bash
   java -jar target/ShoppingCart.jar
   ```

### 4. Access the Application

Once the application is running, open your browser and navigate to:

```
http://localhost:8080
```

The application will be available at the root context path.

## Project Structure

- `src/main/java/com/p/bce/shopping/cart/` - Java source code
  - `ShoppingCartApplication.java` - Spring Boot main application class
  - `controller/` - Spring MVC controllers
  - `rpc/bc/` - Business classes (converted to Spring Services)
  - `rpc/dao/` - Data Access Objects (using Spring JdbcTemplate)
  - `rpc/pojo/` - Data Transfer Objects
- `src/main/webapp/` - Web resources (HTML, JSP, CSS, JS)
- `src/main/resources/` - Configuration files
  - `application.properties` - Spring Boot configuration
- `target/` - Compiled classes and JAR file (generated after build)

## Key Changes from Original Version

This application has been converted from a traditional servlet-based application to Spring Boot:

1. **Spring Boot Integration**: Uses Spring Boot for auto-configuration and embedded server
2. **Spring MVC Controllers**: Replaced direct JSP Java calls with Spring MVC controllers
3. **Spring JdbcTemplate**: DAOs now use Spring's JdbcTemplate instead of raw JDBC
4. **Dependency Injection**: Business classes and DAOs use Spring's `@Service` and `@Repository` annotations
5. **Configuration**: Database configuration moved to `application.properties` (Spring Boot standard)
6. **No External Tomcat**: Application runs with embedded Tomcat server

## Troubleshooting

1. **Database Connection Issues:**
   - Verify MySQL is running
   - Check database credentials in `src/main/resources/application.properties`
   - Ensure the database and tables are created
   - Verify the database name matches: `bce-shopping-cart`

2. **Port Already in Use:**
   - Change the port in `src/main/resources/application.properties`:
     ```properties
     server.port=8081
     ```

3. **JSP Not Rendering:**
   - Ensure JSP files are in `src/main/webapp/` directory
   - Check that `tomcat-embed-jasper` dependency is in `pom.xml`

4. **ClassNotFoundException:**
   - Run `mvn clean install` to ensure all dependencies are downloaded
   - Verify MySQL connector version in `pom.xml`

5. **Application Won't Start:**
   - Check the console for error messages
   - Verify Java version (JDK 8+ required)
   - Ensure MySQL server is running before starting the application

## Development

### Running in Development Mode

For development with auto-reload (requires Spring Boot DevTools - optional):

```bash
mvn spring-boot:run
```

### Building for Production

```bash
mvn clean package
```

This creates a standalone JAR file (`target/ShoppingCart.jar`) that can be deployed anywhere Java is installed.

### Making Changes

1. Edit source files in `src/main/java/`
2. Edit JSP/HTML files in `src/main/webapp/`
3. Restart the application (or use Spring Boot DevTools for auto-reload)

## Technology Stack

- **Framework**: Spring Boot 2.7.18
- **Web**: Spring MVC
- **View**: JSP with JSTL
- **Database**: MySQL 8.0+
- **Build Tool**: Maven
- **Java Version**: JDK 8+

## License

This is a sample application for educational purposes.
