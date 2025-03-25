package by.bnd.je.jdbc.dto;

import java.math.BigDecimal;

public record TicketFilter(String passengerName,
                           String seatNo,
                           int limit,
                           int offset) {
}
