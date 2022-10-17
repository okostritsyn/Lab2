package ok.apps.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class Mark implements Serializable {
    private Long id;
    private String subject;
    private int mark;
    private Long subjectId;
    private Long studentId;
    private String student;

    public boolean isNew() {
        return this.getId()==null || this.getId() == 0;
    }
}
