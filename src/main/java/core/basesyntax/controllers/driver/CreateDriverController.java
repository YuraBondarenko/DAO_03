package core.basesyntax.controllers.driver;

import core.basesyntax.lib.Injector;
import core.basesyntax.model.Driver;
import core.basesyntax.service.DriverService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateDriverController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("core.basesyntax");
    private final DriverService driverService = (DriverService) injector
            .getInstance(DriverService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/driver/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String licenceNumber = req.getParameter("licenceNumber");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Driver driver = new Driver(name, licenceNumber);
        driver.setLogin(login);
        driver.setPassword(password);
        if (driverService.findByLogin(login).isPresent()) {
            req.setAttribute("message", "Login is already taken");
            req.getRequestDispatcher("/WEB-INF/views/driver/create.jsp").forward(req, resp);
        }
        driverService.create(driver);
        resp.sendRedirect(req.getContextPath() + "/drivers");
    }
}
