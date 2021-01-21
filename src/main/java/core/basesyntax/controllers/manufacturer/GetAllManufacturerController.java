package core.basesyntax.controllers.manufacturer;

import core.basesyntax.lib.Injector;
import core.basesyntax.model.Manufacturer;
import core.basesyntax.service.ManufacturerService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetAllManufacturerController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("core.basesyntax");
    private ManufacturerService manufacturerService = (ManufacturerService) injector
            .getInstance(ManufacturerService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Manufacturer> manufacturers = manufacturerService.getAll();
        req.setAttribute("manufacturers", manufacturers);
        req.getRequestDispatcher("/WEB-INF/views/manufacturer/all.jsp").forward(req, resp);
    }
}
