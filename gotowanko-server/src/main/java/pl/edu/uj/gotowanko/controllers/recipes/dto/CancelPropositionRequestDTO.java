package pl.edu.uj.gotowanko.controllers.recipes.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by michal on 04.05.15.
 */
public class CancelPropositionRequestDTO {
    @NotBlank
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
