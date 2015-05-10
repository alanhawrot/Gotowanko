package pl.edu.uj.gotowanko.controllers.recipes.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

/**
 * Created by alanhawrot on 04.05.15.
 */
public class GetFilteredRecipesPageableResponseDTO {

    private Collection<FilteredRecipeResponseDTO> content = new ArrayList<>();
    private PageMetadata pageMetadata;

    public class FilteredRecipeResponseDTO {

        private Long id;
        private String title;
        private String userEmail;
        private String userName;
        private Integer numberOfLikes;
        private String photoUrl;
        private Calendar dateAdded;
        private Calendar lastEdited;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public Integer getNumberOfLikes() {
            return numberOfLikes;
        }

        public void setNumberOfLikes(Integer numberOfLikes) {
            this.numberOfLikes = numberOfLikes;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }

        public Calendar getDateAdded() {
            return dateAdded;
        }

        public void setDateAdded(Calendar dateAdded) {
            this.dateAdded = dateAdded;
        }

        public Calendar getLastEdited() {
            return lastEdited;
        }

        public void setLastEdited(Calendar lastEdited) {
            this.lastEdited = lastEdited;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserName() {
            return userName;
        }
    }

    public class PageMetadata {

        private Integer size;
        private Integer totalElements;
        private Integer totalPages;
        private Integer number;

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public Integer getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(Integer totalElements) {
            this.totalElements = totalElements;
        }

        public Integer getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }
    }

    public FilteredRecipeResponseDTO createFilteredRecipeDTO() {
        return new FilteredRecipeResponseDTO();
    }

    public PageMetadata createPageMetadata() {
        return new PageMetadata();
    }

    public Collection<FilteredRecipeResponseDTO> getContent() {
        return content;
    }

    private void setContent(Collection<FilteredRecipeResponseDTO> content) {
        this.content = content;
    }

    public void addFilteredRecipeDTO(FilteredRecipeResponseDTO filteredRecipeResponseDTO) {
        getContent().add(filteredRecipeResponseDTO);
    }

    public PageMetadata getPageMetadata() {
        return pageMetadata;
    }

    public void setPageMetadata(PageMetadata pageMetadata) {
        this.pageMetadata = pageMetadata;
    }
}
