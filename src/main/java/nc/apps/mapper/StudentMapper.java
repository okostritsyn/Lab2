package nc.apps.mapper;

import nc.apps.model.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements RowMapper<Student> {

    public Student mapRow(ResultSet resultSet, int i) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getLong("id"));
        student.setName(resultSet.getString("name"));
        student.setGroupid(resultSet.getLong("groupid"));
        student.setGroup(resultSet.getString("groupname"));
        student.setAge(resultSet.getInt("age"));
        student.setAvgMark(resultSet.getDouble("avgmark"));
        return student;
    }
}