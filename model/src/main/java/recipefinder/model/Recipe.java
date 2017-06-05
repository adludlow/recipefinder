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
    @GeneratedValue(strategy= GenerationType.AUTO)
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
}
