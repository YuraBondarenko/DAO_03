package core.basesyntax;

import core.basesyntax.lib.Injector;
import core.basesyntax.model.Manufacturer;
import core.basesyntax.service.ManufacturerService;

public class Main {
    private static final Injector injector = Injector.getInstance("core.basesyntax");

    public static void main(String[] args) {
        ManufacturerService manufacturerService = (ManufacturerService)
                injector.getInstance(ManufacturerService.class);
        manufacturerService.create(new Manufacturer("Honda", "Japan"));
        manufacturerService.create(new Manufacturer("Hyundai", "South Korea"));
        manufacturerService.create(new Manufacturer("Toyota", "Japan"));
        System.out.println(manufacturerService.getAll());
        System.out.println(manufacturerService.get(3L));
        manufacturerService.delete(3L);
        System.out.println("After deleting");
        System.out.println(manufacturerService.getAll());
        Manufacturer tesla = manufacturerService.create(new Manufacturer("Tesla", "USA"));
        System.out.println(manufacturerService.getAll());
        tesla.setName("new Tesla");
        manufacturerService.update(tesla);
        System.out.println("After updating");
        System.out.println(manufacturerService.getAll());
        manufacturerService.delete(1L);
        System.out.println("After deleting");
        System.out.println(manufacturerService.getAll());
        manufacturerService.delete(new Manufacturer("new Tesla", "USA"));
        System.out.println("After deleting");
        System.out.println(manufacturerService.getAll());
    }
}
