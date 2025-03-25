package by.bnd.je.jdbc;

import by.bnd.je.jdbc.dao.FlightDao;
import by.bnd.je.jdbc.entity.Flight;
import by.bnd.je.jdbc.entity.FlightStatus;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {


//        Ticket ticket = new Ticket();
//        ticket.setPassportNo("MP1234");
//        ticket.setPassengerName("Paoulo Gucci");
//        ticket.setFlightId(4L);
//        ticket.setSeatNo("3B");
//        ticket.setCost(BigDecimal.TEN);
//
//        //System.out.println(ticketDao.save(ticket));
//        //System.out.println(ticketDao.delete(14L));
//        ticketDao.findAll().forEach(System.out::println);
//        System.out.println("SearchingElement: " + ticketDao.findTicketById(17L));
//        Ticket ticket2 = ticketDao.findTicketById(8L).get();
//        ticket2.setCost(BigDecimal.valueOf(399));
//        ticketDao.update(ticket2);
//        System.out.println(ticketDao.findTicketById(8L).get());

//        var ticketDao = TicketDao.getInstance();
//        var filter = new TicketFilter(null, null, 10, 0);
//        ticketDao.findAll(filter).forEach(System.out::println);

        var flightDao = FlightDao.getInstance();
        flightDao.findAll().forEach(System.out::println);
        Flight flight = new Flight();
        flight.setId(10L);
        flight.setFlightNo("255r");
        flight.setAircraftId(2);
        flight.setDepartureAirportCode("AAA");
        flight.setArrivalAirportCode("BBB");
        flight.setDepartureDate(LocalDateTime.of(2024,10,2,13,20));
        flight.setArrivalDate(LocalDateTime.of(2024,10,2,16,50));
        flight.setStatus(FlightStatus.boarding);
        flightDao.save(flight);
        flightDao.findAll().forEach(System.out::println);
        System.out.println(flightDao.findById(10L));


    }
}
