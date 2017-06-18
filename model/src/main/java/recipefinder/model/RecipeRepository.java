package recipefinder.model;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by aludlow on 2/19/17.
 */
public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long> {
    List<Recipe> findDistinctByIngredientsWithAmount_RawIngredientIn(List<String> rawIngedients);
    List<Recipe> findDistinctByIngredients_IngredientIn(List<String> ingredients);
    List<Recipe> findByUrl(String url);

}
