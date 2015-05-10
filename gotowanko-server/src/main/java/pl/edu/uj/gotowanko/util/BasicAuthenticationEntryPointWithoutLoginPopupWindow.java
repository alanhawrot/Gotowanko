package pl.edu.uj.gotowanko.util;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Does not add header that requires a username and password via a popup
 *
 * @author bchild
 */
public class BasicAuthenticationEntryPointWithoutLoginPopupWindow extends BasicAuthenticationEntryPoint {

    public BasicAuthenticationEntryPointWithoutLoginPopupWindow(String realmName) {
        setRealmName(realmName);
    }

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }

}