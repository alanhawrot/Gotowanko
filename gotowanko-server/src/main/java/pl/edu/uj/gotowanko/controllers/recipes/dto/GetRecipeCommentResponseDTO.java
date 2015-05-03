package pl.edu.uj.gotowanko.controllers.recipes.dto;

import java.util.Calendar;

/**
 * Created by michal on 03.05.15.
 */
public class GetRecipeCommentResponseDTO {
    private Long id;
    private String content;
    private Calendar lastEdited;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Calendar getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(Calendar lastEdited) {
        this.lastEdited = lastEdited;
    }


}
