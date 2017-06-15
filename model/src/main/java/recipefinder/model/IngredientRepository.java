package recipefinder.model;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by aludlow on 6/8/17.
 */
public interface IngredientRepository extends PagingAndSortingRepository<Ingredient, Long> {
    List<Ingredient> findByIngredient(String ingredient);
}
