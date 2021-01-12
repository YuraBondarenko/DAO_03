package core.basesyntax;

import core.basesyntax.lib.Injector;
import core.basesyntax.model.Manufacturer;
import core.basesyntax.service.ManufacturerService;

public class Main {
    private static final Injector injector = Injector.getInstance("core.basesyntax");
    private static final Long ID_FOR_DELETING_3 = 3L;
    private static final Long ID_FOR_DELETING_1 = 1L;
    private static final Long ID_FOR_GETTING_2 = 2L;

    public static void main(String[] args) {
        ManufacturerService manufacturerService = (ManufacturerService)
                injector.getInstance(ManufacturerService.class);
        manufacturerService.create(new Manufacturer("Honda", "Japan"));
        manufacturerService.create(new Manufacturer("Hyundai", "South Korea"));
        manufacturerService.create(new Manufacturer("Toyota", "Japan"));
        System.out.println(manufacturerService.getAll());
        manufacturerService.delete(ID_FOR_DELETING_3);
        System.out.println(manufacturerService.get(ID_FOR_GETTING_2));
        System.out.println("After deleting");
        System.out.println(manufacturerService.getAll());
        Manufacturer tesla = manufacturerService.create(new Manufacturer("Tesla", "USA"));
        System.out.println(manufacturerService.getAll());
        tesla.setName("new Tesla");
        manufacturerService.update(tesla);
        System.out.println("After updating");
        System.out.println(manufacturerService.getAll());
        manufacturerService.delete(ID_FOR_DELETING_1);
        System.out.println("After deleting");
        System.out.println(manufacturerService.getAll());
        manufacturerService.delete(new Manufacturer("new Tesla", "USA"));
        System.out.println("After deleting");
        System.out.println(manufacturerService.getAll());
        System.out.println(manufacturerService.get(ID_FOR_GETTING_2));
    }
}
