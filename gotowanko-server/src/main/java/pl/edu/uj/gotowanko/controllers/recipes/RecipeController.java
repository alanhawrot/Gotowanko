package pl.edu.uj.gotowanko.controllers.recipes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.uj.gotowanko.controllers.recipes.dto.CreateRecipeRequestDTO;

import javax.validation.Valid;
import java.util.Map;

/**
 * Created by michal on 17.04.15.
 */
@RestController
@RequestMapping(value = "/recipes")
public class RecipeController {
    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class.getSimpleName());

    //@Secured(value = "ROLE_USER")
    @RequestMapping(method = RequestMethod.POST, headers = "content-type=multipart/*")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRecipe(
            @RequestPart(value = "meta-data", required = false) String dto,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        logger.error("meta-data " + dto);
        logger.error("file " + file);


    }
}
