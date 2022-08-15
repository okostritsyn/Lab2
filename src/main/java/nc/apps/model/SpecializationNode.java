package nc.apps.model;

import java.io.Serializable;

public class SpecializationNode implements Serializable {
    private String id;
    private String text;
    private String parent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        if (parent.equals("0")){
            this.parent = "#";
        } else {
            this.parent = parent;
        };
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
