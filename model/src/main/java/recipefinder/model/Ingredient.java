package recipefinder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by aludlow on 3/3/17.
 */
@Entity(name="INGREDIENT")
public class Ingredient extends Timestamped{
    public Ingredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public Ingredient(){}

    @Id
    @Column(name="ID")
    @GeneratedValue(generator="ingredient_id_seq")
    @SequenceGenerator(name="ingredient_id_seq", sequenceName = "ingredient_id_seq", allocationSize = 1)
    private long id;
    @Column(name="INGREDIENT")
    private String ingredient;
    @ManyToMany(mappedBy = "ingredients")
    @JsonIgnore
    private List<Recipe> recipes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
