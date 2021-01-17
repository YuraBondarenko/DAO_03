package core.basesyntax.service;

import core.basesyntax.dao.CarDao;
import core.basesyntax.exceptions.DataProcessingException;
import core.basesyntax.lib.Inject;
import core.basesyntax.lib.Service;
import core.basesyntax.model.Car;
import core.basesyntax.model.Driver;
import core.basesyntax.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
                + " (driver_id, car_id)"
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
        String removeDriverFromCarQuery = "DELETE FROM cars_drivers"
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
        return carDao.getAllByDriver(driverId);
    }

    private void removeAllDriversFromCar(Long id) {
        String removeDriverFromCarQuery = "DELETE FROM cars_drivers"
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
}
