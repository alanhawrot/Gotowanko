package controllers.users;

import controllers.users.dto.UserPOST;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import repositories.UsersRepository;

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
    public User addUser(@RequestBody UserPOST userPOST) {
        User newUser = new User();
        newUser.setEmail(userPOST.getEmail());
        newUser.setPassword(userPOST.getPassword());
        Calendar calendar = Calendar.getInstance();
        newUser.setRegistrationDate(calendar);
        newUser.setLastLogged(calendar);

        usersRepository.save(newUser);

        return newUser;
    }

    @Secured(value = "ROLE_USER")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateUser(@PathVariable Long id) {

    }

    @Secured(value = "ROLE_USER")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeUser(@PathVariable Long id) {

    }

    @Secured(value = "ROLE_USER")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        return usersRepository.findOne(id);
    }
}
