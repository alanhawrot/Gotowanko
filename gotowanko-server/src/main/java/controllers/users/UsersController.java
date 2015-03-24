package controllers.users;

import controllers.users.dto.CreateUserRequestDTO;
import controllers.users.dto.CreateUserResponseDTO;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import repositories.UsersRepository;

import javax.validation.Valid;
import java.util.Calendar;

/**
 * Created by alanhawrot on 22.03.15.
 */
@RestController
@RequestMapping(value = "/users")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponseDTO createUser(@Valid @RequestBody CreateUserRequestDTO createUserRequestDTO) {
        User newUser = new User();
        newUser.setEmail(createUserRequestDTO.getEmail());
        newUser.setPassword(createUserRequestDTO.getPassword());
        Calendar calendar = Calendar.getInstance();
        newUser.setRegistrationDate(calendar);
        newUser.setLastLogged(calendar);

        usersRepository.save(newUser);

        CreateUserResponseDTO createUserResponseDTO = new CreateUserResponseDTO();
        createUserResponseDTO.setId(newUser.getId());
        createUserResponseDTO.setEmail(newUser.getEmail());
        createUserResponseDTO.setRegistrationDate(newUser.getRegistrationDate());
        createUserResponseDTO.setLastLogged(newUser.getLastLogged());

        return createUserResponseDTO;
    }

    @Secured(value = "IS_AUTHENTICATED_ANONYMOUSLY")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateUser(@PathVariable Long id) {

    }

    @Secured(value = "IS_AUTHENTICATED_ANONYMOUSLY")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeUser(@PathVariable Long id) {
        usersRepository.delete(id);
    }

    @Secured(value = "IS_AUTHENTICATED_ANONYMOUSLY")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        return usersRepository.findOne(id);
    }
}
