<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Exam System - Login</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
    <div class="container">
        <div class="login-container">
            <div class="login-box">
                <h1>Online Exam System</h1>
                <p class="subtitle">Student & Admin Portal</p>

                <% String error = (String) request.getAttribute("error");
                   if (error != null) { %>
                    <div class="alert alert-error">
                        <%= error %>
                    </div>
                <% } %>

                <form action="login" method="POST">
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="text" id="username" name="username" required
                               placeholder="Enter your username">
                    </div>

                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" required
                               placeholder="Enter your password">
                    </div>

                    <button type="submit" class="btn btn-primary">Login</button>
                </form>

                <div class="demo-credentials">
                    <p><strong>Demo Credentials:</strong></p>
                    <p>Student: student1 / password123</p>
                    <p>Admin: admin1 / admin123</p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
