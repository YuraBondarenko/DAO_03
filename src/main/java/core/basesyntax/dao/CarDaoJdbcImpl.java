package core.basesyntax.dao;

import core.basesyntax.exceptions.DataProcessingException;
import core.basesyntax.lib.Dao;
import core.basesyntax.model.Car;
import core.basesyntax.model.Driver;
import core.basesyntax.model.Manufacturer;
import core.basesyntax.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class CarDaoJdbcImpl implements CarDao {
    @Override
    public Car create(Car car) {
        String query = "INSERT INTO `cars`"
                + " (manufacturer_id, model)"
                + " VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection
                         .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, car.getManufacturer().getId());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                car.setId(resultSet.getObject(1, Long.class));
            }
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot create car " + car, e);
        }
    }

    @Override
    public Optional<Car> get(Long id) {
        String getByIdQuery = "SELECT * FROM cars c"
                + " INNER JOIN manufacturers m ON c.manufacturer_id = m.id"
                + " WHERE c.id = ?"
                + " AND c.deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(getByIdQuery)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getCar(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get driver by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Car> getAll() {
        String getAllQuery = "SELECT * FROM cars c"
                + " INNER JOIN manufacturers m ON c.manufacturer_id = m.id"
                + " WHERE c.deleted = false";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(getAllQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(getCar(resultSet));
            }
            return cars;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get any cars", e);
        }
    }

    @Override
    public Car update(Car car) {
        String updateQuery = "UPDATE cars SET manufacturer_id = ?,"
                + " model = ? WHERE id = ? AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setLong(1, car.getManufacturer().getId());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setLong(3, car.getId());
            preparedStatement.executeUpdate();
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot update car " + car, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE cars SET deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot deleted car by id " + id, e);
        }
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        String getAllByDriverQuery = "SELECT * FROM cars_drivers cd"
                + " INNER JOIN cars c ON cd.car_id = c.id"
                + " INNER JOIN drivers d ON cd.driver_id = d.id"
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
            throw new DataProcessingException("Cannot get any driver by car id" + carId, e);
        }
    }
}
