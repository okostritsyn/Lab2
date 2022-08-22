package nc.apps.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class Subject implements Serializable {
    private Long id;
    private String name;

    public boolean isNew() {
        return this.getId()==null || this.getId() == 0;
    }
}
