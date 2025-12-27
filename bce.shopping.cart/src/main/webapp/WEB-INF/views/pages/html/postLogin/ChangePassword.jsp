<html>
    <head>
        <title>Change Password</title>
        <link rel="stylesheet" href="/pages/lib/css/main.css">
        <script src="/pages/lib/js/ChangePassword.js"></script>
    </head>
    <body class="body-style">
        <h1 style="text-align: center;"><u>ONLINE SHOPPING CART</u></h1>
        
        <%
            String error = (String) request.getAttribute("error");
            String success = (String) request.getAttribute("success");
        %>
        
        <div style="text-align: right; margin: 10px;">
            <a href="/pages/html/postLogin/Profile.jsp">Back to Profile</a> | 
            <a href="/pages/html/postLogin/Logout.jsp">Logout</a>
        </div>
        
        <% if (error != null) { %>
            <div style="color: red; padding: 10px; border: 1px solid red; margin: 10px;">
                <%= error %>
            </div>
        <% } %>
        
        <% if (success != null) { %>
            <div style="color: green; padding: 10px; border: 1px solid green; margin: 10px;">
                <%= success %>
            </div>
        <% } %>
        
        <h2>Change Password</h2>
        
        <div style="background-color: #f0f0f0; padding: 10px; margin: 10px 0; border: 1px solid #ccc;">
            <b>Password Requirements:</b>
            <ul style="margin: 5px 0;">
                <li>At least 8 characters long</li>
                <li>Contains at least one uppercase letter</li>
                <li>Contains at least one lowercase letter</li>
                <li>Contains at least one digit</li>
                <li>Contains at least one special character (@$!%*?&)</li>
            </ul>
        </div>
        
        <form name="changePasswordFrm" method="POST" action="/pages/html/postLogin/ChangePassword.jsp" onsubmit="return validate();">
            <table border="1" cellpadding="10" style="width: 100%; max-width: 600px;">
                <tr>
                    <td width="30%"><b>Current Password *:</b></td>
                    <td width="70%"><input type="password" name="CurrentPassword" size="30" required></td>
                </tr>
                <tr>
                    <td><b>New Password *:</b></td>
                    <td><input type="password" name="NewPassword" size="30" required></td>
                </tr>
                <tr>
                    <td><b>Confirm New Password *:</b></td>
                    <td><input type="password" name="ConfirmPassword" size="30" required></td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center;">
                        <input type="submit" value="Change Password" name="Change" style="padding: 10px 20px; font-size: 16px;">
                        <input type="button" value="Cancel" onclick="window.location.href='/pages/html/postLogin/Profile.jsp'" style="padding: 10px 20px; font-size: 16px; margin-left: 10px;">
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>

