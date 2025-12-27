<html>
    <head>
        <title>Edit Profile</title>
        <link rel="stylesheet" href="/pages/lib/css/main.css">
        <script src="/pages/lib/js/EditProfile.js"></script>
    </head>
    <body class="body-style">
        <h1 style="text-align: center;"><u>ONLINE SHOPPING CART</u></h1>
        <%@ page language="java" import="com.p.bce.shopping.cart.rpc.pojo.UserProfileDTO"%>
        
        <%
            UserProfileDTO profile = (UserProfileDTO) request.getAttribute("profile");
            String error = (String) request.getAttribute("error");
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
        
        <h2>Edit Profile</h2>
        
        <% if (profile != null) { %>
            <form name="editProfileFrm" method="POST" action="/pages/html/postLogin/UpdateProfile.jsp" onsubmit="return validate();">
                <table border="1" cellpadding="10" style="width: 100%; max-width: 800px;">
                    <tr>
                        <td width="25%"><b>Username:</b></td>
                        <td width="75%"><%= profile.getUserName() %> (cannot be changed)</td>
                    </tr>
                    <tr>
                        <td><b>First Name *:</b></td>
                        <td><input type="text" name="FirstName" value="<%= profile.getFirstName() %>" size="30" required></td>
                    </tr>
                    <tr>
                        <td><b>Middle Name:</b></td>
                        <td><input type="text" name="MiddleName" value="<%= profile.getMiddleName() != null ? profile.getMiddleName() : "" %>" size="30"></td>
                    </tr>
                    <tr>
                        <td><b>Last Name *:</b></td>
                        <td><input type="text" name="LastName" value="<%= profile.getLastName() %>" size="30" required></td>
                    </tr>
                    <tr>
                        <td><b>Email *:</b></td>
                        <td><input type="email" name="Email" value="<%= profile.getEmail() %>" size="30" required></td>
                    </tr>
                    <tr>
                        <td><b>Phone *:</b></td>
                        <td><input type="text" name="Phone" value="<%= profile.getPhone() %>" size="30" required></td>
                    </tr>
                    <tr>
                        <td><b>Address 1 *:</b></td>
                        <td><input type="text" name="Address1" value="<%= profile.getAddress1() %>" size="50" required></td>
                    </tr>
                    <tr>
                        <td><b>Address 2:</b></td>
                        <td><input type="text" name="Address2" value="<%= profile.getAddress2() != null ? profile.getAddress2() : "" %>" size="50"></td>
                    </tr>
                    <tr>
                        <td><b>City *:</b></td>
                        <td><input type="text" name="City" value="<%= profile.getCity() %>" size="30" required></td>
                    </tr>
                    <tr>
                        <td><b>State *:</b></td>
                        <td>
                            <select name="State" size="1" required>
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
                        </td>
                    </tr>
                    <tr>
                        <td><b>Pin Code *:</b></td>
                        <td><input type="text" name="PinCode" value="<%= profile.getPinCode() %>" size="10" required></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center;">
                            <input type="submit" value="Update Profile" name="Update" style="padding: 10px 20px; font-size: 16px;">
                            <input type="button" value="Cancel" onclick="window.location.href='/pages/html/postLogin/Profile.jsp'" style="padding: 10px 20px; font-size: 16px; margin-left: 10px;">
                        </td>
                    </tr>
                </table>
            </form>
        <% } else { %>
            <p style="color: red;">Profile not found.</p>
        <% } %>
    </body>
</html>

