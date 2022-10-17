package ok.apps.mapper;

import ok.apps.model.Mark;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MarkMapper implements RowMapper<Mark> {
    public Mark mapRow(ResultSet resultSet, int i) throws SQLException {
        Mark mark = new Mark();
        mark.setId(resultSet.getLong("id"));
        mark.setSubject(resultSet.getString("subject"));
        mark.setSubjectId(resultSet.getLong("subjectid"));
        mark.setMark(resultSet.getInt("mark"));
        mark.setStudentId(resultSet.getLong("studentid"));
        mark.setStudent(resultSet.getString("student"));
        return mark;
    }
}