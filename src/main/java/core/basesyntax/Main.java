package core.basesyntax;

import core.basesyntax.lib.Injector;
import core.basesyntax.model.Manufacturer;
import core.basesyntax.service.CarService;
import core.basesyntax.service.ManufacturerService;

public class Main {
    private static final Injector injector = Injector.getInstance("core.basesyntax");

    public static void main(String[] args) {
        ManufacturerService manufacturerService = (ManufacturerService)
                injector.getInstance(ManufacturerService.class);
        CarService carService = (CarService)
                injector.getInstance(CarService.class);
        /*System.out.println(manufacturerService.create(
        new Manufacturer("Hyundai", "South Korea")));*/
        /*manufacturerService.create(new Manufacturer("Toyota", "Japan"));
        System.out.println(manufacturerService.get(1L));
        System.out.println(manufacturerService.getAll());
        System.out.println(manufacturerService.update(new Manufacturer(1L, "newToyota", "Japan")));
        System.out.println(manufacturerService.get(1L));
        manufacturerService.delete(2L);
        System.out.println(manufacturerService.getAll());*/
        /*System.out.println(driverService.create(new Driver("name", "number")));*/
        /* Driver driver = new Driver("newName", "newNumber");
        driver.setId(2L);
        System.out.println(driverService.get(2L));
        System.out.println(driverService.getAll());
        System.out.println(driverService.delete(1L));
        System.out.println(driverService.update(driver));*/
        /* Manufacturer manufacturer = manufacturerService.get(1L);
        System.out.println(manufacturer);*/
        /*      manufacturerService.create(manufacturer);*/
        /*System.out.println(carService.create(new Car("car", manufacturer)));*/
        /* Car car = new Car("newCar", manufacturer);
        car.setId(1L);
        System.out.println(carService.get(1L));*/
        /*carService.addDriverToCar(driverService.get(1L), car);
        carService.addDriverToCar(driverService.get(2L), car);*/
        /* System.out.println(carService.get(1L));*/
        /*carService.removeDriverFromCar(driverService.get(1L), car);
        carService.removeDriverFromCar(driverService.get(2L), car);*/
        /*  System.out.println(carService.get(1L));
        System.out.println(carService.getAllByDriver(1L));
        carService.addDriverToCar(driverService.get(1L), carService.get(4L));
        System.out.println(carService.getAllByDriver(1L));*/

        /*Manufacturer manufacturer1 = new Manufacturer("manufacturer1", "country1");
        Manufacturer manufacturer2 = new Manufacturer("manufacturer2", "country2");
        Driver driver1 = new Driver("name1", "licence1");
        Driver driver2 = new Driver("name2", "licence2");
        driver1 = driverService.create(driver1);
        driver2 = driverService.create(driver2);
        manufacturer1 = manufacturerService.create(manufacturer1);
        manufacturer2 = manufacturerService.create(manufacturer2);
        */

        Manufacturer manufacturer1 = manufacturerService.get(1L);
        Manufacturer manufacturer2 = manufacturerService.get(2L);

        /*Car car1 = new Car("model5", manufacturer1);
        Car car2 = new Car("model6", manufacturer1);
        Car car = carService.create(car1);
        Car car3 = carService.create(car2);*/
        /* carService.addDriverToCar(driver1, carService.get(6L));
        carService.addDriverToCar(driver2, carService.get(4L));
        carService.addDriverToCar(driver2, carService.get(2L));*/
        /*System.out.println(carService.getAll());
        car1.setModel("newModel");
        carService.update(car1);
        System.out.println(carService.getAll());
        driver1.setName("newName");
        driverService.update(driver1);*/
        /*System.out.println(carService.getAll());
        System.out.println(carService.getAllByDriver(1L));
        *//*carService.removeDriverFromCar(driver2, carService.get(4L));*//*
        System.out.println(carService.get(2L));
        Car car = carService.get(2L);
        car.setModel("1111111");
        carService.update(car);
        Car car11 = carService.get(2L);
        car11.setModel("mode123");
        carService.update(car11);
        System.out.println(carService.get(2L));
        System.out.println(carService.getAllByDriver(1L));
        carService.delete(6L);
        System.out.println(carService.getAllByDriver(1L));
        car11.setModel("001230");
        carService.update(car11);
        System.out.println(carService.getAllByDriver(1L));
        DriverService driverService = (DriverService)
                injector.getInstance(DriverService.class);
        Driver driver1 = driverService.get(1L);
        carService.removeDriverFromCar(driver1, car11);
        System.out.println(carService.getAllByDriver(1L));
        System.out.println(carService.getAllByDriver(2L));*/
        /*        carService.addDriverToCar(driver1, carService.get(1L));
        carService.addDriverToCar(driver1, carService.get(2L));
        carService.addDriverToCar(driver1, carService.get(3L));
        carService.addDriverToCar(driver1, carService.get(4L));*/
        /*System.out.println(carService.getAllByDriver(1L));*/
        /*        carService.removeDriverFromCar(driver1, carService.get(1L));
        carService.removeDriverFromCar(driver1, carService.get(2L));
        carService.removeDriverFromCar(driver1, carService.get(3L));
        carService.removeDriverFromCar(driver1, carService.get(4L));*/
        /*System.out.println(carService.getAllByDriver(1L));
        carService.addDriverToCar(driver1, carService.get(2L));
        Driver driver2 = driverService.get(2L);
        carService.addDriverToCar(driver2, carService.get(2L));
        System.out.println(carService.getAllByDriver(1L));
        System.out.println(carService.getAllByDriver(2L));
        carService.create(car);
        System.out.println(carService.getAllByDriver(1L));
        System.out.println(carService.getAllByDriver(2L));*/
        System.out.println(carService.get(2L));
        System.out.println(carService.getAllByDriver(2L));
    }
}
