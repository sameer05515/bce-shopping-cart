# Thymeleaf View Resolution Troubleshooting

## Issue
Getting "Whitelabel Error Page" when accessing `/pages/html/postLogin/SearchCriteria`

## Solution Applied

### 1. Controller Mappings Updated
All controllers now accept both URLs (with and without `.jsp` extension):
- `@GetMapping({"/pages/html/postLogin/SearchCriteria.jsp", "/pages/html/postLogin/SearchCriteria"})`

This allows backward compatibility with existing links while supporting cleaner URLs.

### 2. View Names Updated
All controller return statements now use Thymeleaf view names:
- `return "pages/postLogin/SearchCriteria";` (not `pages/html/postLogin/SearchCriteria`)

### 3. Template Location
Templates are in: `src/main/resources/templates/`
- Common: `templates/common/header.html`, `footer.html`
- User pages: `templates/pages/postLogin/*.html`
- Admin pages: `templates/admin/*.html`

## Required Actions

### 1. **STOP the Application**
   - If running from IDE: Stop it completely
   - If running from command line: Press Ctrl+C

### 2. **Clean and Rebuild**
   ```bash
   mvn clean compile
   ```
   Or for full build:
   ```bash
   mvn clean package
   ```

### 3. **Restart the Application**
   ```bash
   mvn spring-boot:run
   ```
   OR run from your IDE

### 4. **Verify You're Logged In**
   The `/pages/html/postLogin/SearchCriteria` endpoint requires authentication.
   - Make sure you're logged in first
   - Check session has "user" attribute

## Common Issues

### Issue 1: Application Not Restarted
**Symptom**: Changes not taking effect
**Fix**: Completely stop and restart the application

### Issue 2: Not Logged In
**Symptom**: Redirects to Unauthorised page
**Fix**: Login first at `/pages/html/preLogin/Login.html`

### Issue 3: Template Not Found
**Symptom**: Whitelabel error page
**Fix**: 
- Verify templates are in `src/main/resources/templates/`
- Check view name matches template path (without `.html`)
- Ensure `application.properties` has Thymeleaf configuration

### Issue 4: Compilation Errors
**Symptom**: Build fails
**Fix**: 
- Check for syntax errors in templates
- Verify all Thymeleaf expressions are correct
- Check controller return values

## Verification Checklist

- [ ] Application stopped and restarted
- [ ] Project rebuilt (`mvn clean compile`)
- [ ] Templates exist in `src/main/resources/templates/`
- [ ] `application.properties` has Thymeleaf config
- [ ] Controller mappings include both URLs (with/without `.jsp`)
- [ ] User is logged in (session has "user" attribute)
- [ ] Database is running and accessible

## Test URLs

After restart, test these URLs:
- `/pages/html/postLogin/SearchCriteria` or `/pages/html/postLogin/SearchCriteria.jsp`
- `/pages/html/postLogin/Profile` or `/pages/html/postLogin/Profile.jsp`
- `/pages/html/postLogin/Cart` or `/pages/html/postLogin/Cart.jsp`

Both URL formats should work now!

