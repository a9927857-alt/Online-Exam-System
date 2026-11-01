<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Exam Result - Online Exam System</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
    <%
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Integer correctAnswers = (Integer) session.getAttribute("correctAnswers");
        Integer totalQuestions = (Integer) session.getAttribute("totalQuestions");
        Double percentage = (Double) session.getAttribute("percentage");

        String resultStatus = "FAILED";
        if (percentage != null && percentage >= 50) {
            resultStatus = "PASSED";
        }
    %>

    <div class="container">
        <div class="result-container">
            <div class="result-header">
                <h1>Exam Result</h1>
            </div>

            <div class="result-card">
                <div class="result-status <%= resultStatus.toLowerCase() %>">
                    <h2><%= resultStatus %></h2>
                </div>

                <div class="result-details">
                    <div class="detail-item">
                        <label>Total Questions:</label>
                        <span><%= totalQuestions %></span>
                    </div>

                    <div class="detail-item">
                        <label>Correct Answers:</label>
                        <span><%= correctAnswers %></span>
                    </div>

                    <div class="detail-item">
                        <label>Wrong Answers:</label>
                        <span><%= (totalQuestions - correctAnswers) %></span>
                    </div>

                    <div class="detail-item">
                        <label>Score:</label>
                        <span class="score"><%= String.format("%.2f", percentage) %>%</span>
                    </div>
                </div>

                <div class="progress-bar">
                    <div class="progress" style="width: <%= percentage %>%"></div>
                </div>

                <div class="result-actions">
                    <a href="studentDashboard.jsp" class="btn btn-primary">Go to Dashboard</a>
                    <a href="result" class="btn btn-secondary">View History</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
