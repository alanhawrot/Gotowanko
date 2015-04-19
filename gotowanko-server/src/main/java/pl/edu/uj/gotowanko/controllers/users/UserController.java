package pl.edu.uj.gotowanko.controllers.users;

import pl.edu.uj.gotowanko.controllers.users.dto.GetCurrentlyLoggedUserResponseDTO;
import pl.edu.uj.gotowanko.controllers.users.dto.CreateUserRequestDTO;
import pl.edu.uj.gotowanko.controllers.users.dto.CreateUserResponseDTO;
import pl.edu.uj.gotowanko.controllers.users.dto.GetUserResponseDTO;
import pl.edu.uj.gotowanko.controllers.users.dto.UpdateUserRequestDTO;
import pl.edu.uj.gotowanko.entities.*;
import pl.edu.uj.gotowanko.exceptions.businesslogic.NoSuchResourceException;
import pl.edu.uj.gotowanko.exceptions.businesslogic.PermissionDeniedException;
import pl.edu.uj.gotowanko.exceptions.businesslogic.ResourceAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.edu.uj.gotowanko.repositories.*;

import javax.validation.Valid;
import java.util.Calendar;

/**
 * Created by alanhawrot on 22.03.15.
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponseDTO createUser(@Valid @RequestBody CreateUserRequestDTO createUserRequestDTO) throws ResourceAlreadyExistsException {
        if (!userRepository.findAllByEmail(createUserRequestDTO.getEmail()).isEmpty()) {
            throw new ResourceAlreadyExistsException("User already exists.");
        }

        User newUser = new User();
        newUser.setEmail(createUserRequestDTO.getEmail());
        newUser.setPassword(createUserRequestDTO.getPassword());
        Calendar calendar = Calendar.getInstance();
        newUser.setRegistrationDate(calendar);
        newUser.setLastLogged(calendar);

        userRepository.save(newUser);

        CreateUserResponseDTO createUserResponseDTO = new CreateUserResponseDTO();
        createUserResponseDTO.setId(newUser.getId());
        createUserResponseDTO.setEmail(newUser.getEmail());
        createUserResponseDTO.setRegistrationDate(newUser.getRegistrationDate());

        return createUserResponseDTO;
    }

    @Secured(value = "ROLE_USER")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequestDTO updateUserRequestDTO) throws PermissionDeniedException, NoSuchResourceException {
        String authenticatedUserEmail = userService.getCurrentlyLoggedUserEmail().get();
        User modifiedUser = userRepository.findOne(id);

        if (modifiedUser == null) {
            throw new NoSuchResourceException("There is no user with given id");
        }

        if (authenticatedUserEmail.compareTo(modifiedUser.getEmail()) != 0) {
            throw new PermissionDeniedException("You are not the owner of given account");
        }

        modifiedUser.setEmail(updateUserRequestDTO.getEmail());
        modifiedUser.setPassword(updateUserRequestDTO.getPassword());

        userRepository.save(modifiedUser);
    }

    @Secured(value = "ROLE_USER")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable Long id) throws PermissionDeniedException, NoSuchResourceException {
        String authenticatedUserEmail = userService.getCurrentlyLoggedUserEmail().get();
        User modifiedUser = userRepository.findOne(id);

        if (modifiedUser == null) {
            throw new NoSuchResourceException("There is no user with given id");
        }

        if (authenticatedUserEmail.compareTo(modifiedUser.getEmail()) != 0) {
            throw new PermissionDeniedException("You are not the owner of given account");
        }

        userRepository.delete(modifiedUser);
    }

    @Secured(value = "ROLE_USER")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public GetUserResponseDTO getUser(@PathVariable Long id) throws NoSuchResourceException {
        User user = userRepository.findOne(id);

        if (user == null) {
            throw new NoSuchResourceException("There is no user with given id");
        }

        GetUserResponseDTO userResponseDTO = new GetUserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setRegistrationDate(user.getRegistrationDate());
        userResponseDTO.setLastLogged(user.getLastLogged());
        userResponseDTO.setRecipes(user.getRecipes());
        userResponseDTO.setComments(user.getComments());

        return userResponseDTO;
    }

    @Secured(value = "ROLE_USER")
    @RequestMapping(value = "/currently_logged", method = RequestMethod.GET)
    public GetCurrentlyLoggedUserResponseDTO getCurrentlyLoggedUser() {
        User user = userService.getCurrentlyLoggedUser().get();

        GetCurrentlyLoggedUserResponseDTO currentlyLoggedUser = new GetCurrentlyLoggedUserResponseDTO();
        currentlyLoggedUser.setId(user.getId());
        currentlyLoggedUser.setEmail(user.getEmail());
        currentlyLoggedUser.setRecipes(user.getRecipes());
        currentlyLoggedUser.setComments(user.getComments());
        currentlyLoggedUser.setRegistrationDate(user.getRegistrationDate());
        currentlyLoggedUser.setLastLogged(user.getLastLogged());

        return currentlyLoggedUser;
    }
}