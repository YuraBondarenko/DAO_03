package core.basesyntax.service;

import core.basesyntax.dao.CarDao;
import core.basesyntax.lib.Inject;
import core.basesyntax.lib.Service;
import core.basesyntax.model.Car;
import core.basesyntax.model.Driver;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    @Inject
    private CarDao carDao;

    @Inject
    private DriverService driverService;

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
        return carDao.delete(id);
    }

    @Override
    public void addDriverToCar(Driver driver, Car car) {
        if (!driverService.getAll().contains(driver)) {
            driverService.create(driver);
        }
        List<Driver> cars = new ArrayList<>();
        cars.addAll(car.getDrivers());
        cars.add(driver);
        car.setDrivers(cars);
        update(car);
    }

    @Override
    public void removeDriverFromCar(Driver driver, Car car) {
        List<Driver> cars = new ArrayList<>();
        cars.addAll(car.getDrivers());
        Driver driverToRemove = cars.stream()
                .filter(s -> s.equals(driver))
                .findFirst()
                .get();
        cars.remove(driverToRemove);
        car.setDrivers(cars);
        update(car);
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        return carDao.getAllByDriver(driverId);
    }
}
