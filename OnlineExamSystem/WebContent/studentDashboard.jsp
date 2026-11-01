<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Dashboard - Online Exam System</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
    <%
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String username = (String) session.getAttribute("username");
    %>

    <div class="container">
        <nav class="navbar">
            <div class="nav-brand">Online Exam System</div>
            <div class="nav-menu">
                <span>Welcome, <%= username %></span>
                <a href="result" class="nav-link">View Results</a>
                <a href="logout.jsp" class="nav-link">Logout</a>
            </div>
        </nav>

        <div class="dashboard-container">
            <h1>Student Dashboard</h1>

            <div class="dashboard-content">
                <div class="exams-section">
                    <h2>Available Exams</h2>
                    <div class="exams-grid">
                        <div class="exam-card">
                            <h3>Java Programming Exam</h3>
                            <p class="exam-details">Duration: 60 minutes</p>
                            <p class="exam-details">Total Questions: 50</p>
                            <a href="exam?examId=1" class="btn btn-primary">Start Exam</a>
                        </div>

                        <div class="exam-card">
                            <h3>Web Development Exam</h3>
                            <p class="exam-details">Duration: 45 minutes</p>
                            <p class="exam-details">Total Questions: 40</p>
                            <a href="exam?examId=2" class="btn btn-primary">Start Exam</a>
                        </div>

                        <div class="exam-card">
                            <h3>Database Management Exam</h3>
                            <p class="exam-details">Duration: 50 minutes</p>
                            <p class="exam-details">Total Questions: 45</p>
                            <a href="exam?examId=3" class="btn btn-primary">Start Exam</a>
                        </div>
                    </div>
                </div>

                <div class="quick-links">
                    <h2>Quick Links</h2>
                    <ul>
                        <li><a href="result">View Result History</a></li>
                        <li><a href="index.jsp">Home</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
