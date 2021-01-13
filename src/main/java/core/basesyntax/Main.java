package core.basesyntax;

import core.basesyntax.lib.Injector;
import core.basesyntax.model.Car;
import core.basesyntax.model.Driver;
import core.basesyntax.model.Manufacturer;
import core.basesyntax.service.CarService;
import core.basesyntax.service.DriverService;
import core.basesyntax.service.ManufacturerService;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Injector injector = Injector.getInstance("core.basesyntax");
    private static final Long HYUNDAI = 1L;
    private static final Long TESLA = 2L;

    public static void main(String[] args) {
        DriverService driverService = (DriverService)
                injector.getInstance(DriverService.class);
        CarService carService = (CarService)
                injector.getInstance(CarService.class);

        System.out.println(carService.getAll());
        Driver barak = driverService.create(new Driver("Barak", "286"));
        Driver mahdi = driverService.create(new Driver("Mahdi", "111"));
        List<Driver> driversForHyundai = new ArrayList<>();
        driversForHyundai.add(barak);
        driversForHyundai.add(mahdi);

        ManufacturerService manufacturerService = (ManufacturerService)
                injector.getInstance(ManufacturerService.class);
        Manufacturer hyundai = manufacturerService
                .create(new Manufacturer("Hyundai", "South Korea"));
        Car hyundaiCar = carService.create(new Car("Hyundai", hyundai));
        hyundaiCar.setDrivers(driversForHyundai);
        List<Driver> driversForTesla = new ArrayList<>();
        driversForTesla.add(barak);
        System.out.println(carService.getAll());
        System.out.println(carService.getAllByDriver(TESLA));
        System.out.println(driverService.getAll());
        driverService.delete(HYUNDAI);
        System.out.println(driverService.getAll());
        System.out.println(carService.getAllByDriver(TESLA));
        carService.delete(HYUNDAI);
        System.out.println(carService.getAllByDriver(TESLA));
        System.out.println(driverService.get(TESLA).getLicenceNumber());
        mahdi.setLicenceNumber("110");
        driverService.update(mahdi);
        System.out.println(driverService.get(TESLA).getLicenceNumber());
        Manufacturer tesla = manufacturerService.create(new Manufacturer("Tesla", "USA"));
        Car teslaCar = carService.create(new Car("Tesla", tesla));
        carService.addDriverToCar(mahdi, teslaCar);
        System.out.println(carService.getAllByDriver(TESLA));
        carService.removeDriverFromCar(mahdi, hyundaiCar);
        System.out.println(carService.getAllByDriver(TESLA));
        System.out.println(carService.get(TESLA));
    }
}
