package pl.edu.uj.gotowanko.exceptions.businesslogic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by michal on 22.03.15.
 * Przechwytuje błędy logiki.
 */
@ControllerAdvice
public class BusinessLogicExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(BusinessLogicExceptionHandler.class.getSimpleName());

    @ExceptionHandler(value = BusinessLogicException.class)
    public ResponseEntity<BusinessLogicExceptionResponseDTO> exceptionHandler(BusinessLogicException e) {
        BusinessLogicExceptionResponseDTO businessLogicResponseDTO = new BusinessLogicExceptionResponseDTO(e);
        HttpStatus httpStatus = e.getHttpStatus();
        return new ResponseEntity<>(businessLogicResponseDTO, httpStatus);
    }
}

