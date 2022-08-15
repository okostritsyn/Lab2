package nc.apps.model;

import java.io.Serializable;

public class Specialization implements Serializable {
    private Long id;
    private String name;
    private Long parentId;
    private String parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Specialization() {
    }

    public Specialization(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean isNew() {
        return this.getId()==null || this.getId() == 0;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParent() {
        return this.parent;
    }
}
