package ok.apps.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class Student implements Serializable {
    private Long id;
    private String name;
    private int age;
    private long groupid;
    private String group;
    private double avgMark;

    public boolean isNew() {
        return this.getId()==null || this.getId() == 0;
    }


}
