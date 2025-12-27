# Whitelabel Error Debugging Guide

## Common Causes of Whitelabel Error

1. **Template Not Found**
   - Template path doesn't match view name
   - Template file missing or in wrong location
   - Thymeleaf configuration incorrect

2. **Exception During Rendering**
   - Database connection error
   - Null pointer exception
   - Template syntax error
   - Missing model attributes

3. **Controller Not Found**
   - URL mapping doesn't match
   - Controller not scanned by Spring
   - Request method mismatch (GET vs POST)

4. **Application Not Restarted**
   - Changes not compiled
   - Old classes still in memory

## Debug Steps

### Step 1: Check Application Logs
Look for:
- `TemplateInputException` - Template not found
- `NullPointerException` - Missing data
- `SQLException` - Database connection issue
- `BeanCreationException` - Spring configuration issue

### Step 2: Verify Template Exists
```bash
# Check if template file exists
Test-Path "src\main\resources\templates\pages\postLogin\SearchCriteria.html"
Test-Path "target\classes\templates\pages\postLogin\SearchCriteria.html"
```

### Step 3: Check Controller Mapping
Verify the URL matches the `@GetMapping` or `@PostMapping` annotation.

### Step 4: Test Database Connection
The controller calls `categoryDetailsBC.getAllCategoryDetails()` which requires database access.

### Step 5: Add Error Handling
Add try-catch blocks and logging to identify the exact error.

## Quick Fix Checklist

- [ ] Application fully stopped and restarted
- [ ] `mvn clean compile` executed successfully
- [ ] Templates exist in `src/main/resources/templates/`
- [ ] Database is running and accessible
- [ ] User is logged in (for protected endpoints)
- [ ] Check application logs for specific error messages

