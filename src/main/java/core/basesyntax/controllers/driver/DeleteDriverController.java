package core.basesyntax.controllers.driver;

import core.basesyntax.lib.Injector;
import core.basesyntax.service.DriverService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteDriverController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("core.basesyntax");
    private final DriverService driverService = (DriverService) injector.getInstance(DriverService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        driverService.delete(Long.valueOf(id));
        resp.sendRedirect(req.getContextPath() + "/drivers");
    }
}
