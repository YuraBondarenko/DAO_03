package core.basesyntax.db;

import core.basesyntax.model.Car;
import core.basesyntax.model.Driver;
import core.basesyntax.model.Manufacturer;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    public static final List<Manufacturer> manufacturers = new ArrayList<>();
    public static final List<Driver> drivers = new ArrayList<>();
    public static final List<Car> cars = new ArrayList<>();
    private static Long manufacturerId = 0L;
    private static Long driverId = 0L;
    private static Long carsId = 0L;

    public static void addManufacturer(Manufacturer manufacturer) {
        manufacturer.setId(++manufacturerId);
        manufacturers.add(manufacturer);
    }

    public static void addDriver(Driver driver) {
        driver.setId(++driverId);
        drivers.add(driver);
    }

    public static void addCar(Car car) {
        car.setId(++carsId);
        cars.add(car);
    }
}
