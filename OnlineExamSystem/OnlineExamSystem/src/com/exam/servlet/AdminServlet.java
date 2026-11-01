package com.exam.servlet;

import com.exam.dao.QuestionDAO;
import com.exam.model.Question;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String role = (String) session.getAttribute("role");
        if (!"admin".equals(role)) {
            response.sendRedirect("studentDashboard.jsp");
            return;
        }

        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");

        if ("addQuestion".equals(action)) {
            addQuestion(request, response);
        } else if ("deleteQuestion".equals(action)) {
            deleteQuestion(request, response);
        }

        response.sendRedirect("admin.jsp");
    }

    private void addQuestion(HttpServletRequest request, HttpServletResponse response) {
        try {
            Question question = new Question();
            question.setQuestionText(request.getParameter("questionText"));
            question.setOptionA(request.getParameter("optionA"));
            question.setOptionB(request.getParameter("optionB"));
            question.setOptionC(request.getParameter("optionC"));
            question.setOptionD(request.getParameter("optionD"));
            question.setCorrectAnswer(request.getParameter("correctAnswer"));
            question.setExamId(Integer.parseInt(request.getParameter("examId")));

            QuestionDAO questionDAO = new QuestionDAO();
            questionDAO.addQuestion(question);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteQuestion(HttpServletRequest request, HttpServletResponse response) {
        try {
            int questionId = Integer.parseInt(request.getParameter("questionId"));
            QuestionDAO questionDAO = new QuestionDAO();
            questionDAO.deleteQuestion(questionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
