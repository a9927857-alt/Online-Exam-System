<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Panel - Online Exam System</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
    <%
        Object userId = session.getAttribute("userId");
        String role = (String) session.getAttribute("role");

        if (userId == null || !"admin".equals(role)) {
            response.sendRedirect("login.jsp");
            return;
        }
        String username = (String) session.getAttribute("username");
    %>

    <div class="container">
        <nav class="navbar">
            <div class="nav-brand">Online Exam System - Admin Panel</div>
            <div class="nav-menu">
                <span>Welcome, <%= username %></span>
                <a href="logout.jsp" class="nav-link">Logout</a>
            </div>
        </nav>

        <div class="admin-container">
            <h1>Admin Dashboard</h1>

            <div class="admin-content">
                <div class="admin-section">
                    <h2>Add New Question</h2>
                    <form action="admin" method="POST" class="admin-form">
                        <input type="hidden" name="action" value="addQuestion">

                        <div class="form-group">
                            <label for="examId">Select Exam:</label>
                            <select id="examId" name="examId" required>
                                <option value="1">Java Programming Exam (Exam 1)</option>
                                <option value="2">Web Development Exam (Exam 2)</option>
                                <option value="3">Database Management Exam (Exam 3)</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="questionText">Question:</label>
                            <textarea id="questionText" name="questionText" rows="3" required
                                      placeholder="Enter the question text"></textarea>
                        </div>

                        <div class="options-grid">
                            <div class="form-group">
                                <label for="optionA">Option A:</label>
                                <input type="text" id="optionA" name="optionA" required
                                       placeholder="Enter option A">
                            </div>

                            <div class="form-group">
                                <label for="optionB">Option B:</label>
                                <input type="text" id="optionB" name="optionB" required
                                       placeholder="Enter option B">
                            </div>

                            <div class="form-group">
                                <label for="optionC">Option C:</label>
                                <input type="text" id="optionC" name="optionC" required
                                       placeholder="Enter option C">
                            </div>

                            <div class="form-group">
                                <label for="optionD">Option D:</label>
                                <input type="text" id="optionD" name="optionD" required
                                       placeholder="Enter option D">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="correctAnswer">Correct Answer:</label>
                            <select id="correctAnswer" name="correctAnswer" required>
                                <option value="">Select correct answer</option>
                                <option value="A">A</option>
                                <option value="B">B</option>
                                <option value="C">C</option>
                                <option value="D">D</option>
                            </select>
                        </div>

                        <button type="submit" class="btn btn-success">Add Question</button>
                    </form>
                </div>

                <div class="admin-section">
                    <h2>Quick Links</h2>
                    <ul class="admin-links">
                        <li><a href="studentDashboard.jsp">View Student Dashboard</a></li>
                        <li><a href="index.jsp">Go to Home</a></li>
                    </ul>
                </div>

                <div class="admin-info">
                    <h3>Instructions:</h3>
                    <ul>
                        <li>Use the form above to add new questions to exams</li>
                        <li>Select the appropriate exam from the dropdown</li>
                        <li>Fill in all the options and select the correct answer</li>
                        <li>Click "Add Question" to submit</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
