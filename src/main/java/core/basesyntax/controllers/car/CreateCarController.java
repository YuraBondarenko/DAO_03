package core.basesyntax.controllers.car;

import core.basesyntax.lib.Injector;
import core.basesyntax.model.Car;
import core.basesyntax.model.Manufacturer;
import core.basesyntax.service.CarService;
import core.basesyntax.service.ManufacturerService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateCarController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("core.basesyntax");
    private CarService carService = (CarService) injector.getInstance(CarService.class);
    private ManufacturerService manufacturerService = (ManufacturerService) injector
            .getInstance(ManufacturerService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/car/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String model = req.getParameter("model");
        Long manufacturerId = Long.parseLong(req.getParameter("manufacturerId"));
        Manufacturer manufacturer = manufacturerService.get(manufacturerId);
        carService.create(new Car(model, manufacturer));
        resp.sendRedirect(req.getContextPath() + "/cars");
    }
}
