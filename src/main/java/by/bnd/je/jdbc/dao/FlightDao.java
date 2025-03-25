package by.bnd.je.jdbc.dao;

public class FlightDao<Long,Flight> {
    private final static FlightDao INSTANCE = new FlightDao();

    private FlightDao() {
    }
}
