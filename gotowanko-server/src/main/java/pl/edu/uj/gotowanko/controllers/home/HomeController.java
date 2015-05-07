package pl.edu.uj.gotowanko.controllers.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by alanhawrot on 17.03.15.
 */
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class.getSimpleName());

    @RequestMapping(value = {"/", "/home", "/web"})
    public String home() {
        return "index.html";
    }

}