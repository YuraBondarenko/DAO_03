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
        String query = "INSERT INTO cars"
                + " (manufacturer_id, model)"
                + " VALUES (?, ?);";
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
                + " AND c.deleted = false AND m.deleted = false";
        Car car = null;
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(getByIdQuery)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                car = getCar(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get car by id " + id, e);
        }
        if (car != null) {
            car.setDrivers(getDrivers(id));
        }
        return Optional.ofNullable(car);
    }

    @Override
    public List<Car> getAll() {
        String getAllQuery = "SELECT * FROM cars c"
                + " INNER JOIN manufacturers m ON c.manufacturer_id = m.id"
                + " WHERE c.deleted = false AND m.deleted = false";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(getAllQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(getCar(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get any cars", e);
        }
        for (Car car : cars) {
            car.setDrivers(getDrivers(car.getId()));
        }
        return cars;
    }

    @Override
    public Car update(Car car) {
        String updateQuery = "UPDATE cars SET manufacturer_id = ?,"
                + " model = ? WHERE id = ? AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updatePreparedStatement = connection
                         .prepareStatement(updateQuery)) {
            updatePreparedStatement.setLong(1, car.getManufacturer().getId());
            updatePreparedStatement.setString(2, car.getModel());
            updatePreparedStatement.setLong(3, car.getId());
            updatePreparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot update car " + car, e);
        }
        deleteFromCarsDrivers(car);
        insertIntoCarsDrivers(car);
        return car;
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE cars SET deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete car by id " + id, e);
        }
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        String getAllByDriverQuery = "SELECT * FROM cars c"
                + " INNER JOIN cars_drivers cd ON cd.car_id = c.id"
                + " INNER JOIN manufacturers m ON c.manufacturer_id = m.id"
                + " INNER JOIN drivers d ON d.id = cd.driver_id"
                + " WHERE cd.driver_id = ? AND m.deleted = false"
                + " AND d.deleted = false AND c.deleted = false";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection
                         .prepareStatement(getAllByDriverQuery)) {
            preparedStatement.setLong(1, driverId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(getCar(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get any car by driver by id " + driverId, e);
        }
        for (Car car : cars) {
            car.setDrivers(getDrivers(car.getId()));
        }
        return cars;
    }

    private void deleteFromCarsDrivers(Car car) {
        String deleteCarsDriversQuery = "DELETE FROM cars_drivers WHERE car_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection
                         .prepareStatement(deleteCarsDriversQuery)) {
            preparedStatement.setLong(1, car.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete car " + car
                    + " from table cars_drivers", e);
        }
    }

    private void insertIntoCarsDrivers(Car car) {
        String insertCarsDriversQuery = "INSERT INTO cars_drivers"
                + " (car_id, driver_id) VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection
                         .prepareStatement(insertCarsDriversQuery)) {
            preparedStatement.setLong(1, car.getId());
            for (Driver driver : car.getDrivers()) {
                preparedStatement.setLong(2, driver.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot insert car " + car
                    + " into table cars_drivers", e);
        }
    }

    private Car getCar(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("c.id", Long.class);
        String model = resultSet.getObject("c.model", String.class);
        Long manufacturerId = resultSet.getObject("c.manufacturer_id", Long.class);
        String nameManufacturer = resultSet.getObject("m.name", String.class);
        String countryManufacturer = resultSet.getObject("m.country", String.class);
        Manufacturer manufacturer = new Manufacturer(nameManufacturer, countryManufacturer);
        manufacturer.setId(manufacturerId);
        Car car = new Car(model, manufacturer);
        car.setId(id);
        return car;
    }

    private List<Driver> getDrivers(Long carId) {
        List<Driver> drivers = new ArrayList<>();
        String getDriversQuery = "SELECT * FROM cars_drivers cd"
                + " INNER JOIN drivers d ON cd.driver_id = d.id"
                + " WHERE cd.car_id = ? AND d.deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection
                         .prepareStatement(getDriversQuery)) {
            preparedStatement.setLong(1, carId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getObject("name", String.class);
                String licenceNumber = resultSet.getObject("licenceNumber", String.class);
                Long driverId = resultSet.getObject("id", Long.class);
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

