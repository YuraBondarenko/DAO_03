package core.basesyntax.service;

import core.basesyntax.dao.CarDao;
import core.basesyntax.exceptions.DataProcessingException;
import core.basesyntax.lib.Inject;
import core.basesyntax.lib.Service;
import core.basesyntax.model.Car;
import core.basesyntax.model.Driver;
import core.basesyntax.model.Manufacturer;
import core.basesyntax.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    @Inject
    private CarDao carDao;

    @Override
    public Car create(Car car) {
        return carDao.create(car);
    }

    @Override
    public Car get(Long id) {
        return carDao.get(id).get();
    }

    @Override
    public List<Car> getAll() {
        return carDao.getAll();
    }

    @Override
    public Car update(Car car) {
        return carDao.update(car);
    }

    @Override
    public boolean delete(Long id) {
        removeAllDriversFromCar(id);
        return carDao.delete(id);
    }

    @Override
    public void addDriverToCar(Driver driver, Car car) {
        String addDriverQuery = "INSERT INTO `cars_drivers`"
                + "(driver_id, car_id)"
                + " VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection
                         .prepareStatement(addDriverQuery)) {
            preparedStatement.setLong(1, driver.getId());
            preparedStatement.setLong(2, car.getId());
            preparedStatement.executeUpdate();
            get(car.getId()).getDrivers().add(driver);
            update(car);
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot add driver " + driver + " to car" + car, e);
        }
    }

    @Override
    public void removeDriverFromCar(Driver driver, Car car) {
        String removeDriverFromCarQuery = " DELETE FROM cars_drivers"
                + " WHERE driver_id = ? AND car_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection
                         .prepareStatement(removeDriverFromCarQuery)) {
            preparedStatement.setLong(1, driver.getId());
            preparedStatement.setLong(2, car.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot remove driver " + driver
                    + " from car " + car, e);
        }
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        String getAllByDriverQuery = "SELECT * FROM taxi_service.cars_drivers cd"
                + " INNER JOIN taxi_service.cars c ON cd.car_id = c.id"
                + " INNER JOIN taxi_service.drivers d ON cd.driver_id = d.id"
                + " WHERE cd.driver_id = ?";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(getAllByDriverQuery)) {
            preparedStatement.setLong(1, driverId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(getCar(resultSet));
            }
            return cars;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get any car by driver by id " + driverId, e);
        }
    }

    private void removeAllDriversFromCar(Long id) {
        String removeDriverFromCarQuery = " DELETE FROM cars_drivers"
                + " WHERE car_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection
                         .prepareStatement(removeDriverFromCarQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete all drivers from car by " + id, e);
        }
    }

    private Car getCar(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("c.id", Long.class);
        Long manufacturerId = resultSet.getObject("c.manufacturer_id", Long.class);
        String model = resultSet.getObject("c.model", String.class);
        Car car = new Car(model, getManufacturer(manufacturerId));
        car.setId(id);
        car.setDrivers(getDrivers(id));
        return car;
    }

    private Manufacturer getManufacturer(Long id) {
        String getByIdQuery = "SELECT * FROM manufacturers"
                + " WHERE id = ?"
                + " AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(getByIdQuery)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                String nameManufacturer = resultSet.getObject("name", String.class);
                String countryManufacturer = resultSet.getObject("country", String.class);
                manufacturer = new Manufacturer(nameManufacturer, countryManufacturer);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get manufacturer by id " + id, e);
        }
    }

    private List<Driver> getDrivers(Long carId) {
        List<Driver> drivers = new ArrayList<>();
        String getDriversQuery = "SELECT * FROM taxi_service.cars_drivers  cd"
                + " INNER JOIN taxi_service.cars c ON cd.car_id = c.id"
                + " INNER JOIN taxi_service.drivers d ON cd.driver_id = d.id"
                + " WHERE cd.car_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection
                         .prepareStatement(getDriversQuery)) {
            preparedStatement.setLong(1, carId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getObject("d.name", String.class);
                String licenceNumber = resultSet.getObject("d.licenceNumber", String.class);
                Long driverId = resultSet.getObject("d.id", Long.class);
                Driver driver = new Driver(name, licenceNumber);
                driver.setId(driverId);
                drivers.add(driver);
            }
            return drivers;
        } catch (SQLException e) {
            throw new DataProcessingException("qqq", e);
        }
    }
}
