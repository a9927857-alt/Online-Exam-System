
package com.exam.servlet;

import com.exam.util.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ResultServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check whether the user is logged in
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp");
            return;
        }

        // Safely retrieve the user ID
        Object userIdObject = session.getAttribute("userId");

        if (!(userIdObject instanceof Number)) {
            session.invalidate();
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp");
            return;
        }

        int userId = ((Number) userIdObject).intValue();

        // Retrieve the user's result history
        List<Map<String, Object>> results = getResultHistory(userId);

        request.setAttribute("results", results);

        request.getRequestDispatcher("/resultHistory.jsp")
               .forward(request, response);
    }

    private List<Map<String, Object>> getResultHistory(int userId)
            throws ServletException {

        List<Map<String, Object>> results = new ArrayList<>();

        String query =
                "SELECT result_id, exam_id, total_questions, " +
                "correct_answers, percentage, submission_date " +
                "FROM results " +
                "WHERE user_id = ? " +
                "ORDER BY submission_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {

                    Map<String, Object> result = new HashMap<>();

                    result.put("resultId", rs.getInt("result_id"));
                    result.put("examId", rs.getInt("exam_id"));
                    result.put("totalQuestions",
                            rs.getInt("total_questions"));
                    result.put("correctAnswers",
                            rs.getInt("correct_answers"));
                    result.put("percentage",
                            rs.getDouble("percentage"));
                    result.put("submissionDate",
                            rs.getTimestamp("submission_date"));

                    results.add(result);
                }
            }

        } catch (SQLException e) {
            log("Error retrieving result history for user ID: " + userId, e);

            throw new ServletException(
                    "Unable to retrieve exam result history.", e);
        }

        return results;
    }
}

