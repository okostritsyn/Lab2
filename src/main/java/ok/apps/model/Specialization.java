package ok.apps.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Specialization implements Serializable {
    private Long id;
    private String name;
    private Long parentId;
    private String parent;

    public boolean isNew() {
        return this.getId()==null || this.getId() == 0;
    }
}
