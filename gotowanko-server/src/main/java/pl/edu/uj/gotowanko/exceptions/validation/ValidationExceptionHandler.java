package pl.edu.uj.gotowanko.exceptions.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michal on 22.03.15.
 * Przechwytuje błędy walidacji wygenerowane przez @Valid i zwraca sformatowaną listę błędów jako JSON z kodem 412.
 */
@ControllerAdvice
public class ValidationExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ValidationExceptionHandler.class.getSimpleName());

    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public List<ValidationErrorResponseDTO> exceptionHandler(MethodArgumentNotValidException e) {
        List<ValidationErrorResponseDTO> errors= new ArrayList<>(3);
        for(ObjectError oe : e.getBindingResult().getAllErrors()) {
            if(oe instanceof FieldError)  {
                errors.add(new ValidationErrorResponseDTO((FieldError)oe));
            } else {
                errors.add(new ValidationErrorResponseDTO(oe));
            }
        }
        return errors;
    }
}

