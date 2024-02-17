package servlets;

import db.DbManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userEmail = req.getParameter("user_email");
        String userPassword = req.getParameter("user_password");
        String message = DbManager.auth(userEmail, userPassword);
//        req.setAttribute("message",message);
//        req.getRequestDispatcher("auth.jsp").forward(req,resp);
        if ("success".equals(message)) {
            resp.sendRedirect("home.jsp");

        } else {
            req.setAttribute("message", message);
            req.getRequestDispatcher("auth.jsp").forward(req,resp);
        }
    }
}
