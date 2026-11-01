
package com.exam.servlet;

import com.exam.dao.QuestionDAO;
import com.exam.model.Question;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AdminServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check whether the user is logged in
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        
        String role = (String) session.getAttribute("role");

        if (!"admin".equalsIgnoreCase(role)) {
            response.sendRedirect(
                request.getContextPath() + "/studentDashboard.jsp"
            );
            return;
        }

        request.getRequestDispatcher("/admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check login
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Check admin authorization for POST requests too
        String role = (String) session.getAttribute("role");

        if (!"admin".equalsIgnoreCase(role)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Access denied. Admin privileges are required.");
            return;
        }

        String action = request.getParameter("action");

        try {
            if ("addQuestion".equals(action)) {
                addQuestion(request);
            } else if ("deleteQuestion".equals(action)) {
                deleteQuestion(request);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid action.");
                return;
            }

            response.sendRedirect(
                request.getContextPath() + "/admin.jsp"
            );

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid exam ID or question ID.");
        } catch (Exception e) {
            log("Error processing admin request", e);

            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to process the request. Please try again.");
        }
    }

    private void addQuestion(HttpServletRequest request) throws Exception {

        String questionText = request.getParameter("questionText");
        String optionA = request.getParameter("optionA");
        String optionB = request.getParameter("optionB");
        String optionC = request.getParameter("optionC");
        String optionD = request.getParameter("optionD");
        String correctAnswer = request.getParameter("correctAnswer");
        String examIdParam = request.getParameter("examId");

        // Validate required fields
        if (isBlank(questionText)
                || isBlank(optionA)
                || isBlank(optionB)
                || isBlank(optionC)
                || isBlank(optionD)
                || isBlank(correctAnswer)
                || isBlank(examIdParam)) {

            throw new IllegalArgumentException(
                    "All question fields are required."
            );
        }

        // Validate the correct answer
        if (!correctAnswer.equals("A")
                && !correctAnswer.equals("B")
                && !correctAnswer.equals("C")
                && !correctAnswer.equals("D")) {

            throw new IllegalArgumentException(
                    "Correct answer must be A, B, C, or D."
            );
        }

        int examId = Integer.parseInt(examIdParam);

        Question question = new Question();
        question.setQuestionText(questionText.trim());
        question.setOptionA(optionA.trim());
        question.setOptionB(optionB.trim());
        question.setOptionC(optionC.trim());
        question.setOptionD(optionD.trim());
        question.setCorrectAnswer(correctAnswer);
        question.setExamId(examId);

        QuestionDAO questionDAO = new QuestionDAO();
        questionDAO.addQuestion(question);
    }

    private void deleteQuestion(HttpServletRequest request) throws Exception {

        String questionIdParam = request.getParameter("questionId");

        if (isBlank(questionIdParam)) {
            throw new IllegalArgumentException("Question ID is required.");
        }

        int questionId = Integer.parseInt(questionIdParam);

        QuestionDAO questionDAO = new QuestionDAO();
        questionDAO.deleteQuestion(questionId);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}

