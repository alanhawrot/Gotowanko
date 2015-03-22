package controllers.home;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by alanhawrot on 17.03.15.
 */
@RestController
public class HomeController {

    @RequestMapping(value = {"/home"})
    public HomeResponseDTO home(@RequestBody HomeRequestDTO dto) {
        HomeResponseDTO homeResponseDTO = new HomeResponseDTO();
        homeResponseDTO.setValue(dto.getValue());
        return homeResponseDTO;
    }
}
