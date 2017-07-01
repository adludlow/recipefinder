package recipefinder.model;

import javax.persistence.*;

/**
 * Created by aludlow on 6/26/17.
 */
@Entity(name="UNRECOGNISED_INGREDIENT")
public class UnrecognisedIngredient extends Timestamped {
   public UnrecognisedIngredient() {}

   public UnrecognisedIngredient(IngredientWithAmount iwa) {
      this.setUnrecognisedIngredient(iwa);
   }

   @Id
   @Column(name="ID")
   @GeneratedValue(generator="unrec_ingr_id_seq")
   @SequenceGenerator(name="unrec_ingr_id_seq", sequenceName = "unrec_ingr_id_seq", allocationSize = 1)
   private long id;

   @OneToOne(fetch = FetchType.LAZY)
   @JoinColumn(name="IWA_ID")
   private IngredientWithAmount unrecognisedIngredient;

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public IngredientWithAmount getUnrecognisedIngredient() {
      return unrecognisedIngredient;
   }

   public void setUnrecognisedIngredient(IngredientWithAmount unrecognisedIngredient) {
      this.unrecognisedIngredient = unrecognisedIngredient;
   }
}
