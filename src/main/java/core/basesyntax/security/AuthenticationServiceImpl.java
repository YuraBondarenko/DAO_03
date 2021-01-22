package core.basesyntax.security;

import core.basesyntax.lib.Inject;
import core.basesyntax.lib.Service;
import core.basesyntax.model.Driver;
import core.basesyntax.service.DriverService;
import javax.naming.AuthenticationException;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private DriverService driverService;

    @Override
    public Driver login(String login, String password) throws AuthenticationException {
        Driver driverByLogin = driverService.findByLogin(login).orElseThrow(() ->
                new AuthenticationException("Incorrect login"));
        if (driverByLogin.getPassword().equals(password)) {
            return driverByLogin;
        }
        throw new AuthenticationException("Incorrect password");
    }
}
