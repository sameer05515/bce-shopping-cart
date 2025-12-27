# BCE Shopping Cart Application - Functionalities

## Overview

This is a Spring Boot-based online shopping cart application for books. The application provides user authentication, book search functionality, and shopping cart management. It has been converted from a traditional servlet-based application to Spring Boot with modern architecture.

## Table of Contents

1. [User Management](#user-management)
2. [Book Search & Browsing](#book-search--browsing)
3. [Shopping Cart](#shopping-cart)
4. [Database Operations](#database-operations)
5. [API Endpoints](#api-endpoints)
6. [User Interface Pages](#user-interface-pages)
7. [Technical Architecture](#technical-architecture)

---

## User Management

### 1. User Registration
- **Functionality**: New users can create an account
- **Endpoint**: `POST /pages/jsp/Sign.jsp`
- **Controller**: `UserController.signUp()`
- **Service**: `UserProfileBC.save()`
- **DAO**: `UserProfileDAO.save()`
- **Features**:
  - Username uniqueness validation
  - Password confirmation
  - Complete user profile creation including:
    - Personal information (First Name, Middle Name, Last Name)
    - Address details (Address1, Address2, City, State, PinCode)
    - Contact information (Email, Phone)
  - Data stored in both `user_auth` and `user_profile` tables
  - Client-side validation via JavaScript (`NewUser.js`)
  - Server-side validation for duplicate usernames

### 2. User Authentication (Login)
- **Functionality**: Existing users can log in to the system
- **Endpoint**: `POST /pages/jsp/Validate.jsp`
- **Controller**: `UserController.validateUser()`
- **Service**: `UserProfileBC.validate()`
- **DAO**: `UserProfileDAO.validate()`
- **Features**:
  - Username and password validation
  - Session management (stores username in session)
  - Role-based redirection:
    - Administrator users → Admin page
    - Regular users → Search Criteria page
  - Invalid credentials handling (redirects to InvalidUser page)
  - Client-side validation via JavaScript (`Login.js`)

### 3. User Logout
- **Functionality**: Users can log out from the system
- **Endpoint**: `GET /pages/html/postLogin/Logout.jsp`
- **Controller**: `UserController.logout()`
- **Features**:
  - Session invalidation
  - Redirects to login page

### 4. Username Existence Check
- **Functionality**: Validates if a username already exists during registration
- **Service**: `UserProfileBC.keyExists()`
- **DAO**: `UserProfileDAO.keyExists()`
- **Features**:
  - Prevents duplicate usernames
  - Returns boolean result

---

## Book Search & Browsing

### 1. Search Criteria Page
- **Functionality**: Displays search options for books
- **Endpoint**: `GET /pages/html/postLogin/SearchCriteria.jsp`
- **Controller**: `SearchController.searchCriteria()`
- **Service**: `CategoryDetailsBC.getAllCategoryDetails()`
- **DAO**: `CategoryDetailsDAO.getAllCategoryDetails()`
- **Features**:
  - Displays all available book categories
  - Provides multiple search options:
    - Search by Category (dropdown)
    - Search by Book Title (text input)
    - Search by Book Author (text input)
    - Search by Book Publisher (text input)
  - Session validation (requires login)
  - Client-side validation via JavaScript (`SearchItems.js`)

### 2. Book Search
- **Functionality**: Searches books based on user criteria
- **Endpoint**: `POST /pages/html/postLogin/Search.jsp`
- **Controller**: `SearchController.search()`
- **Service**: `CategoryDetailsBC.getSearchedBookCategories()`
- **DAO**: `CategoryDetailsDAO.getSearchedBookCategories()`
- **Features**:
  - **Search Types Supported**:
    - By Category Name
    - By Book Title
    - By Book Author
    - By Book Publisher
  - **Search Modes**:
    - New Search: Searches in `book_details` table, clears temp results
    - Advanced Search: Searches in `temp_detail` table (refined search)
  - Case-insensitive search (UPPER function)
  - Results include:
    - Book ID
    - Category ID and Name
    - Title, Author, Publisher, Edition
    - Price, Quantity Available
    - Description
  - Automatic storage of search results in `temp_detail` table
  - Session validation (requires login)

### 3. Search Results Display
- **Functionality**: Displays search results with book details
- **View**: `Search.jsp`
- **Features**:
  - Tabular display of search results
  - Checkbox selection for adding books to cart
  - Shows all book details (Title, Author, Publisher, Edition, Price, Quantity, Description, Category)
  - Sequential numbering of results
  - Option to perform additional searches
  - Add to Cart functionality
  - Logout option

### 4. Get All Categories
- **Functionality**: Retrieves all book categories from database
- **Service**: `CategoryDetailsBC.getAllCategoryDetails()`
- **DAO**: `CategoryDetailsDAO.getAllCategoryDetails()`
- **Returns**: List of `CategoryDetailsDTO` objects containing CategoryId and CategoryName

### 5. Get Book Details
- **Functionality**: Retrieves detailed information about a specific book
- **Service**: `BookDetailsBC.getBookDetail()`
- **DAO**: `BookDetailsDAO.getBookDetail()`
- **Returns**: `BookDetailDTO` object with complete book information

---

## Shopping Cart

### 1. Temporary Search Results Management
- **Functionality**: Manages temporary storage of search results
- **Service**: `CategoryDetailsBC.insertIntoTempDetails()`
- **DAO**: `CategoryDetailsDAO.insertIntoTempDetails()`
- **Features**:
  - Stores search results in `temp_detail` table
  - Enables advanced/refined searches
  - Allows multiple search iterations

### 2. Clear Temporary Results
- **Functionality**: Clears old temporary search results
- **Service**: `CategoryDetailsBC.deleteOldTempDetails()`
- **DAO**: `CategoryDetailsDAO.deleteOldTempDetails()`
- **Features**:
  - Deletes all records from `temp_detail` table
  - Called before new searches

### 3. Add to Cart (Partial Implementation)
- **Functionality**: Selected books can be added to cart
- **View**: `Inter_Cart.jsp` (referenced but not fully implemented)
- **Features**:
  - Checkbox-based book selection
  - Session storage of selected items
  - Cart page display (`Cart.jsp`)

---

## Database Operations

### Database Tables

1. **user_auth**
   - Stores username and password for authentication
   - Fields: username, password

2. **user_profile**
   - Stores complete user profile information
   - Fields: username, password, firstname, middlename, lastname, address1, address2, city, state, pincode, email, phone
   - Primary Key: username

3. **book_details**
   - Stores book information
   - Fields: bookid, categoryid, title, author, publisher, edition, price, quantity, description
   - Primary Key: bookid

4. **category_details**
   - Stores book categories
   - Fields: categoryid, categoryname
   - Primary Key: categoryid

5. **temp_detail**
   - Temporary storage for search results
   - Fields: bookid, categoryid, title, author, publisher, edition, price, quantity, description

6. **order_details**
   - Stores order line items
   - Fields: orderid, bookid, quantity

7. **order_table**
   - Stores order header information
   - Fields: orderid, userid, totalamount, orderdate
   - Primary Key: orderid

### Data Access Layer

All DAOs use Spring's `JdbcTemplate` for database operations:
- **UserProfileDAO**: User authentication and profile management
- **CategoryDetailsDAO**: Category and book search operations
- **BookDetailsDAO**: Book detail retrieval

---

## API Endpoints

### Spring MVC Controllers

#### HomeController
- `GET /` → Redirects to index.html

#### UserController
- `POST /pages/jsp/Validate.jsp` → User login validation
- `POST /pages/jsp/Sign.jsp` → User registration
- `GET /pages/html/postLogin/Logout.jsp` → User logout

#### SearchController
- `GET /pages/html/postLogin/SearchCriteria.jsp` → Display search criteria page
- `POST /pages/html/postLogin/Search.jsp` → Perform book search

### Static Resources
All static HTML, CSS, and JavaScript files are served from `src/main/resources/static/`

### JSP Views
JSP files are located in `src/main/webapp/WEB-INF/views/` and rendered by Spring MVC

---

## User Interface Pages

### Pre-Login Pages

1. **index.html** (`/`)
   - Welcome page
   - Links to Help, Sign Up, and Login

2. **Login.html** (`/pages/html/preLogin/Login.html`)
   - User login form
   - Username and password fields
   - Link to registration page
   - Client-side validation

3. **NewUser.html** (`/pages/html/preLogin/NewUser.html`)
   - User registration form
   - Complete profile fields
   - State dropdown (Indian states)
   - Client-side validation for all fields
   - Password confirmation

4. **InvalidUser.html** (`/pages/html/preLogin/InvalidUser.html`)
   - Displayed when login fails
   - Options to create new account or login again

5. **Unauthorised.html** (`/pages/html/preLogin/Unauthorised.html`)
   - Displayed when accessing protected pages without login
   - Link to login page

6. **ReadMe.html** (`/help/ReadMe.html`)
   - Database schema documentation
   - Table creation scripts
   - Application help

### Post-Login Pages

1. **SearchCriteria.jsp** (`/pages/html/postLogin/SearchCriteria.jsp`)
   - Book search interface
   - Multiple search options
   - Category dropdown
   - Logout link

2. **Search.jsp** (`/pages/html/postLogin/Search.jsp`)
   - Search results display
   - Book selection checkboxes
   - Add to Cart button
   - Additional search form
   - Logout link

3. **Admin.html** (`/pages/html/postLogin/Admin.html`)
   - Administrator area (placeholder)

4. **Logout.jsp** (`/pages/html/postLogin/Logout.jsp`)
   - Logout confirmation
   - Link back to login

### Legacy JSP Pages (Still Functional)

1. **Validate.jsp** (`/pages/jsp/Validate.jsp`)
   - Legacy login processing (still works via ShoppingApiRPC)
   - Uses Spring-managed beans

2. **Sign.jsp** (`/pages/jsp/Sign.jsp`)
   - Legacy registration processing (still works via ShoppingApiRPC)
   - Uses Spring-managed beans

---

## Technical Architecture

### Framework & Technologies
- **Framework**: Spring Boot 2.7.18
- **Web Framework**: Spring MVC
- **View Technology**: JSP with JSTL
- **Database**: MySQL 8.0+
- **Data Access**: Spring JDBC (JdbcTemplate)
- **Build Tool**: Maven
- **Java Version**: JDK 8+

### Architecture Layers

1. **Controller Layer** (`com.p.bce.shopping.cart.controller`)
   - Handles HTTP requests
   - Manages session
   - Returns view names or redirects
   - Controllers: `HomeController`, `UserController`, `SearchController`

2. **Service Layer** (`com.p.bce.shopping.cart.rpc.bc`)
   - Business logic implementation
   - Transaction management
   - Services: `UserProfileBC`, `CategoryDetailsBC`, `BookDetailsBC`
   - Annotated with `@Service`

3. **Data Access Layer** (`com.p.bce.shopping.cart.rpc.dao`)
   - Database operations
   - Uses Spring JdbcTemplate
   - DAOs: `UserProfileDAO`, `CategoryDetailsDAO`, `BookDetailsDAO`
   - Annotated with `@Repository`

4. **DTO Layer** (`com.p.bce.shopping.cart.rpc.pojo`)
   - Data Transfer Objects
   - DTOs: `UserProfileDTO`, `UserAuthDTO`, `CategoryDetailsDTO`, `BookDetailDTO`, `SearchedBookCategories`

5. **RPC Layer** (`com.p.bce.shopping.cart.rpc`)
   - Legacy API compatibility layer
   - `ShoppingApiRPC`: Provides static methods for backward compatibility
   - Uses Spring ApplicationContext to inject beans

### Configuration

1. **Application Properties** (`application.properties`)
   - Database connection settings
   - Server port configuration
   - Logging configuration

2. **Web Configuration** (`WebConfig.java`)
   - JSP view resolver configuration
   - Static resource handlers
   - View prefix: `/WEB-INF/views/`
   - View suffix: `.jsp`

### Dependency Injection
- All services and repositories use Spring's `@Autowired` annotation
- Spring manages bean lifecycle and dependencies
- Singleton scope by default

### Session Management
- HTTP Session used for user authentication
- Session attributes:
  - `user`: Current logged-in username
  - `ctr_val`: Counter for search results

### Security Features
- Session-based authentication
- Protected routes (require login)
- Password storage (plain text - should be encrypted in production)
- Username uniqueness validation

---

## Known Limitations & Future Enhancements

### Current Limitations
1. Passwords stored in plain text (should use encryption/hashing)
2. Shopping cart functionality partially implemented
3. Order processing not fully implemented
4. No admin functionality beyond placeholder page
5. No pagination for search results
6. No image support for books
7. Limited error handling and user feedback

### Potential Enhancements
1. Implement password encryption (BCrypt)
2. Complete shopping cart functionality
3. Order placement and management
4. Admin panel for book management
5. User profile management
6. Book reviews and ratings
7. Payment gateway integration
8. Email notifications
9. Search result pagination
10. Book image uploads
11. Advanced search filters
12. Wishlist functionality

---

## File Structure

```
src/main/
├── java/com/p/bce/shopping/cart/
│   ├── ShoppingCartApplication.java    # Spring Boot main class
│   ├── config/
│   │   └── WebConfig.java              # Web configuration
│   ├── controller/
│   │   ├── HomeController.java         # Home page controller
│   │   ├── UserController.java         # User management controller
│   │   └── SearchController.java       # Search controller
│   ├── rpc/
│   │   ├── ShoppingApiRPC.java         # Legacy RPC layer
│   │   ├── bc/                          # Business classes (Services)
│   │   ├── dao/                         # Data Access Objects
│   │   └── pojo/                        # Data Transfer Objects
│   └── util/                            # Utility classes
├── resources/
│   ├── application.properties          # Application configuration
│   └── static/                          # Static resources (HTML, CSS, JS)
│       ├── index.html
│       ├── help/
│       └── pages/
└── webapp/
    ├── WEB-INF/
    │   ├── views/                       # JSP views
    │   └── conf/                        # Configuration files
    └── pages/jsp/                       # Legacy JSP files
```

---

## Summary

The BCE Shopping Cart Application is a functional Spring Boot web application that provides:

✅ **User Management**: Registration, Login, Logout  
✅ **Book Search**: Multiple search criteria (Category, Title, Author, Publisher)  
✅ **Search Results**: Display and management  
✅ **Session Management**: Secure user sessions  
✅ **Database Integration**: MySQL with Spring JDBC  
✅ **Modern Architecture**: Spring Boot with MVC pattern  
✅ **Backward Compatibility**: Legacy JSP support via ShoppingApiRPC  

The application is ready for use and can be extended with additional features as needed.

