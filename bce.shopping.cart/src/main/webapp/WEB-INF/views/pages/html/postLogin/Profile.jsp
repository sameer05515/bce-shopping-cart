<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>My Profile - BCE Shopping Cart</title>
        <link rel="stylesheet" href="/pages/lib/css/main.css">
    </head>
    <body class="body-style">
        <div class="page-container">
            <div class="page-header">
                <h1>👤 My Profile</h1>
            </div>

            <div class="nav-bar">
                <div class="nav-links">
                    <a href="/pages/html/postLogin/SearchCriteria.jsp">🏠 Home</a>
                    <a href="/pages/html/postLogin/Logout.jsp">🚪 Logout</a>
                </div>
            </div>
        
            <%@ page language="java" import="com.p.bce.shopping.cart.rpc.pojo.UserProfileDTO"%>
            
            <%
                UserProfileDTO profile = (UserProfileDTO) request.getAttribute("profile");
                String error = (String) request.getAttribute("error");
                String success = (String) request.getAttribute("success");
            %>
            
            <% if (error != null) { %>
                <div class="alert alert-error">
                    <strong>Error:</strong> <%= error %>
                </div>
            <% } %>
            
            <% if (success != null) { %>
                <div class="alert alert-success">
                    <strong>Success:</strong> <%= success %>
                </div>
            <% } %>
            
            <% if (profile != null) { %>
                <div class="profile-card">
                    <div class="card-header">Personal Information</div>
                    <div class="profile-info">
                        <div class="profile-label">Username:</div>
                        <div class="profile-value"><%= profile.getUserName() %></div>
                    </div>
                    <div class="profile-info">
                        <div class="profile-label">First Name:</div>
                        <div class="profile-value"><%= profile.getFirstName() %></div>
                    </div>
                    <div class="profile-info">
                        <div class="profile-label">Middle Name:</div>
                        <div class="profile-value"><%= profile.getMiddleName() != null && !profile.getMiddleName().isEmpty() ? profile.getMiddleName() : "—" %></div>
                    </div>
                    <div class="profile-info">
                        <div class="profile-label">Last Name:</div>
                        <div class="profile-value"><%= profile.getLastName() %></div>
                    </div>
                    <div class="profile-info">
                        <div class="profile-label">Email:</div>
                        <div class="profile-value"><%= profile.getEmail() %></div>
                    </div>
                    <div class="profile-info">
                        <div class="profile-label">Phone:</div>
                        <div class="profile-value"><%= profile.getPhone() %></div>
                    </div>
                </div>

                <div class="profile-card">
                    <div class="card-header">Address Information</div>
                    <div class="profile-info">
                        <div class="profile-label">Address Line 1:</div>
                        <div class="profile-value"><%= profile.getAddress1() %></div>
                    </div>
                    <div class="profile-info">
                        <div class="profile-label">Address Line 2:</div>
                        <div class="profile-value"><%= profile.getAddress2() != null && !profile.getAddress2().isEmpty() ? profile.getAddress2() : "—" %></div>
                    </div>
                    <div class="profile-info">
                        <div class="profile-label">City:</div>
                        <div class="profile-value"><%= profile.getCity() %></div>
                    </div>
                    <div class="profile-info">
                        <div class="profile-label">State:</div>
                        <div class="profile-value"><%= profile.getState() %></div>
                    </div>
                    <div class="profile-info">
                        <div class="profile-label">Pin Code:</div>
                        <div class="profile-value"><%= profile.getPinCode() %></div>
                    </div>
                </div>
                
                <div class="btn-group">
                    <a href="/pages/html/postLogin/EditProfile.jsp" class="btn btn-primary">
                        ✏️ Edit Profile
                    </a>
                    <a href="/pages/html/postLogin/ChangePassword.jsp" class="btn btn-secondary">
                        🔒 Change Password
                    </a>
                </div>
            <% } else { %>
                <div class="alert alert-error">
                    Profile not found.
                </div>
            <% } %>
        </div>
    </body>
</html>

