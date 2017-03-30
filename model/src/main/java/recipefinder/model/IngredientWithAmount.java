package recipefinder.model;

import javax.persistence.*;

/**
 * Created by aludlow on 3/2/17.
 */
@Entity(name="INGREDIENT_WITH_AMOUNT")
public class IngredientWithAmount extends Timestamped {
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column(name="INGREDIENT")
    private String rawIngredient;
    @Column(name="AMOUNT")
    private int amount;
    @Column(name="UOM")
    private String uom;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="INGREDIENT_ID", nullable = true)
    private Ingredient ingredient;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRawIngredient() {
        return rawIngredient;
    }

    public void setRawIngredient(String ingredient) {
        this.rawIngredient = ingredient;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }
}
