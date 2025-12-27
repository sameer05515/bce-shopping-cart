<%@ page language="java" %>
<%
    String userName = (String) session.getAttribute("user");
    boolean isLoggedIn = userName != null && !userName.isEmpty();
    String currentPage = request.getRequestURI();
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
                <div class="user-info">
                    <span class="user-name">👤 <%= userName %></span>
                </div>
                <nav class="main-nav">
                    <a href="/pages/html/postLogin/SearchCriteria.jsp" 
                       class="<%= currentPage.contains("SearchCriteria") || currentPage.contains("Search") ? "active" : "" %>">
                        🔍 Search
                    </a>
                    <a href="/pages/html/postLogin/Profile.jsp" 
                       class="<%= currentPage.contains("Profile") ? "active" : "" %>">
                        👤 Profile
                    </a>
                    <a href="/admin" 
                       class="<%= currentPage.contains("/admin") ? "active" : "" %>">
                        ⚙️ Admin
                    </a>
                    <a href="/pages/html/postLogin/Logout.jsp">🚪 Logout</a>
                </nav>
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
    gap: 15px;
    align-items: center;
}

.main-nav a {
    color: white;
    text-decoration: none;
    padding: 8px 15px;
    border-radius: 5px;
    transition: all 0.3s ease;
    font-weight: 500;
    white-space: nowrap;
}

.main-nav a:hover {
    background: rgba(255, 255, 255, 0.2);
    transform: translateY(-2px);
}

.main-nav a.active {
    background: rgba(255, 255, 255, 0.3);
    font-weight: 600;
}

@media (max-width: 768px) {
    .header-container {
        flex-direction: column;
        gap: 15px;
    }
    
    .header-logo h1 {
        font-size: 1.5em;
    }
    
    .main-nav {
        flex-wrap: wrap;
        justify-content: center;
    }
    
    .main-nav a {
        font-size: 0.9em;
        padding: 6px 12px;
    }
    
    .user-info {
        font-size: 0.9em;
    }
}
</style>

