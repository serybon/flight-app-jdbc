package by.bnd.je.jdbc;

import by.bnd.je.jdbc.dao.TicketDao;
import by.bnd.je.jdbc.entity.Ticket;
import org.w3c.dom.ls.LSOutput;

import java.math.BigDecimal;
import java.sql.SQLException;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {

        var ticketDao = TicketDao.getInstance();
        Ticket ticket = new Ticket();
        ticket.setPassportNo("MP1234");
        ticket.setPassengerName("Paoulo Gucci");
        ticket.setFlightId(4L);
        ticket.setSeatNo("3B");
        ticket.setCost(BigDecimal.TEN);

        //System.out.println(ticketDao.save(ticket));
        //System.out.println(ticketDao.delete(14L));
        ticketDao.findAll().forEach(System.out::println);
        System.out.println("SearchingElement: " + ticketDao.findTicketById(17L));
        Ticket ticket2 = ticketDao.findTicketById(8L).get();
        ticket2.setCost(BigDecimal.valueOf(399));
        ticketDao.update(ticket2);
        System.out.println(ticketDao.findTicketById(8L).get());
    }


}
