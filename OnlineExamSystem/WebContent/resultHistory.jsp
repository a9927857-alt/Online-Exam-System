<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Result History - Online Exam System</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
    <%
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> results = (List<Map<String, Object>>) request.getAttribute("results");
    %>

    <div class="container">
        <nav class="navbar">
            <div class="nav-brand">Online Exam System</div>
            <div class="nav-menu">
                <a href="studentDashboard.jsp" class="nav-link">Dashboard</a>
                <a href="logout.jsp" class="nav-link">Logout</a>
            </div>
        </nav>

        <div class="results-container">
            <h1>Your Exam Results</h1>

            <% if (results != null && !results.isEmpty()) { %>
                <div class="results-table">
                    <table>
                        <thead>
                            <tr>
                                <th>Exam ID</th>
                                <th>Total Questions</th>
                                <th>Correct Answers</th>
                                <th>Score (%)</th>
                                <th>Status</th>
                                <th>Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                for (Map<String, Object> result : results) {
                                    int total = (Integer) result.get("totalQuestions");
                                    int correct = (Integer) result.get("correctAnswers");
                                    double percentage = (Double) result.get("percentage");
                                    String status = percentage >= 50 ? "PASSED" : "FAILED";
                                    String statusClass = status.equals("PASSED") ? "passed" : "failed";
                            %>
                                <tr>
                                    <td><%= result.get("examId") %></td>
                                    <td><%= total %></td>
                                    <td><%= correct %></td>
                                    <td><strong><%= String.format("%.2f", percentage) %>%</strong></td>
                                    <td><span class="badge <%= statusClass %>"><%= status %></span></td>
                                    <td><%= sdf.format(result.get("submissionDate")) %></td>
                                </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            <% } else { %>
                <div class="no-results">
                    <p>No exam results found. Start an exam to see your results here.</p>
                    <a href="studentDashboard.jsp" class="btn btn-primary">Go to Dashboard</a>
                </div>
            <% } %>
        </div>
    </div>
</body>
</html>
