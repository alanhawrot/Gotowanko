package pl.edu.uj.gotowanko.controllers.home;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

public class HomeRequestDTO {
    @Size(min = 1, max = 10) //od 1 do 10 znaków, lub null.
    private String value;

    @NotEmpty // null nie przejdzie, ani pusta kolekcja
    @Size(min = 1) //tutaj null przejdzie, w przeciwieństwie do powyższej.
    Collection<String> collection;

    @Range(min = 5, max = 6) //alternatywa dla dwóch poniższych, null przejdzie.
    @Min(value = 5)
    @Max(value = 6)
    Integer val;

    @NotBlank //nie przepuszcza null ani spacji, to jest ciekawsze dla stringów niż @NotEmpty
    private String nullValue;

    @NotNull
    @Email //null przechodzi.
    private String email;

    @Valid //żeby klasy wewnętrzne się też zwalidowały, również tu trzeba użyć @Valid.
    @NotNull
    HomeRequestDTO innerDTO;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<String> getCollection() {
        return collection;
    }

    public void setCollection(Collection<String> collection) {
        this.collection = collection;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }

    public HomeRequestDTO getInnerDTO() {
        return innerDTO;
    }

    public void setInnerDTO(HomeRequestDTO innerDTO) {
        this.innerDTO = innerDTO;
    }
}
