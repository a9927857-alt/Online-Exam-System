
package com.exam.servlet;

import com.exam.util.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Enumeration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SubmitExamServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Verify that the user is logged in
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp");
            return;
        }

        Object userIdObject = session.getAttribute("userId");
        Object examIdObject = session.getAttribute("examId");

        if (!(userIdObject instanceof Number)
                || !(examIdObject instanceof Number)) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid session or exam information.");
            return;
        }

        int userId = ((Number) userIdObject).intValue();
        int examId = ((Number) examIdObject).intValue();

        int correctAnswers = 0;
        int totalQuestions = 0;

        String selectQuery =
                "SELECT correct_answer FROM questions " +
                "WHERE question_id = ? AND exam_id = ?";

        String insertQuery =
                "INSERT INTO results " +
                "(user_id, exam_id, total_questions, correct_answers, " +
                "percentage, submission_date) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {

            // Count and evaluate submitted answers
            Enumeration<String> paramNames =
                    request.getParameterNames();

            try (PreparedStatement selectStmt =
                         conn.prepareStatement(selectQuery)) {

                while (paramNames.hasMoreElements()) {

                    String paramName = paramNames.nextElement();

                    if (!paramName.startsWith("question_")) {
                        continue;
                    }

                    String questionIdStr =
                            paramName.substring("question_".length());

                    int questionId;

                    try {
                        questionId = Integer.parseInt(questionIdStr);
                    } catch (NumberFormatException e) {
                        continue;
                    }

                    String userAnswer = request.getParameter(paramName);

                    if (userAnswer == null || userAnswer.isBlank()) {
                        continue;
                    }

                    selectStmt.setInt(1, questionId);
                    selectStmt.setInt(2, examId);

                    try (ResultSet rs = selectStmt.executeQuery()) {

                        // Only count questions belonging to this exam
                        if (rs.next()) {
                            totalQuestions++;

                            String correctAnswer =
                                    rs.getString("correct_answer");

                            if (correctAnswer != null
                                    && correctAnswer.trim().equalsIgnoreCase(
                                            userAnswer.trim())) {
                                correctAnswers++;
                            }
                        }
                    }
                }
            }

            // Calculate percentage
            double percentage = totalQuestions > 0
                    ? ((double) correctAnswers / totalQuestions) * 100.0
                    : 0.0;

            // Save the result
            try (PreparedStatement insertStmt =
                         conn.prepareStatement(insertQuery)) {

                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, examId);
                insertStmt.setInt(3, totalQuestions);
                insertStmt.setInt(4, correctAnswers);
                insertStmt.setDouble(5, percentage);
                insertStmt.setTimestamp(
                        6, new Timestamp(System.currentTimeMillis()));

                insertStmt.executeUpdate();
            }

            // Store results for the result page
            session.setAttribute("correctAnswers", correctAnswers);
            session.setAttribute("totalQuestions", totalQuestions);
            session.setAttribute("percentage", percentage);

            // Redirect after successful submission
            response.sendRedirect(
                    request.getContextPath() + "/result.jsp");

        } catch (SQLException e) {

            log("Error submitting exam results for user ID: " + userId, e);

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to save your exam result. Please try again.");
        }
    }
}

