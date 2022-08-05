package nc.apps.entity.student;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements RowMapper<Student> {

    public Student mapRow(ResultSet resultSet, int i) throws SQLException {

        Student student = new Student();
        student.setId(resultSet.getLong("stid"));
        student.setName(resultSet.getString("stname"));
        student.setGroupid(resultSet.getLong("groupid"));
        student.setAge(resultSet.getInt("stage"));
        return student;
    }
}