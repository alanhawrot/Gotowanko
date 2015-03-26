package pl.edu.uj.gotowanko.exceptions.businesslogic;

import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

/**
 * Created by michal on 23.03.15.
 */
public abstract class BusinessLogicException extends Exception {
    private String errorCode;
    private Object cargo;
    private HttpStatus httpStatus;

    protected BusinessLogicException(String message, HttpStatus httpStatus) {
        this(message, httpStatus, null);
    }

    protected BusinessLogicException(String message, HttpStatus httpStatus, Object cargo) {
        super(message);
        Assert.notNull(message);
        Assert.notNull(httpStatus);

        this.errorCode = getClass().getSimpleName();
        this.httpStatus = httpStatus;
        this.cargo = cargo;
    }

    public Object getCargo() {

        return cargo;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
