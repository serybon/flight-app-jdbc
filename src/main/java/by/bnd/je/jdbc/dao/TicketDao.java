package by.bnd.je.jdbc.dao;

import by.bnd.je.jdbc.dto.TicketFilter;
import by.bnd.je.jdbc.entity.Ticket;
import by.bnd.je.jdbc.exception.DaoException;
import by.bnd.je.jdbc.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TicketDao implements Dao<Long,Ticket> {
    private final static TicketDao INSTANCE = new TicketDao();

    private final static String SAVE_SQL =
            """
                    INSERT INTO ticket(passport_no, passenger_name, flight_id, seat_no, cost) 
                    values (?,?,?,?,?)
                    """;
    private static final String DELETE_SQL =
            """
                    DELETE FROM ticket where id = ?
                    """;
    private static final String UPDATE_SQL =
            """
                    UPDATE ticket 
                    SET passport_no = ?,
                        passenger_name = ?,
                        flight_id = ?,
                        seat_no = ?,
                        cost = ?
                    WHERE id = ?
                    """;
    private static final String FIND_ALL_SQL =
            """
                    SELECT id, passport_no, passenger_name, flight_id, seat_no, cost FROM ticket
                    """;
    private static final String FIND_TICKET_BY_ID =
            """
                    SELECT id, passport_no, passenger_name, flight_id, seat_no, cost from ticket
                    where id = ?
                    """;


    public boolean update(Ticket ticket) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, ticket.getPassportNo());
            statement.setString(2, ticket.getPassengerName());
            statement.setLong(3, ticket.getFlightId());
            statement.setString(4, ticket.getSeatNo());
            statement.setBigDecimal(5, ticket.getCost());
            statement.setLong(6, ticket.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Ticket save(Ticket t) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, t.getPassportNo());
            statement.setString(2, t.getPassengerName());
            statement.setLong(3, t.getFlightId());
            statement.setString(4, t.getSeatNo());
            statement.setBigDecimal(5, t.getCost());
            statement.executeUpdate();

            var keys = statement.getGeneratedKeys();
            if (keys.next()) {
                t.setId(keys.getLong(1));
            }

            return t;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Ticket> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {

            var resultSet = statement.executeQuery();
            List<Ticket> tickets = new ArrayList<>();
            while (resultSet.next()) {
                tickets.add(buildTicket(resultSet));
            }
            return tickets;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Ticket> findAll(TicketFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.passengerName() != null) {
            parameters.add(filter.passengerName());
            whereSql.add("passenger_name = ?");
        }
        if (filter.seatNo() != null) {
            parameters.add("%" + filter.seatNo() + "%");
            whereSql.add("seat_no LIKE ?");
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        var where = whereSql.stream().collect(Collectors.joining(
                " AND ",
                parameters.size() > 2 ? " WHERE " : "",
                " LIMIT ? OFFSET ? "
        ));
        String sql = FIND_ALL_SQL + where;

        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            System.out.println(statement);
            var resultSet = statement.executeQuery();
            List<Ticket> tickets = new ArrayList<>();
            while (resultSet.next()) {
                tickets.add(buildTicket(resultSet));
            }
            return tickets;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Ticket> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_TICKET_BY_ID)) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            Ticket ticket = null;
            if (resultSet.next()) {
                ticket = buildTicket(resultSet);
            }
            return Optional.ofNullable(ticket);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static Ticket buildTicket(ResultSet resultSet) throws SQLException {
        return new Ticket(
                resultSet.getLong("id"),
                resultSet.getString("passport_no"),
                resultSet.getString("passenger_name"),
                resultSet.getLong("flight_id"),
                resultSet.getString("seat_no"),
                resultSet.getBigDecimal("cost"));
    }

    private TicketDao() {

    }

    public static TicketDao getInstance() {
        return INSTANCE;
    }
}
