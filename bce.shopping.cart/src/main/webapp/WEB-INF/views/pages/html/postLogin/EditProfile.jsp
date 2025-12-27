<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit Profile - BCE Shopping Cart</title>
        <link rel="stylesheet" href="/pages/lib/css/main.css">
        <script src="/pages/lib/js/EditProfile.js"></script>
    </head>
    <body class="body-style">
        <div class="page-container">
            <div class="page-header">
                <h1>✏️ Edit Profile</h1>
            </div>

            <div class="nav-bar">
                <div class="nav-links">
                    <a href="/pages/html/postLogin/Profile.jsp">← Back to Profile</a>
                    <a href="/pages/html/postLogin/SearchCriteria.jsp">🏠 Home</a>
                    <a href="/pages/html/postLogin/Logout.jsp">🚪 Logout</a>
                </div>
            </div>
        
            <%@ page language="java" import="com.p.bce.shopping.cart.rpc.pojo.UserProfileDTO"%>
            
            <%
                UserProfileDTO profile = (UserProfileDTO) request.getAttribute("profile");
                String error = (String) request.getAttribute("error");
            %>
            
            <% if (error != null) { %>
                <div class="alert alert-error">
                    <strong>Error:</strong> <%= error %>
                </div>
            <% } %>
            
            <% if (profile != null) { %>
                <form name="editProfileFrm" method="POST" action="/pages/html/postLogin/UpdateProfile.jsp" 
                      onsubmit="return validate();" class="card">
                    <p style="color: #e74c3c; margin-bottom: 20px; font-weight: 600;">
                        <span style="color: #e74c3c;">*</span> Fields marked with asterisk are mandatory
                    </p>

                    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">
                        <div class="form-group" style="grid-column: 1 / -1;">
                            <label><b>Username:</b></label>
                            <div style="padding: 12px 15px; background: #f8f9fa; border-radius: 8px; color: #6c757d;">
                                <%= profile.getUserName() %> <small>(cannot be changed)</small>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="FirstName" class="required">First Name</label>
                            <input type="text" id="FirstName" name="FirstName" class="form-control" 
                                   value="<%= profile.getFirstName() %>" required>
                        </div>

                        <div class="form-group">
                            <label for="MiddleName">Middle Name</label>
                            <input type="text" id="MiddleName" name="MiddleName" class="form-control" 
                                   value="<%= profile.getMiddleName() != null ? profile.getMiddleName() : "" %>">
                        </div>

                        <div class="form-group">
                            <label for="LastName" class="required">Last Name</label>
                            <input type="text" id="LastName" name="LastName" class="form-control" 
                                   value="<%= profile.getLastName() %>" required>
                        </div>

                        <div class="form-group">
                            <label for="Email" class="required">Email</label>
                            <input type="email" id="Email" name="Email" class="form-control" 
                                   value="<%= profile.getEmail() %>" required>
                        </div>

                        <div class="form-group">
                            <label for="Phone" class="required">Phone</label>
                            <input type="text" id="Phone" name="Phone" class="form-control" 
                                   value="<%= profile.getPhone() %>" required>
                        </div>

                        <div class="form-group" style="grid-column: 1 / -1;">
                            <label for="Address1" class="required">Address Line 1</label>
                            <input type="text" id="Address1" name="Address1" class="form-control" 
                                   value="<%= profile.getAddress1() %>" required>
                        </div>

                        <div class="form-group" style="grid-column: 1 / -1;">
                            <label for="Address2">Address Line 2</label>
                            <input type="text" id="Address2" name="Address2" class="form-control" 
                                   value="<%= profile.getAddress2() != null ? profile.getAddress2() : "" %>">
                        </div>

                        <div class="form-group">
                            <label for="City" class="required">City</label>
                            <input type="text" id="City" name="City" class="form-control" 
                                   value="<%= profile.getCity() %>" required>
                        </div>

                        <div class="form-group">
                            <label for="State" class="required">State</label>
                            <select id="State" name="State" class="form-control" required>
                                <option value="<%= profile.getState() %>" selected><%= profile.getState() %></option>
                                <option value="Andra Pradesh">Andra Pradesh</option>
                                <option value="Arunachal Pradesh">Arunachal Pradesh</option>
                                <option value="Assam">Assam</option>
                                <option value="Bihar">Bihar</option>
                                <option value="Chhattisgarh">Chhattisgarh</option>
                                <option value="Goa">Goa</option>
                                <option value="Gujarat">Gujarat</option>
                                <option value="Haryana">Haryana</option>
                                <option value="Himachal Pradesh">Himachal Pradesh</option>
                                <option value="Jammu and Kashmir">Jammu and Kashmir</option>
                                <option value="Jharkhand">Jharkhand</option>
                                <option value="Karnataka">Karnataka</option>
                                <option value="Kerala">Kerala</option>
                                <option value="Madya Pradesh">Madya Pradesh</option>
                                <option value="Maharashtra">Maharashtra</option>
                                <option value="Manipur">Manipur</option>
                                <option value="Meghalaya">Meghalaya</option>
                                <option value="Mizoram">Mizoram</option>
                                <option value="Nagaland">Nagaland</option>
                                <option value="Orissa">Orissa</option>
                                <option value="Punjab">Punjab</option>
                                <option value="Rajasthan">Rajasthan</option>
                                <option value="Sikkim">Sikkim</option>
                                <option value="Tamil Nadu">Tamil Nadu</option>
                                <option value="Telagana">Telagana</option>
                                <option value="Tripura">Tripura</option>
                                <option value="Uttaranchal">Uttaranchal</option>
                                <option value="Uttar Pradesh">Uttar Pradesh</option>
                                <option value="West Bengal">West Bengal</option>
                                <option value="Andaman and Nicobar Islands">Andaman and Nicobar Islands</option>
                                <option value="Chandigarh">Chandigarh</option>
                                <option value="Dadar and Nagar Haveli">Dadar and Nagar Haveli</option>
                                <option value="Daman and Diu">Daman and Diu</option>
                                <option value="Delhi">Delhi</option>
                                <option value="Lakshadeep">Lakshadeep</option>
                                <option value="Pondicherry">Pondicherry</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="PinCode" class="required">Pin Code</label>
                            <input type="text" id="PinCode" name="PinCode" class="form-control" 
                                   value="<%= profile.getPinCode() %>" required>
                        </div>
                    </div>

                    <div class="btn-group">
                        <button type="submit" class="btn btn-primary">💾 Update Profile</button>
                        <a href="/pages/html/postLogin/Profile.jsp" class="btn btn-secondary">Cancel</a>
                    </div>
                </form>
            <% } else { %>
                <div class="alert alert-error">
                    Profile not found.
                </div>
            <% } %>
        </div>
    </body>
</html>
