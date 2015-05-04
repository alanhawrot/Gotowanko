package pl.edu.uj.gotowanko.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import java.net.InetAddress;

import static java.lang.String.format;

/**
 * Created by michal on 03.05.15.
 */
@Service
public class PathService {

    private String ROOT_URL = format("http://%s:%s", "localhost", "8080");

    public String getServerRoot() {
        return ROOT_URL + "/rest";
    }

    public String getWebRoot() { //TODO: fix web address
        return ROOT_URL;
    }

    public String getWebUpdatePropositionPath(Long id) {
        return format("%s/recipe_update_proposition/%d", getWebRoot(), id);
    }

    public String getWebRejectPropositionPath(Long id) {
        return format("%s/recipe_update_proposition/%d", getWebRoot(), id);
    }
}
