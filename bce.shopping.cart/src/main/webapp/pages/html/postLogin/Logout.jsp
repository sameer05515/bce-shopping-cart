<html>
    <head>
        <title>Logged out</title>
        <link rel="stylesheet" href="../../lib/css/main.css">
        <script src="../../lib/js/SearchItems.js"></script>
    </head>

    <body class="body-style">
        <h1 style="text-align: center;"><u>ONLINE SHOPPING CART</u></h1>
        <%@ page language="java" %>
        <%
            session.invalidate();
        %>

        <br><br>
        <span><b>You have been logged out successfully.
            <br><br>
            Return to Shopping Cart? <a href="../preLogin/Login.html">Click Here</a>
        </b></span>
    </body>

</html>