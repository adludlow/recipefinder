package recipefinder.model.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import recipefinder.model.IngredientWithAmount;
import recipefinder.model.MethodStep;
import recipefinder.model.Recipe;
import recipefinder.model.RecipeRepository;

import javax.transaction.Transactional;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by aludlow on 2/22/17.
 */
@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class RecipeTests {

    @Autowired
    RecipeRepository recipeRepository;
    private Recipe recipe;

    @Before
    public void init() {
        recipe = new Recipe("Pavlova",
                new ArrayList<MethodStep>(),
                new ArrayList<IngredientWithAmount>(),
                "http://recipes.com/pavlova"
                );
    }

    @Test
    public void findSavedRecipeById() {
        recipe = recipeRepository.save(recipe);
        System.out.println(recipe.getId());
        assertThat(recipeRepository.findOne(recipe.getId()), is(recipe));
    }
}
