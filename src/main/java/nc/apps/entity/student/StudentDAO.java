package nc.apps.entity.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class StudentDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_STUDENT = "select * from students where stid = ?";
    private final String SQL_DELETE_STUDENT = "delete from students where stid = ?";
    private final String SQL_UPDATE_STUDENT = "update students set stname = ?, stage = ?, groupid  = ? where stid = ?";
    private final String SQL_GET_ALL = "select * from students";
    private final String SQL_INSERT_STUDENT = "insert into students(stid, stname, age, groupid) values(?,?,?,?)";

    public boolean update(Student student) {
        return jdbcTemplate.update(SQL_UPDATE_STUDENT, student.getName(), student.getAge(), student.getGroupid(),
                student.getId()) > 0;
    }

    public boolean create(Student student) {
        return jdbcTemplate.update(SQL_INSERT_STUDENT, student.getId(), student.getName(), student.getAge(),
                student.getGroupid()) > 0;    }

    public List<Student> findAll() {
        return jdbcTemplate.query(SQL_GET_ALL, new StudentMapper());
    }

    public Student findById(long id) {
        return jdbcTemplate.queryForObject(SQL_FIND_STUDENT,  new StudentMapper(), id);
    }

    public boolean delete(Student student) {
        return jdbcTemplate.update(SQL_DELETE_STUDENT, student.getId()) > 0;
    }
}
