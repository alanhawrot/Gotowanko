package pl.edu.uj.gotowanko.controllers.recipes;

import org.springframework.stereotype.Component;
import pl.edu.uj.gotowanko.entities.Recipe;
import pl.edu.uj.gotowanko.util.GetFilteredRecipesSortOptions;

import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by alanhawrot on 04.05.15.
 */
@Component
public class RecipeComparatorFactory {

    private HashMap<GetFilteredRecipesSortOptions, Comparator<Recipe>> recipeComparatorsHashMap = new HashMap<>();

    public RecipeComparatorFactory() {
        registerRecipeComparator(GetFilteredRecipesSortOptions.BY_DATE_ADDED, (o1, o2) -> o1.getDateAdded().compareTo(o2.getDateAdded()));

        registerRecipeComparator(GetFilteredRecipesSortOptions.BY_NUMBER_OF_LIKES, (o1, o2) -> {
            if (o1.getUserLikes().size() < o2.getUserLikes().size()) {
                return -1;
            } else if (o1.getUserLikes().size() > o2.getUserLikes().size()) {
                return 1;
            } else {
                return 0;
            }
        });

        registerRecipeComparator(GetFilteredRecipesSortOptions.BY_TITLE_ALPHABETICALLY, (o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()));
    }

    public Comparator<Recipe> createRecipeComparator(GetFilteredRecipesSortOptions sortOption) {
        Comparator<Recipe> recipeComparator = recipeComparatorsHashMap.get(sortOption);
        return recipeComparator == null ? recipeComparatorsHashMap.get(GetFilteredRecipesSortOptions.BY_DATE_ADDED) : recipeComparator;
    }

    public void registerRecipeComparator(GetFilteredRecipesSortOptions sortOption, Comparator<Recipe> recipeComparator) {
        recipeComparatorsHashMap.putIfAbsent(sortOption, recipeComparator);
    }

    public void unregisterRecipeComparator(GetFilteredRecipesSortOptions sortOption) {
        recipeComparatorsHashMap.remove(sortOption);
    }
}
