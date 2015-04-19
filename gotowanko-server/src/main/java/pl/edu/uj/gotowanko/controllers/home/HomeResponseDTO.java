package pl.edu.uj.gotowanko.controllers.home;

import com.fasterxml.jackson.annotation.JsonInclude;

public class HomeResponseDTO {
    private String value;
    private String nullValue;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNullValue() {
        return nullValue;
    }

    public void setNullValue(String nullValue) {
        this.nullValue = nullValue;
    }
}
