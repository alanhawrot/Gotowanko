package controllers.users;

import controllers.users.dto.*;
import entities.User;
import exceptions.businesslogic.NoSuchResourceException;
import exceptions.businesslogic.PermissionDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
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

        return createUserResponseDTO;
    }

    @Secured(value = "ROLE_USER")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequestDTO updateUserRequestDTO) throws PermissionDeniedException, NoSuchResourceException {
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User modifiedUser = usersRepository.findOne(id);

        if (modifiedUser == null) {
            throw new NoSuchResourceException("There is no user with given id");
        }

        if (authenticatedUserEmail.compareTo(modifiedUser.getEmail()) != 0) {
            throw new PermissionDeniedException("You are not the owner of given account");
        }

        modifiedUser.setEmail(updateUserRequestDTO.getEmail());
        modifiedUser.setPassword(updateUserRequestDTO.getPassword());

        usersRepository.save(modifiedUser);
    }

    @Secured(value = "ROLE_USER")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable Long id) throws PermissionDeniedException, NoSuchResourceException {
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User modifiedUser = usersRepository.findOne(id);

        if (modifiedUser == null) {
            throw new NoSuchResourceException("There is no user with given id");
        }

        if (authenticatedUserEmail.compareTo(modifiedUser.getEmail()) != 0) {
            throw new PermissionDeniedException("You are not the owner of given account");
        }

        usersRepository.delete(modifiedUser);
    }

    @Secured(value = "ROLE_USER")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public GetUserResponseDTO getUser(@PathVariable Long id) throws NoSuchResourceException {
        User user = usersRepository.findOne(id);

        if (user != null) {
            GetUserResponseDTO userResponseDTO = new GetUserResponseDTO();
            userResponseDTO.setId(user.getId());
            userResponseDTO.setEmail(user.getEmail());
            userResponseDTO.setRegistrationDate(user.getRegistrationDate());
            userResponseDTO.setLastLogged(user.getLastLogged());
            userResponseDTO.setRecipes(user.getRecipes());
            userResponseDTO.setComments(user.getComments());

            return userResponseDTO;
        } else {
            throw new NoSuchResourceException("There is no user with given id");
        }
    }
}
