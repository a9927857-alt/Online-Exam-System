
package com.exam.servlet;

import com.exam.dao.QuestionDAO;
import com.exam.model.Question;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ExamServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check whether the user is logged in
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Get exam ID safely
        int examId = 1;

        try {
            String examIdParam = request.getParameter("examId");

            if (examIdParam != null && !examIdParam.trim().isEmpty()) {
                examId = Integer.parseInt(examIdParam);
            }
        } catch (NumberFormatException e) {
            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Invalid exam ID."
            );
            return;
        }

        // Fetch questions for the selected exam
        QuestionDAO questionDAO = new QuestionDAO();
        List<Question> questions = questionDAO.getQuestionsByExamId(examId);

        // Store exam information in the session
        session.setAttribute("examId", examId);
        session.setAttribute("questions", questions);
        session.setAttribute("totalQuestions", questions.size());

        // Forward to the exam page
        request.getRequestDispatcher("/exam.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}

