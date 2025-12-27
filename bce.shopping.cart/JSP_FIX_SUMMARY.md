# JSP Rendering Fix - Summary

## Current Status
- ✅ Packaging: WAR
- ✅ JSP files: `src/main/webapp/WEB-INF/views/`
- ✅ View resolver configured in `WebConfig.java`
- ✅ Files copied to target directory
- ❌ JSPs still not rendering

## Critical Steps to Fix

### 1. **STOP the Application**
   - If running from IDE: Stop it completely
   - If running from command line: Press Ctrl+C

### 2. **Clean and Rebuild**
   ```bash
   mvn clean package -DskipTests
   ```

### 3. **Restart the Application**
   ```bash
   mvn spring-boot:run
   ```
   OR run from your IDE

### 4. **Verify You're Logged In**
   The `/pages/html/postLogin/SearchCriteria.jsp` endpoint requires authentication.
   - Make sure you're logged in first
   - Check session has "user" attribute

### 5. **Check Application Logs**
   Look for:
   - View resolver initialization
   - JSP compilation errors
   - Any exceptions when accessing the URL

## Common Issues

### Issue 1: Application Not Restarted
**Symptom**: Changes not taking effect
**Fix**: Completely stop and restart the application

### Issue 2: Not Logged In
**Symptom**: Redirects to Unauthorised page
**Fix**: Login first at `/pages/html/preLogin/Login.html`

### Issue 3: View Resolver Not Finding JSPs
**Symptom**: Whitelabel error page
**Fix**: 
- Verify JSPs are in `src/main/webapp/WEB-INF/views/`
- Check `WebConfig.java` has correct prefix: `/WEB-INF/views/`
- Ensure application.properties doesn't override view resolver

### Issue 4: JSP Compilation Errors
**Symptom**: Error in logs about JSP compilation
**Fix**: Check JSP syntax, especially JSP includes

## Debug Steps

1. **Enable Debug Logging** (already done in application.properties)
   ```
   logging.level.org.springframework.web.servlet.view=DEBUG
   ```

2. **Check Controller is Being Called**
   - Add a log statement in `SearchController.searchCriteria()`
   - Verify it's being executed

3. **Check View Resolution**
   - Look for logs about view resolution
   - Check if view name is being resolved correctly

4. **Verify JSP File Access**
   - Check if JSP file exists in target directory
   - Verify file permissions

## Next Steps

After restarting:
1. Try accessing: `http://localhost:8080/pages/html/postLogin/SearchCriteria.jsp`
2. Check browser console for errors
3. Check application logs for detailed error messages
4. Verify you're logged in (session has "user" attribute)

