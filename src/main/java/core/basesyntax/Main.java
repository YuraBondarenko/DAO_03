package core.basesyntax;

import core.basesyntax.lib.Injector;
import core.basesyntax.model.Driver;
import core.basesyntax.service.CarService;
import core.basesyntax.service.DriverService;
import core.basesyntax.service.ManufacturerService;

public class Main {
    private static final Injector injector = Injector.getInstance("core.basesyntax");

    public static void main(String[] args) {
        ManufacturerService manufacturerService = (ManufacturerService)
                injector.getInstance(ManufacturerService.class);
        DriverService driverService = (DriverService) injector.getInstance(DriverService.class);
        CarService carService = (CarService)
                injector.getInstance(CarService.class);
        System.out.println(carService.getAll());
        System.out.println(driverService.getAll());
        driverService.create(new Driver("name3", "132r"));
        System.out.println(driverService.getAll());
    }
}
