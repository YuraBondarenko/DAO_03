package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.lib.Dao;
import core.basesyntax.model.Car;
import core.basesyntax.model.Driver;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Dao
public class CarDaoImpl implements CarDao {

    @Override
    public Car create(Car car) {
        Storage.addCar(car);
        return car;
    }

    @Override
    public Optional<Car> get(Long id) {
        return Storage.cars.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Car> getAll() {
        return Storage.cars;
    }

    @Override
    public Car update(Car car) {
        IntStream.range(0, Storage.cars.size())
                .filter(i -> Storage.cars.get(i).getId().equals(car.getId()))
                .forEach(i -> Storage.cars.set(i, car));
        return car;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.cars.removeIf(m -> m.getId().equals(id));
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        return getAll().stream()
                .filter(c -> c.getDrivers().stream()
                        .anyMatch(s -> s.getId().equals(driverId)))
                .collect(Collectors.toList());
    }
}