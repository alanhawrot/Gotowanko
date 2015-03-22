package controllers.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by alanhawrot on 17.03.15.
 */
@RestController
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class.getSimpleName());

    @RequestMapping(value = {"/home/{id}"})
    public HomeResponseDTO home(
            @PathVariable("id") String id, // niestety Spring jeszcze nie wspiera walidacji na @PathVariable
            // nieraz wygodniej będzie wykorzystać body :P
            @Valid @RequestBody HomeRequestDTO dto) {
        HomeResponseDTO homeResponseDTO = new HomeResponseDTO();
        homeResponseDTO.setValue(dto.getValue());
        return homeResponseDTO;
    }


}