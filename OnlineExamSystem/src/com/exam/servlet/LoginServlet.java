
package com.exam.servlet;

import com.exam.dao.UserDAO;
import com.exam.model.User;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate the input
        if (username == null || username.trim().isEmpty()
                || password == null || password.isEmpty()) {

            request.setAttribute("error",
                    "Please enter your username and password.");

            request.getRequestDispatcher("/login.jsp")
                   .forward(request, response);
            return;
        }

        try {
            UserDAO userDAO = new UserDAO();

            if (userDAO.authenticateUser(username.trim(), password)) {

                User user = userDAO.getUserByUsername(username.trim());

                if (user == null) {
                    request.setAttribute("error",
                            "Unable to retrieve your account. Please try again.");

                    request.getRequestDispatcher("/login.jsp")
                           .forward(request, response);
                    return;
                }

                // Create a fresh session after successful authentication
                HttpSession oldSession = request.getSession(false);

                if (oldSession != null) {
                    oldSession.invalidate();
                }

                HttpSession session = request.getSession(true);

                session.setAttribute("user", user);
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("username", user.getUsername());
                session.setAttribute("role", user.getRole());

                // Session expires after 30 minutes of inactivity
                session.setMaxInactiveInterval(30 * 60);

                // Redirect according to user role
                String role = user.getRole();

                if ("admin".equalsIgnoreCase(role)) {
                    response.sendRedirect(
                            request.getContextPath() + "/admin.jsp");
                } else {
                    response.sendRedirect(
                            request.getContextPath() + "/studentDashboard.jsp");
                }

            } else {
                request.setAttribute("error",
                        "Invalid username or password!");

                request.getRequestDispatcher("/login.jsp")
                       .forward(request, response);
            }

        } catch (Exception e) {
            log("Error during user login", e);

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            request.setAttribute("error",
                    "A server error occurred. Please try again.");

            request.getRequestDispatcher("/login.jsp")
                   .forward(request, response);
        }
    }
}

