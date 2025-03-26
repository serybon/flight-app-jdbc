package by.bnd.je.jdbc.servlet;

import by.bnd.je.jdbc.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/dispatcher")
public class DispatcherServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        var dispatcher = req.getRequestDispatcher(JspHelper.getPath("tickets"));
        req.setAttribute("dispatcher", true);
        dispatcher.include(req, resp);

        var writer = resp.getWriter();
        writer.write("<h1>dispatcher</h1>");

    }
}
