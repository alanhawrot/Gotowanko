package pl.edu.uj.gotowanko.exceptions.businesslogic;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by michal on 23.03.15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessLogicExceptionResponseDTO {
    public String errorCode;
    public String errorMessage;
    public Object cargo;

    public BusinessLogicExceptionResponseDTO(BusinessLogicException e) {
        errorCode = e.getErrorCode();
        errorMessage = e.getMessage();
        cargo = e.getCargo();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getCargo() {
        return cargo;
    }

    public void setCargo(Object cargo) {
        this.cargo = cargo;
    }
}
