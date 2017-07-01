package recipefinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import recipefinder.model.*;
import util.NgramIter;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aludlow on 6/20/17.
 */
@RestController
@CrossOrigin
@RequestMapping("/function")
public class FunctionalController {
    @Autowired
    RecipeRepository recipeRepo;
    @Autowired
    IngredientRepository ingredientRepo;
    @Autowired
    UnrecognisedIngredientRepository unrecognisedIngredientRepo;

    private Map<String, Ingredient> masterIngredients;

    public FunctionalController() {
    }

    @PostConstruct
    private void init() {
        masterIngredients = new HashMap<String, Ingredient>();

        // Initialise ingredient cache
        Iterable<Ingredient> ingredients = ingredientRepo.findAll();
        for(Ingredient i: ingredients){
            masterIngredients.put(i.getIngredient().toLowerCase(), i);
        }
    }

    @RequestMapping(path="/ingredientmatch", method= RequestMethod.GET)
    public void matchIngredients(boolean append) {
        int max_iterations = 10;
        int pageSize = 1000;
        for(int i = 0; i < max_iterations; i++) {
            Page<Recipe> p = recipeRepo.findAll(new PageRequest(i, pageSize));
            for (Recipe r : p) {
                List<Ingredient> ingredients = null;
                if (!append)
                    ingredients = new ArrayList<>();
                else
                    ingredients = r.getIngredients();

                for (IngredientWithAmount ingr : r.getIngredientsWithAmount()) {
                    boolean matched = false;
                    for (int ng = 1; ng < 4; ng++) {
                        NgramIter ngramIter = new NgramIter(ng, ingr.getRawIngredient().replaceAll("[^a-zA-Z ]", "").toLowerCase());
                        while (ngramIter.hasNext()) {
                            // Test if ingredient exists in repo.
                            String nGram = ngramIter.next();
                            if (masterIngredients.containsKey(nGram)) {
                                ingredients.add(masterIngredients.get(nGram));
                                matched = true;
                            }
                        }
                    }
                    if (!matched)
                        unrecognisedIngredientRepo.save(new UnrecognisedIngredient(ingr));
                }
                r.setIngredients(ingredients);
                recipeRepo.save(r);
            }
        }
    }
}
