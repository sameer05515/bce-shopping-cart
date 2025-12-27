<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Change Password - BCE Shopping Cart</title>
        <link rel="stylesheet" href="/pages/lib/css/main.css">
        <script src="/pages/lib/js/ChangePassword.js"></script>
    </head>
    <body class="body-style">
        <div class="page-container">
            <div class="page-header">
                <h1>🔒 Change Password</h1>
            </div>

            <div class="nav-bar">
                <div class="nav-links">
                    <a href="/pages/html/postLogin/Profile.jsp">← Back to Profile</a>
                    <a href="/pages/html/postLogin/SearchCriteria.jsp">🏠 Home</a>
                    <a href="/pages/html/postLogin/Logout.jsp">🚪 Logout</a>
                </div>
            </div>
        
            <%
                String error = (String) request.getAttribute("error");
                String message = (String) request.getAttribute("message");
            %>
            
            <% if (error != null) { %>
                <div class="alert alert-error">
                    <strong>Error:</strong> <%= error %>
                </div>
            <% } %>
            
            <% if (message != null) { %>
                <div class="alert alert-success">
                    <strong>Success:</strong> <%= message %>
                </div>
            <% } %>
            
            <div class="password-requirements">
                <h4>Password Requirements:</h4>
                <ul>
                    <li>At least 8 characters long</li>
                    <li>Contains at least one uppercase letter (A-Z)</li>
                    <li>Contains at least one lowercase letter (a-z)</li>
                    <li>Contains at least one digit (0-9)</li>
                    <li>Contains at least one special character (@$!%*?&)</li>
                </ul>
            </div>
            
            <form name="changePasswordFrm" method="POST" action="/pages/html/postLogin/ChangePassword.jsp" 
                  onsubmit="return validate();" class="card">
                <div class="form-group">
                    <label for="CurrentPassword" class="required">Current Password</label>
                    <input type="password" id="CurrentPassword" name="CurrentPassword" class="form-control" 
                           placeholder="Enter your current password" required>
                </div>

                <div class="form-group">
                    <label for="NewPassword" class="required">New Password</label>
                    <input type="password" id="NewPassword" name="NewPassword" class="form-control" 
                           placeholder="Enter new password" required>
                </div>

                <div class="form-group">
                    <label for="ConfirmPassword" class="required">Confirm New Password</label>
                    <input type="password" id="ConfirmPassword" name="ConfirmPassword" class="form-control" 
                           placeholder="Re-enter new password" required>
                </div>

                <div class="btn-group">
                    <button type="submit" class="btn btn-primary">🔒 Change Password</button>
                    <a href="/pages/html/postLogin/Profile.jsp" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </body>
</html>
