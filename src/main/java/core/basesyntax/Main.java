package core.basesyntax;

import core.basesyntax.lib.Injector;
import core.basesyntax.model.Manufacturer;
import core.basesyntax.service.ManufacturerService;

public class Main {
    private static final Injector injector = Injector.getInstance("core.basesyntax");

    public static void main(String[] args) {
        ManufacturerService manufacturerService = (ManufacturerService)
                injector.getInstance(ManufacturerService.class);
        manufacturerService.create(new Manufacturer("Hyundai", "South Korea"));
        manufacturerService.create(new Manufacturer("Toyota", "Japan"));
        System.out.println(manufacturerService.get(1L));
        System.out.println(manufacturerService.getAll());
        System.out.println(manufacturerService.update(new Manufacturer(1L, "newToyota", "Japan")));
        System.out.println(manufacturerService.get(1L));
        manufacturerService.delete(2L);
        System.out.println(manufacturerService.getAll());
    }
}
