package nc.apps.model;

import java.io.Serializable;

public class Mark implements Serializable {
    private Long id;
    private String subject;
    private int mark;
    private Long subjectId;
    private Long studentId;
    private String student;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Mark() {
    }

    public Mark(Long id, String subject) {
        this.id = id;
        this.subject = subject;
    }

    public boolean isNew() {
        return this.getId()==null || this.getId() == 0;
    }
}
