package pl.edu.uj.gotowanko.controllers.home;

import pl.edu.uj.gotowanko.exceptions.businesslogic.NoSuchResourceException;
import pl.edu.uj.gotowanko.exceptions.businesslogic.PermissionDeniedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by alanhawrot on 17.03.15.
 */
@RestController(value = "/rest")
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class.getSimpleName());

    @RequestMapping(value = {"/", "/home"})
    public String home() {
        return "OK";
    }

    @RequestMapping(value = {"/home/{id}"})
    public HomeResponseDTO homeTest(
            @PathVariable("id") String id, // niestety Spring jeszcze nie wspiera walidacji na @PathVariable
            // nieraz wygodniej będzie wykorzystać body :P
            @Valid @RequestBody HomeRequestDTO dto) {
        HomeResponseDTO homeResponseDTO = new HomeResponseDTO();
        homeResponseDTO.setValue(dto.getValue());
        return homeResponseDTO;
    }

    @RequestMapping(value = {"/home/exception"})
    public void homeExceptionTest() throws NoSuchResourceException, PermissionDeniedException {
        method();
    }

    private void method() throws NoSuchResourceException, PermissionDeniedException {
        if ("".equals("")) {
            throw new NoSuchResourceException("Użytkownik nieodnaleziony.");
        } else if ("".equals("")) {
            throw new PermissionDeniedException("Nieprawidłowe hasło");
        }
    }
}