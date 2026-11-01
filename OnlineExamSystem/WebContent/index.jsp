<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Exam System</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
    <%
        Object user = session.getAttribute("user");
        if (user != null) {
            String role = (String) session.getAttribute("role");
            if ("admin".equals(role)) {
                response.sendRedirect("admin.jsp");
            } else {
                response.sendRedirect("studentDashboard.jsp");
            }
        }
    %>

    <div class="container">
        <div class="home-container">
            <div class="welcome-box">
                <h1>Welcome to Online Exam System</h1>
                <p class="lead">A comprehensive platform for conducting online examinations</p>

                <div class="features">
                    <div class="feature-item">
                        <h3>📝 Multiple Choice Questions</h3>
                        <p>Answer carefully curated MCQ questions</p>
                    </div>
                    <div class="feature-item">
                        <h3>⏱️ Time Management</h3>
                        <p>Exam timer to manage your time effectively</p>
                    </div>
                    <div class="feature-item">
                        <h3>📊 Instant Results</h3>
                        <p>Get your results immediately after submission</p>
                    </div>
                    <div class="feature-item">
                        <h3>📈 Performance Tracking</h3>
                        <p>View your performance history and progress</p>
                    </div>
                </div>

                <div class="action-buttons">
                    <a href="login.jsp" class="btn btn-primary">Login</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
