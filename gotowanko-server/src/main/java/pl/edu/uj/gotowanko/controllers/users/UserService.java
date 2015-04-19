package pl.edu.uj.gotowanko.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.edu.uj.gotowanko.entities.User;
import pl.edu.uj.gotowanko.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Optional<String> getCurrentlyLoggedUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? Optional.ofNullable(authentication.getName()) : Optional.empty();
    }

    public Optional<User> getCurrentlyLoggedUser() {

        Optional<String> currentlyLoggedUserEmail = getCurrentlyLoggedUserEmail();
        if (currentlyLoggedUserEmail.isPresent())
            return Optional.of(userRepository.findByEmail(currentlyLoggedUserEmail.get()));
        else
            return Optional.empty();

    }

}