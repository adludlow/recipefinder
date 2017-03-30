package recipefinder.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by aludlow on 3/3/17.
 */
@MappedSuperclass
public abstract class Timestamped {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_TIMESTAMP", nullable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_TIMESTAMP", nullable = false)
    private Date updated;

    @PrePersist
    protected void onCreate() {
        updated = created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }
}
