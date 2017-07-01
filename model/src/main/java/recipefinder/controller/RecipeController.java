package recipefinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipefinder.model.*;

import java.util.*;

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
    public ResponseEntity<Recipe> getRecipe(@PathVariable(value="id") Long id) {
        return new ResponseEntity<Recipe>(recipeRepo.findOne(id), HttpStatus.OK);
    }

    @RequestMapping(method=POST)
    public ResponseEntity<Recipe> saveRecipe(@RequestBody Recipe recipe) throws RecipeConflictException {
        // Check if recipe exists using url
        List<Recipe> existing = recipeRepo.findByUrl(recipe.getUrl());
        if(existing.size() > 0){
            throw new RecipeConflictException("Recipe with url: " + recipe.getUrl() + " already exists.");
        }
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
        return new ResponseEntity<Recipe>(recipeRepo.save(recipe), HttpStatus.OK);
    }

    @RequestMapping(method=GET)
    public ResponseEntity<List<Recipe>> getRecipesByIngredient(@RequestParam(value = "url", defaultValue = "") String url,
                                               @RequestParam(value = "includesAll", defaultValue = "false") Boolean includesAll,
                                               @RequestParam(value = "includesOnly", defaultValue = "false") Boolean includesOnly,
                                               @RequestParam(value = "ingredient", required = false) String[] ingredientArray) {
        // Search by URL takes precedence
        if(url.length() > 0){
            return new ResponseEntity<List<Recipe>>(recipeRepo.findByUrl(url), HttpStatus.OK);
        }

        List<Recipe> recipeList = new ArrayList<Recipe>();
        if(ingredientArray != null && ingredientArray.length > 0){
            List<String> ingredientList = Arrays.asList(ingredientArray);
            Iterable<Recipe> recipeIterable = recipeRepo.findDistinctByIngredients_IngredientIn(ingredientList);
            if(includesOnly){
                for(Recipe r: recipeIterable) {
                    // find recipes contaning only listed ingredients.
                    Set<String> ingredientSet = r.getIngredientsAsSet();
                    if(ingredientSet.containsAll(ingredientList) &&
                       ingredientSet.size() == new HashSet(ingredientList).size()){
                        recipeList.add(r);
                    }
                }
            }
            else if(includesAll){
                for(Recipe r:  recipeIterable) {
                    // find recipes containing all listed ingredients.
                    if(r.getIngredientsAsSet().containsAll(ingredientList)){
                        recipeList.add(r);
                    }
                }
            }
            else {
                recipeIterable.forEach(recipeList::add);
            }
        }
        else {
            // TODO should be a PageRequest
            Iterable<Recipe> recipeIterable = recipeRepo.findAll();
            recipeIterable.forEach(recipeList::add);
        }

        return new ResponseEntity<List<Recipe>>(recipeList, HttpStatus.OK);
    }

    @ExceptionHandler(RecipeConflictException.class)
    public ResponseEntity<String> handleConflictException(RecipeConflictException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
