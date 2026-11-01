package com.exam.servlet;

import com.exam.dao.QuestionDAO;
import com.exam.model.Question;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ExamServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int examId = Integer.parseInt(request.getParameter("examId") != null ?
                request.getParameter("examId") : "1");

        QuestionDAO questionDAO = new QuestionDAO();
        List<Question> questions = questionDAO.getQuestionsByExamId(examId);

        session.setAttribute("examId", examId);
        session.setAttribute("questions", questions);
        session.setAttribute("totalQuestions", questions.size());

        request.getRequestDispatcher("exam.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
