package pl.edu.uj.gotowanko.exceptions.validation;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationErrorResponseDTO {
    private String errorMessage;
    private String jsonPath;
    private String errorCode;

    public ValidationErrorResponseDTO(FieldError fieldError) {
        errorMessage = fieldError.getDefaultMessage();
        errorCode = fieldError.getCode();
        jsonPath = fieldError.getField();
    }

    public ValidationErrorResponseDTO(ObjectError oe) {
        errorCode = oe.getCode();
        errorMessage = oe.getDefaultMessage();
        jsonPath = oe.getObjectName();
    }

    public ValidationErrorResponseDTO() {

    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getJsonPath() {
        return jsonPath;
    }

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}