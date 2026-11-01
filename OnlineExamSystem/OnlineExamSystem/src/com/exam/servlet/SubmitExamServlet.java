package com.exam.servlet;

import com.exam.util.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SubmitExamServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        int examId = (Integer) session.getAttribute("examId");
        int correctAnswers = 0;
        int totalQuestions = 0;

        // Get user answers
        Enumeration<String> paramNames = request.getParameterNames();

        try (Connection conn = DBConnection.getConnection()) {
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                if (paramName.startsWith("question_")) {
                    totalQuestions++;
                    String questionIdStr = paramName.substring(9);
                    int questionId = Integer.parseInt(questionIdStr);
                    String userAnswer = request.getParameter(paramName);

                    // Get correct answer from database
                    String query = "SELECT correct_answer FROM questions WHERE question_id = ?";
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, questionId);
                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        String correctAnswer = rs.getString("correct_answer");
                        if (userAnswer.equals(correctAnswer)) {
                            correctAnswers++;
                        }
                    }
                    rs.close();
                    pstmt.close();
                }
            }

            // Calculate percentage
            double percentage = (totalQuestions > 0) ?
                    ((double) correctAnswers / totalQuestions) * 100 : 0;

            // Store result in database
            String insertQuery = "INSERT INTO results (user_id, exam_id, total_questions, correct_answers, percentage, submission_date) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setInt(1, userId);
            insertStmt.setInt(2, examId);
            insertStmt.setInt(3, totalQuestions);
            insertStmt.setInt(4, correctAnswers);
            insertStmt.setDouble(5, percentage);
            insertStmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            insertStmt.executeUpdate();
            insertStmt.close();

            // Set session attributes for result display
            session.setAttribute("correctAnswers", correctAnswers);
            session.setAttribute("totalQuestions", totalQuestions);
            session.setAttribute("percentage", percentage);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("result.jsp");
    }
}
