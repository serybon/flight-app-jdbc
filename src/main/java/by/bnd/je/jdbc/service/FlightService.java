package by.bnd.je.jdbc.service;

import by.bnd.je.jdbc.dao.FlightDao;
import by.bnd.je.jdbc.dto.FlightDto;

import java.util.List;
import java.util.stream.Collectors;

public class FlightService {

    public List<FlightDto> findAll(){
        return flightDao.findAll().stream().map(flight -> new FlightDto(flight.getId(), "%s - %s - %s".formatted(
                flight.getArrivalAirportCode(),
                flight.getDepartureAirportCode(),
                flight.getStatus()))).collect(Collectors.toList());
    };

    private static final FlightService INSTANCE = new FlightService();
    private static final FlightDao flightDao = FlightDao.getInstance();

    private FlightService() {
    }

    public static FlightService getInstance() {
        return INSTANCE;
    }
}
