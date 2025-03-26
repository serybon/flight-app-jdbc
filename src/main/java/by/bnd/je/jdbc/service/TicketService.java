package by.bnd.je.jdbc.service;

import by.bnd.je.jdbc.dao.FlightDao;
import by.bnd.je.jdbc.dao.TicketDao;
import by.bnd.je.jdbc.dto.FlightDto;
import by.bnd.je.jdbc.dto.TicketDto;

import java.util.List;
import java.util.stream.Collectors;

public class TicketService {

    private static final TicketService INSTANCE = new TicketService();
    private static final TicketDao ticketDao = TicketDao.getInstance();

    public List<TicketDto> findAllByFlightId(Long flightId) {
        return ticketDao.findAllByFlightId(flightId).stream().map(ticket -> new TicketDto(
                ticket.getId(),
                ticket.getFlight().getId(),
                ticket.getSeatNo())).collect(Collectors.toList());
    }

    private TicketService() {
    }

    public static TicketService getInstance() {
        return INSTANCE;
    }


}
