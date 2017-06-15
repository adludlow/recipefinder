package recipefinder.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

/**
 * Created by aludlow on 2/19/17.
 */

@Entity(name="RECIPE")
public class Recipe extends Timestamped {
    @Id
    @Column(name="ID")
    @GeneratedValue(generator="recipe_id_seq")
    @SequenceGenerator(name="recipe_id_seq", sequenceName = "RECIPE_ID_SEQ", allocationSize = 1)
    private long id;

    @Column(name="NAME")
    private String name;
    @OneToMany(cascade = ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="RECIPE_ID")
    private List<MethodStep> steps;
    @Column(name="URL")
    private String url;
    @OneToMany(cascade = ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="RECIPE_ID", nullable = false)
    private List<IngredientWithAmount> ingredientsWithAmount;
    @ManyToMany(cascade = ALL)
    @JoinTable(name="RECIPE_INGREDIENTS",
               joinColumns = @JoinColumn(name = "RECIPE_ID", referencedColumnName = "ID"),
               inverseJoinColumns = @JoinColumn(name = "INGREDIENT_ID", referencedColumnName = "ID"))
    private List<Ingredient> ingredients;

    protected Recipe() {}

    public Recipe(String name, List<MethodStep> steps, List<IngredientWithAmount> ingredients, String url) {
        setName(name);
        setUrl(url);
        setSteps(steps);
        setIngredientsWithAmount(ingredients);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<IngredientWithAmount> getIngredientsWithAmount() {
        return ingredientsWithAmount;
    }

    public void setIngredientsWithAmount(List<IngredientWithAmount> ingredients) {
        this.ingredientsWithAmount = ingredients;
    }

    public List<MethodStep> getSteps() {
        return steps;
    }

    public void setSteps(List<MethodStep> steps) {
        this.steps = steps;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<String> getIngredientsAsSet(){
        Set<String> ingredients = new HashSet<String>();
        for(IngredientWithAmount i: getIngredientsWithAmount()) {
            ingredients.add(i.getRawIngredient());
        }
        return ingredients;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", steps=" + steps +
                ", url='" + url + '\'' +
                ", ingredientsWithAmount=" + ingredientsWithAmount.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        if (getName() != null ? !getName().equals(recipe.getName()) : recipe.getName() != null) return false;
        if (getSteps() != null ? !getSteps().equals(recipe.getSteps()) : recipe.getSteps() != null) return false;
        if (getUrl() != null ? !getUrl().equals(recipe.getUrl()) : recipe.getUrl() != null) return false;
        if (getIngredientsWithAmount() != null ? !getIngredientsWithAmount().equals(recipe.getIngredientsWithAmount()) : recipe.getIngredientsWithAmount() != null)
            return false;
        return getIngredients() != null ? getIngredients().equals(recipe.getIngredients()) : recipe.getIngredients() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getSteps() != null ? getSteps().hashCode() : 0);
        result = 31 * result + (getUrl() != null ? getUrl().hashCode() : 0);
        result = 31 * result + (getIngredientsWithAmount() != null ? getIngredientsWithAmount().hashCode() : 0);
        result = 31 * result + (getIngredients() != null ? getIngredients().hashCode() : 0);
        return result;
    }
}
