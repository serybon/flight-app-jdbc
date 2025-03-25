package by.bnd.je.jdbc.searchInfo;

import by.bnd.je.jdbc.utils.ConnectionManager;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SearchInfo {
    public static List<Integer> getTicketsByFlightId(Integer flightId) throws SQLException {
        String sql = """
                select * from ticket
                where flight_id = %s
                """.formatted(flightId);

        List<Integer> tickets = new ArrayList<>();
        try (var connection = ConnectionManager.get()) {
            var statement = connection.createStatement();
            var result = statement.executeQuery(sql);
            while (result.next()) {
                System.out.println(result.getInt(1) + " " + result.getString(2)
                                   + " " + result.getString(3) + " " + result.getInt(4)
                                   + " " + result.getString(5) + " " + result.getBigDecimal(6));
                tickets.add(result.getInt(1));
            }
        }
        return tickets;
    }

    public static List<Integer> findFlightsBetween(LocalDateTime start, LocalDateTime end) throws SQLException {
        String sql = """
                select * from flight
                where departure_date between ? and ?
                """;

        List<Integer> flights = new ArrayList<>();
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(sql);) {
            statement.setTimestamp(1, Timestamp.valueOf(start));
            statement.setTimestamp(2, Timestamp.valueOf(end));
            var result = statement.executeQuery();
            while (result.next()) {
                flights.add(result.getInt(1));
            }
        }
        return flights;
    }
}
