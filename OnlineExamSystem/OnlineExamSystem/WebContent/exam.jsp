<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.exam.model.Question"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Exam - Online Exam System</title>
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
        List<Question> questions = (List<Question>) session.getAttribute("questions");
        Integer totalQuestions = (Integer) session.getAttribute("totalQuestions");
        Integer examId = (Integer) session.getAttribute("examId");

        if (questions == null) {
            response.sendRedirect("studentDashboard.jsp");
            return;
        }
    %>

    <div class="container">
        <div class="exam-container">
            <div class="exam-header">
                <div class="exam-title">
                    <h1>Online Examination</h1>
                    <p>Total Questions: <%= totalQuestions %></p>
                </div>
                <div class="exam-timer">
                    <h3>Time Remaining</h3>
                    <div class="timer" id="timer">60:00</div>
                    <p class="timer-warning">Auto-submit on time over</p>
                </div>
            </div>

            <form action="submitExam" method="POST" id="examForm">
                <div class="questions-container">
                    <%
                        int qCount = 1;
                        for (Question q : questions) {
                    %>
                        <div class="question-block">
                            <h3>Q<%= qCount %>. <%= q.getQuestionText() %></h3>

                            <div class="options">
                                <label class="option">
                                    <input type="radio" name="question_<%= q.getQuestionId() %>"
                                           value="A" required>
                                    <span class="option-text"><strong>A)</strong> <%= q.getOptionA() %></span>
                                </label>

                                <label class="option">
                                    <input type="radio" name="question_<%= q.getQuestionId() %>"
                                           value="B" required>
                                    <span class="option-text"><strong>B)</strong> <%= q.getOptionB() %></span>
                                </label>

                                <label class="option">
                                    <input type="radio" name="question_<%= q.getQuestionId() %>"
                                           value="C" required>
                                    <span class="option-text"><strong>C)</strong> <%= q.getOptionC() %></span>
                                </label>

                                <label class="option">
                                    <input type="radio" name="question_<%= q.getQuestionId() %>"
                                           value="D" required>
                                    <span class="option-text"><strong>D)</strong> <%= q.getOptionD() %></span>
                                </label>
                            </div>
                        </div>
                    <%
                        qCount++;
                        }
                    %>
                </div>

                <div class="exam-actions">
                    <button type="submit" class="btn btn-success">Submit Exam</button>
                    <button type="reset" class="btn btn-secondary">Clear Answers</button>
                </div>
            </form>
        </div>
    </div>

    <script src="assets/js/exam.js"></script>
</body>
</html>
