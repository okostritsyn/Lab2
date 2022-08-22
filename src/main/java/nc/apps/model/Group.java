package nc.apps.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Group implements Serializable {
    private Long id;
    private String name;
    private String specName;
    private Long specId;

    public boolean isNew() {
        return this.getId()==null || this.getId() == 0;
    }
}
