package controllers.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by alanhawrot on 17.03.15.
 */
@RestController
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class.getSimpleName());

    @RequestMapping(value = {"/", "/home"})
    public String home() {
        return "Home";
    }
}