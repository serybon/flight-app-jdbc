package by.bnd.je.jdbc.servlet;

import by.bnd.je.jdbc.service.TicketService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/tickets")
public class TicketServlet extends HttpServlet {
    private final TicketService ticketService = TicketService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        Long flightId = Long.valueOf(req.getParameter("flightId"));

        try (var writer = resp.getWriter()) {
            writer.println("<h1>Купленные билеты</h1>");
            writer.println("<ul>");
            ticketService.findAllByFlightId(flightId).stream().forEach(ticketDto -> writer.write(
                    """
                        <li>%s</li>
                        """.formatted(ticketDto.seatNo())
            ));
            writer.println("</ul>");
        }
    }
}
