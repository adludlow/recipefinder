package recipefinder.model;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by aludlow on 2/19/17.
 */
public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long> {

}
