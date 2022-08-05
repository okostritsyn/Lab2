package nc.apps.entity.student;

import nc.apps.entity.group.Group;
import java.util.Objects;

public class Student {
    private Long id;
    private String name;
    private int age;
    private long groupid;
    private Group group;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isNew() {
        return this.getId()>0;
    }

    protected Student(String name, int age, long groupid) {
        this.name = name;
        this.age = age;
        this.groupid = groupid;
    }

    protected Student() {

    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", group='" + groupid + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id.equals(student.id) && age == student.age && Objects.equals(name, student.name) && Objects.equals(groupid, student.groupid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, groupid);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getGroupid() {
        return groupid;
    }

    public void setGroupid(long groupid) {
        this.groupid = groupid;
    }

}
