<html>
    <head>
        <title>My Profile</title>
        <link rel="stylesheet" href="/pages/lib/css/main.css">
    </head>
    <body class="body-style">
        <h1 style="text-align: center;"><u>ONLINE SHOPPING CART</u></h1>
        <%@ page language="java" import="com.p.bce.shopping.cart.rpc.pojo.UserProfileDTO"%>
        
        <%
            UserProfileDTO profile = (UserProfileDTO) request.getAttribute("profile");
            String error = (String) request.getAttribute("error");
            String success = (String) request.getAttribute("success");
        %>
        
        <div style="text-align: right; margin: 10px;">
            <a href="/pages/html/postLogin/SearchCriteria.jsp">Home</a> | 
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
        
        <h2>My Profile</h2>
        
        <% if (profile != null) { %>
            <table border="1" cellpadding="10" style="width: 100%; max-width: 800px;">
                <tr>
                    <td><b>Username:</b></td>
                    <td><%= profile.getUserName() %></td>
                </tr>
                <tr>
                    <td><b>First Name:</b></td>
                    <td><%= profile.getFirstName() %></td>
                </tr>
                <tr>
                    <td><b>Middle Name:</b></td>
                    <td><%= profile.getMiddleName() != null ? profile.getMiddleName() : "" %></td>
                </tr>
                <tr>
                    <td><b>Last Name:</b></td>
                    <td><%= profile.getLastName() %></td>
                </tr>
                <tr>
                    <td><b>Email:</b></td>
                    <td><%= profile.getEmail() %></td>
                </tr>
                <tr>
                    <td><b>Phone:</b></td>
                    <td><%= profile.getPhone() %></td>
                </tr>
                <tr>
                    <td><b>Address 1:</b></td>
                    <td><%= profile.getAddress1() %></td>
                </tr>
                <tr>
                    <td><b>Address 2:</b></td>
                    <td><%= profile.getAddress2() != null ? profile.getAddress2() : "" %></td>
                </tr>
                <tr>
                    <td><b>City:</b></td>
                    <td><%= profile.getCity() %></td>
                </tr>
                <tr>
                    <td><b>State:</b></td>
                    <td><%= profile.getState() %></td>
                </tr>
                <tr>
                    <td><b>Pin Code:</b></td>
                    <td><%= profile.getPinCode() %></td>
                </tr>
            </table>
            
            <br>
            <div>
                <a href="/pages/html/postLogin/EditProfile.jsp">
                    <button style="padding: 10px 20px; font-size: 16px;">Edit Profile</button>
                </a>
                <a href="/pages/html/postLogin/ChangePassword.jsp">
                    <button style="padding: 10px 20px; font-size: 16px;">Change Password</button>
                </a>
            </div>
        <% } else { %>
            <p style="color: red;">Profile not found.</p>
        <% } %>
    </body>
</html>

