package recipefinder.worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import recipefinder.model.Recipe;
import recipefinder.model.RecipeRepository;

import java.util.List;

/**
 * Created by aludlow on 6/20/17.
 */
public class IngredientMatcher {
    public IngredientMatcher() {}

    @Autowired
    RecipeRepository recipeRepo;

    public void matchIngredients() {
        Page<Recipe> p = recipeRepo.findAll(new PageRequest(0, 100));
        for(Recipe r: p){
            System.out.println(r);
        }
    }
}
