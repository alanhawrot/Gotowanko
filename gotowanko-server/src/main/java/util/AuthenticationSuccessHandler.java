package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import repositories.UsersRepository;

/**
 * Created by alanhawrot on 23.03.15.
 */
public class AuthenticationSuccessHandler implements ApplicationListener<AuthenticationSuccessEvent> {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandler.class.getSimpleName());

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {
        logger.info(authenticationSuccessEvent.getAuthentication().getName());
    }
}
