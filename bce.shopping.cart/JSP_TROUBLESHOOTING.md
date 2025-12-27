# JSP View Resolution Troubleshooting

## Issue
Getting "Whitelabel Error Page" when accessing JSP pages after moving webapp to resources.

## Root Cause
Spring Boot with JAR packaging has known limitations with JSP. The `InternalResourceViewResolver` uses `RequestDispatcher.forward()`, which needs files to be accessible via the servlet context.

## Solution Steps

### 1. Verify JSP Files Location
JSP files should be in:
```
src/main/resources/META-INF/resources/WEB-INF/views/
```

### 2. Rebuild the Project
After moving files, rebuild the project:
```bash
mvn clean package
```

Or for development:
```bash
mvn clean compile
```

### 3. Restart the Application
**Important**: After moving files and rebuilding, restart the Spring Boot application completely.

### 4. Verify Configuration

#### WebConfig.java
- View resolver prefix: `/WEB-INF/views/`
- View resolver suffix: `.jsp`
- Resource handlers include: `classpath:/META-INF/resources/`

#### application.properties
- `spring.mvc.view.prefix=/WEB-INF/views/`
- `spring.mvc.view.suffix=.jsp`

### 5. Check Controller Return Values
Controllers should return view names without `.jsp`:
```java
return "pages/html/postLogin/SearchCriteria"; // Correct
// NOT: return "pages/html/postLogin/SearchCriteria.jsp";
```

### 6. Development vs Production

#### Development (IDE / mvn spring-boot:run)
- JSPs should work from `META-INF/resources/WEB-INF/views/`
- Files are on file system, accessible to JSP compiler

#### Production (JAR)
- JSPs have limitations in JAR packaging
- Consider using WAR packaging for production if JSPs are required
- Or migrate to Thymeleaf (recommended by Spring Boot)

## Alternative: Use WAR Packaging

If JSPs don't work in JAR mode, switch to WAR packaging:

### pom.xml
```xml
<packaging>war</packaging>
```

### Move JSPs back to webapp
For WAR packaging, JSPs should be in:
```
src/main/webapp/WEB-INF/views/
```

## Current Status

✅ JSP files are in correct location: `src/main/resources/META-INF/resources/WEB-INF/views/`
✅ Configuration is correct
✅ Files are being copied to target directory
⚠️ **Action Required**: Restart the application after rebuild

## Next Steps

1. **Stop the application** if it's running
2. **Rebuild**: `mvn clean package` or `mvn clean compile`
3. **Restart**: Start the application fresh
4. **Test**: Access `http://localhost:8080/pages/html/postLogin/SearchCriteria.jsp`

If the issue persists after restart, check:
- Application logs for specific errors
- Whether you're logged in (session required)
- Database connection (required for categories)

