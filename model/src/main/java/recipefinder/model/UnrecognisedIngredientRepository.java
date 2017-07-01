package recipefinder.model;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by aludlow on 6/26/17.
 */
public interface UnrecognisedIngredientRepository extends PagingAndSortingRepository<UnrecognisedIngredient, Long> {
}
