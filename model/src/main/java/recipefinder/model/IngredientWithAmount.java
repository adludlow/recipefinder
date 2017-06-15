package recipefinder.model;

import javax.persistence.*;

/**
 * Created by aludlow on 3/2/17.
 */
@Entity(name="INGREDIENT_WITH_AMOUNT")
public class IngredientWithAmount extends Timestamped {
    @Id
    @Column(name="ID")
    @GeneratedValue(generator="iwa_id_seq")
    @SequenceGenerator(name="iwa_id_seq", sequenceName = "IWA_ID_SEQ", allocationSize = 1)
    private long id;
    @Column(name="FULL_INGREDIENT_TEXT")
    private String fullIngredient;
    @Column(name="RAW_INGREDIENT")
    private String rawIngredient;
    @Column(name="AMOUNT")
    private String amount;
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

    public String getFullIngredient() {
        return fullIngredient;
    }

    public void setFullIngredient(String fullIngredient) {
        this.fullIngredient = fullIngredient;
    }

    public String getRawIngredient() {
        return rawIngredient;
    }

    public void setRawIngredient(String ingredient) {
        this.rawIngredient = ingredient;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
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
