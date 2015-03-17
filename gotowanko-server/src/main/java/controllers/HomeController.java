package controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by alanhawrot on 17.03.15.
 */
@RestController
public class HomeController {

    @RequestMapping(value = {"/", "/home"})
    public String home() {
        return "home";
    }

}
