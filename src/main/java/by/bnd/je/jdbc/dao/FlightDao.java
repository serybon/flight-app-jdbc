package by.bnd.je.jdbc.dao;

import by.bnd.je.jdbc.entity.Flight;
import by.bnd.je.jdbc.entity.FlightStatus;
import by.bnd.je.jdbc.exception.DaoException;
import by.bnd.je.jdbc.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Long, Flight> {

    private final static FlightDao INSTANCE = new FlightDao();

    private final static String SAVE_SQL =
            """
                    INSERT INTO flight(
                                       flight_no,
                                       departure_date,
                                       departure_airport_code,
                                       arrival_date,
                                       arrival_airport_code,
                                       aircraft_id,
                                       status) 
                    VALUES (?,?,?,?,?,?,?)
                    """;
    private static final String DELETE_SQL =
            """
                    DELETE FROM flight where id = ?
                    """;
    private static final String UPDATE_SQL =
            """
                    UPDATE flight
                    SET flight_no = ?,
                        departure_date = ?,
                        departure_airport_code = ?,
                        arrival_date = ?,
                        arrival_airport_code = ?,
                        aircraft_id = ?,
                        status = ?
                    WHERE id = ?
                    """;
    private static final String FIND_ALL_SQL =
            """
                    SELECT id,flight_no, departure_date, departure_airport_code,
                           arrival_date, arrival_airport_code, aircraft_id,
                           status
                    FROM flight
                    """;
    private static final String FIND_BY_ID =
            """
                    SELECT id,flight_no, departure_date, departure_airport_code,
                           arrival_date, arrival_airport_code, aircraft_id,
                           status 
                    FROM flight
                    WHERE id = ?
                    """;

    private FlightDao() {
    }

    public static FlightDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean update(Flight flight) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, flight.getFlightNo());
            statement.setTimestamp(2, Timestamp.valueOf(flight.getDepartureDate()));
            statement.setString(3, flight.getDepartureAirportCode());
            statement.setTimestamp(4, Timestamp.valueOf(flight.getArrivalDate()));
            statement.setString(5, flight.getArrivalAirportCode());
            statement.setInt(6, flight.getAircraftId());
            statement.setString(7, flight.getStatus().toString());
            statement.setLong(8, flight.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Flight> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {

            var resultSet = statement.executeQuery();
            List<Flight> flights = new ArrayList<>();
            while (resultSet.next()) {
                flights.add(buildFlight(resultSet));
            }
            return flights;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public Optional<Flight> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            Flight flight = null;
            if (resultSet.next()) {
                flight = buildFlight(resultSet);
            }
            return Optional.ofNullable(flight);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public Flight save(Flight flight) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, flight.getFlightNo());
            statement.setTimestamp(2, Timestamp.valueOf(flight.getDepartureDate()));
            statement.setString(3, flight.getDepartureAirportCode());
            statement.setTimestamp(4, Timestamp.valueOf(flight.getArrivalDate()));
            statement.setString(5, flight.getArrivalAirportCode());
            statement.setInt(6, flight.getAircraftId());
            statement.setString(7, flight.getStatus().toString());

            statement.executeUpdate();

            var keys = statement.getGeneratedKeys();
            if (keys.next()) {
                flight.setId(keys.getLong(1));
            }

            return flight;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Flight buildFlight(ResultSet resultSet) throws SQLException {
        return new Flight(
                resultSet.getLong("id"),
                resultSet.getString("flight_no"),
                resultSet.getTimestamp("departure_date").toLocalDateTime(),
                resultSet.getString("departure_airport_code"),
                resultSet.getTimestamp("arrival_date").toLocalDateTime(),
                resultSet.getString("arrival_airport_code"),
                resultSet.getInt("aircraft_id"),
                FlightStatus.valueOf(resultSet.getString("status"))
        );
    }
}
