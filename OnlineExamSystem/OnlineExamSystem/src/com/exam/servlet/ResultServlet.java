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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ResultServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        List<Map<String, Object>> results = getResultHistory(userId);

        request.setAttribute("results", results);
        request.getRequestDispatcher("resultHistory.jsp").forward(request, response);
    }

    private List<Map<String, Object>> getResultHistory(int userId) {
        List<Map<String, Object>> results = new ArrayList<>();
        String query = "SELECT * FROM results WHERE user_id = ? ORDER BY submission_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> result = new HashMap<>();
                result.put("resultId", rs.getInt("result_id"));
                result.put("examId", rs.getInt("exam_id"));
                result.put("totalQuestions", rs.getInt("total_questions"));
                result.put("correctAnswers", rs.getInt("correct_answers"));
                result.put("percentage", rs.getDouble("percentage"));
                result.put("submissionDate", rs.getTimestamp("submission_date"));
                results.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }
}
