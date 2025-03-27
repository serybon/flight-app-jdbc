package by.bnd.je.jdbc.servlet;

import by.bnd.je.jdbc.dao.FlightDao;
import by.bnd.je.jdbc.dto.FlightDto;
import by.bnd.je.jdbc.entity.Flight;
import by.bnd.je.jdbc.service.FlightService;
import by.bnd.je.jdbc.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/flights")
public class FlightServlet extends HttpServlet {
    private final FlightService flightService = FlightService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        req.setAttribute("flights", flightService.findAll());
        req.getRequestDispatcher(JspHelper.getPath("flights")).forward(req, resp);

//        try (var writer = resp.getWriter()) {
//            writer.println("<h1>Список перелетов</h1>");
//            writer.println("<ul>");
//            flightService.findAll().stream().forEach(flightDto -> writer.write(
//                        """
//                            <li>
//                            <a href='/tickets?flightId=%d'>%s</a>
//                            </li>
//                            """.formatted(flightDto.id(), flightDto.description())
//            ));
//            writer.println("</ul>");
//        }

    }
}
