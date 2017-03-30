package recipefinder.model;

import javax.persistence.*;

/**
 * Created by aludlow on 3/3/17.
 */
@Entity(name="INGREDIENT")
public class Ingredient extends Timestamped{
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column(name="INGREDIENT")
    private String ingredient;
}
