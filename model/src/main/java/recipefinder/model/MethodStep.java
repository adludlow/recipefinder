package recipefinder.model;

import javax.persistence.*;

/**
 * Created by aludlow on 3/4/17.
 */
@Entity(name="METHOD_STEP")
public class MethodStep {
    @Id
    @Column(name="ID")
    @GeneratedValue(generator="method_step_id_seq")
    @SequenceGenerator(name="method_step_id_seq", sequenceName = "METHOD_STEP_ID_SEQ", allocationSize = 1)
    private long id;
    @Column(name="STEP_TEXT", length = 1000)
    private String stepText;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStepText() {
        return stepText;
    }

    public void setStepText(String stepText) {
        this.stepText = stepText;
    }
}
