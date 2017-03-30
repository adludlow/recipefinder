package recipefinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import recipefinder.model.Recipe;
import recipefinder.model.RecipeRepository;

import java.util.ArrayList;
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

    @RequestMapping(path="/{id}", method=GET)
    public Recipe getRecipe(@PathVariable(value="id") Long id) {
        return recipeRepo.findOne(id);
    }

    @RequestMapping(method=POST)
    public Recipe saveRecipe(@RequestBody Recipe recipe) {
        return recipeRepo.save(recipe);
    }

    @RequestMapping(method=GET)
    public List<Recipe> getRecipes(@RequestParam(value = "", required = false)String searchString) {
        Iterable<Recipe> recipeIterable = recipeRepo.findAll();
        List<Recipe> recipeList = new ArrayList<Recipe>();
        recipeIterable.forEach(recipeList::add);

        return recipeList;
    }
}
