<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.p.bce.shopping.cart.rpc.pojo.CartItemDTO"%>
<%
    String userName = (String) session.getAttribute("user");
    boolean isLoggedIn = userName != null && !userName.isEmpty();
    String currentPage = request.getRequestURI();
    
    // Get cart item count
    int cartItemCount = 0;
    if (isLoggedIn) {
        @SuppressWarnings("unchecked")
        List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");
        if (cart != null) {
            cartItemCount = cart.size();
        }
    }
%>
<header class="site-header">
    <div class="header-container">
        <div class="header-logo">
            <a href="<%= isLoggedIn ? "/pages/html/postLogin/SearchCriteria.jsp" : "/" %>">
                <h1>📚 BCE Shopping Cart</h1>
            </a>
        </div>
        <div class="header-nav">
            <% if (isLoggedIn) { %>
                <div class="header-nav-content">
                    <div class="user-info">
                        <span class="user-name">👤 <%= userName %></span>
                    </div>
                    <nav class="main-nav">
                        <a href="/pages/html/postLogin/SearchCriteria.jsp" 
                           class="<%= currentPage.contains("SearchCriteria") || currentPage.contains("Search") ? "active" : "" %>">
                            🔍 Search Books
                        </a>
                        <a href="/pages/html/postLogin/Cart.jsp" 
                           class="<%= currentPage.contains("Cart") ? "active" : "" %>"
                           style="position: relative;">
                            🛒 Cart
                            <% if (cartItemCount > 0) { %>
                                <span class="cart-badge"><%= cartItemCount %></span>
                            <% } %>
                        </a>
                        <a href="/pages/html/postLogin/OrderHistory.jsp" 
                           class="<%= currentPage.contains("OrderHistory") || currentPage.contains("OrderDetails") ? "active" : "" %>">
                            📋 My Orders
                        </a>
                        <a href="/pages/html/postLogin/Profile.jsp" 
                           class="<%= currentPage.contains("Profile") || currentPage.contains("EditProfile") || currentPage.contains("ChangePassword") ? "active" : "" %>">
                            👤 Profile
                        </a>
                        <div class="nav-dropdown">
                            <a href="/admin" 
                               class="<%= currentPage.contains("/admin") ? "active" : "" %>">
                                ⚙️ Admin ▼
                            </a>
                            <div class="dropdown-content">
                                <a href="/admin">📊 Dashboard</a>
                                <a href="/admin/books">📚 Books</a>
                                <a href="/admin/categories">📁 Categories</a>
                                <a href="/admin/orders">📋 Orders</a>
                            </div>
                        </div>
                        <a href="/pages/html/postLogin/Logout.jsp" class="logout-link">🚪 Logout</a>
                    </nav>
                </div>
            <% } else { %>
                <nav class="main-nav">
                    <a href="/" class="<%= currentPage.equals("/") || currentPage.contains("index") ? "active" : "" %>">
                        🏠 Home
                    </a>
                    <a href="/pages/html/preLogin/Login.html" 
                       class="<%= currentPage.contains("Login") ? "active" : "" %>">
                        🔐 Login
                    </a>
                    <a href="/pages/html/preLogin/NewUser.html" 
                       class="<%= currentPage.contains("NewUser") ? "active" : "" %>">
                        ✍️ Sign Up
                    </a>
                </nav>
            <% } %>
        </div>
    </div>
</header>

<style>
.site-header {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
    padding: 15px 0;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    position: sticky;
    top: 0;
    z-index: 1000;
}

.header-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.header-logo h1 {
    margin: 0;
    font-size: 1.8em;
    color: white;
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
}

.header-logo a {
    text-decoration: none;
    color: white;
    transition: opacity 0.3s ease;
}

.header-logo a:hover {
    opacity: 0.9;
}

.header-nav {
    display: flex;
    align-items: center;
    gap: 20px;
}

.header-nav-content {
    display: flex;
    align-items: center;
    gap: 20px;
}

.user-info {
    padding: 8px 15px;
    background: rgba(255, 255, 255, 0.2);
    border-radius: 20px;
    font-weight: 500;
}

.user-name {
    color: white;
}

.main-nav {
    display: flex;
    gap: 10px;
    align-items: center;
    flex-wrap: wrap;
}

.main-nav a {
    color: white;
    text-decoration: none;
    padding: 8px 15px;
    border-radius: 5px;
    transition: all 0.3s ease;
    font-weight: 500;
    white-space: nowrap;
    display: inline-block;
}

.main-nav a:hover {
    background: rgba(255, 255, 255, 0.2);
    transform: translateY(-2px);
}

.main-nav a.active {
    background: rgba(255, 255, 255, 0.3);
    font-weight: 600;
}

.main-nav a.logout-link {
    background: rgba(220, 53, 69, 0.3);
}

.main-nav a.logout-link:hover {
    background: rgba(220, 53, 69, 0.5);
}

/* Dropdown Menu */
.nav-dropdown {
    position: relative;
    display: inline-block;
}

.nav-dropdown .dropdown-content {
    display: none;
    position: absolute;
    top: 100%;
    right: 0;
    background: white;
    min-width: 200px;
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
    border-radius: 8px;
    z-index: 1000;
    margin-top: 5px;
    overflow: hidden;
}

.nav-dropdown:hover .dropdown-content {
    display: block;
}

.dropdown-content a {
    color: #333;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
    transition: background 0.3s ease;
    border-bottom: 1px solid #f0f0f0;
}

.dropdown-content a:last-child {
    border-bottom: none;
}

.dropdown-content a:hover {
    background: #f8f9fa;
    color: #667eea;
    transform: none;
}

.dropdown-content a.active {
    background: #667eea;
    color: white;
}

/* Cart Badge */
.cart-badge {
    position: absolute;
    top: -5px;
    right: -5px;
    background: #dc3545;
    color: white;
    border-radius: 50%;
    width: 20px;
    height: 20px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 0.7em;
    font-weight: bold;
    border: 2px solid white;
}

@media (max-width: 768px) {
    .header-container {
        flex-direction: column;
        gap: 15px;
    }
    
    .header-logo h1 {
        font-size: 1.5em;
    }
    
    .header-nav-content {
        flex-direction: column;
        width: 100%;
        gap: 15px;
    }
    
    .main-nav {
        flex-wrap: wrap;
        justify-content: center;
        width: 100%;
    }
    
    .main-nav a {
        font-size: 0.85em;
        padding: 6px 10px;
    }
    
    .user-info {
        font-size: 0.9em;
        width: 100%;
        text-align: center;
    }
    
    .nav-dropdown .dropdown-content {
        right: auto;
        left: 50%;
        transform: translateX(-50%);
        min-width: 180px;
    }
}
</style>

