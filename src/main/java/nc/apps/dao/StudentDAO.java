package nc.apps.dao;

import nc.apps.mapper.StudentMapper;
import nc.apps.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class StudentDAO {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final String SQL_FIND_STUDENT_NAME = "select ident from students where students.name LIKE '%' || ? || '%' ORDER BY students.name";
    private final String SQL_FIND_STUDENT = "select ident from students where students.id = ?";
    private final String SQL_DELETE_STUDENT = "delete from students where id = ?";
    private final String SQL_UPDATE_STUDENT = "update students set name = ?, age = ?, groupid  = ? where id = ?";
    private final String SQL_GET_ALL = "select ident from students ORDER BY students.name";
    private final String SQL_INSERT_STUDENT = "insert into students(name, age, groupid) values(?,?,?)";

    private final String SQL_JOIN_GROUP = "students.*, " +
            "groups.name as groupname," +
            "round((SELECT AVG(mark) FROM marks AS marks \n" +
            "         WHERE marks.studentid=students.id),2) AS avgmark" +
            " from students\n" +
            "    left join\n" +
            "    groups on students.groupid = groups.id\n";

    public boolean update(Student student) {
        return jdbcTemplate.update(SQL_UPDATE_STUDENT, student.getName(), student.getAge(), student.getGroupid(),
                student.getId()) > 0;
    }

    public boolean create(Student student) {
        return jdbcTemplate.update(SQL_INSERT_STUDENT, student.getName(), student.getAge(),
                student.getGroupid()) > 0;
    }

    public List<Student> findAll() {
        return jdbcTemplate.query(SQL_GET_ALL.replaceAll("ident from students",SQL_JOIN_GROUP), new StudentMapper());
    }

    public Student findById(long id) {
        return jdbcTemplate.queryForObject(SQL_FIND_STUDENT.replaceAll("ident from students",SQL_JOIN_GROUP),  new StudentMapper(), id);
    }

    public boolean delete(Student student) {
        return jdbcTemplate.update(SQL_DELETE_STUDENT, student.getId()) > 0;
    }

    public List<Student> findByName(String name) {
        return jdbcTemplate.query(SQL_FIND_STUDENT_NAME.replaceAll("ident from students",SQL_JOIN_GROUP),  new StudentMapper(),name.trim());
    }
}
