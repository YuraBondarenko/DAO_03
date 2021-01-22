package core.basesyntax.controllers.car;

import core.basesyntax.lib.Injector;
import core.basesyntax.model.Car;
import core.basesyntax.service.CarService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetMyCurrentCarsController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("core.basesyntax");
    private static final String DRIVER_ID = "id";
    private CarService carService = (CarService) injector.getInstance(CarService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = (Long) req.getSession().getAttribute(DRIVER_ID);
        List<Car> allByDriver = carService.getAllByDriver(id);
        req.setAttribute("cars", allByDriver);
        req.getRequestDispatcher("/WEB-INF/views/car/all.jsp").forward(req, resp);
    }
}
