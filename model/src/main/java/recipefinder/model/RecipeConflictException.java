package recipefinder.model;

/**
 * Created by aludlow on 6/18/17.
 */
public class RecipeConflictException extends Exception {
    public RecipeConflictException(String errorMsg) {
        super(errorMsg);
    }

    public RecipeConflictException() {
        super();
    }
}
