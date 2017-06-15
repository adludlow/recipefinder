package recipefinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import recipefinder.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by aludlow on 2/28/17.
 */
@RestController
@CrossOrigin
@RequestMapping("/recipes")
public class RecipeController {
    @Autowired
    RecipeRepository recipeRepo;
    @Autowired
    IngredientRepository ingredientRepo;

    @RequestMapping(path="/{id}", method=GET)
    public Recipe getRecipe(@PathVariable(value="id") Long id) {
        return recipeRepo.findOne(id);
    }

    @RequestMapping(method=POST)
    public Recipe saveRecipe(@RequestBody Recipe recipe) {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        for(IngredientWithAmount iwa: recipe.getIngredientsWithAmount()) {
            List<String> ingredientStrings = new ArrayList<String>(Arrays.asList(iwa.getRawIngredient().split(" ")));
            ingredientStrings.add(iwa.getRawIngredient());
            for(String i: ingredientStrings) {
                List<Ingredient> existingIngredients = ingredientRepo.findByIngredient(i);
                if(existingIngredients.size() > 0){
                    // Use existing ingredient
                    ingredients.add(existingIngredients.get(0));
                }
                else {
                    // Create new ingredient
                    Ingredient newIngredient = new Ingredient(i);
                    ingredients.add(ingredientRepo.save(newIngredient));
                }
            }
        }
        recipe.setIngredients(ingredients);
        return recipeRepo.save(recipe);
    }

    @RequestMapping(method=GET)
    public List<Recipe> getRecipes(@RequestParam(value = "exclusiveIngredientMatch", defaultValue = "false") Boolean exclusiveIngredientMatch,
                                   @RequestParam(value = "ingredient", required = false)String[] ingredientArray) {
        List<Recipe> recipeList = new ArrayList<Recipe>();
        if(ingredientArray != null && ingredientArray.length > 0){
            List<String> ingredientList = Arrays.asList(ingredientArray);
            if(exclusiveIngredientMatch){
                Iterable<Recipe> recipeIterable = recipeRepo.findDistinctByIngredientsWithAmount_RawIngredientIn(ingredientList);
                for(Recipe r:  recipeIterable) {
                    // find recipes containing all listed ingredients.
                    if(r.getIngredientsAsSet().containsAll(ingredientList)){
                        recipeList.add(r);
                    }
                }
            }
            else {
                Iterable<Recipe> recipeIterable = recipeRepo.findDistinctByIngredientsWithAmount_RawIngredientIn(Arrays.asList(ingredientArray));
                recipeIterable.forEach(recipeList::add);
            }
        }
        else {
            Iterable<Recipe> recipeIterable = recipeRepo.findAll();
            recipeIterable.forEach(recipeList::add);
        }

        return recipeList;
    }
}
