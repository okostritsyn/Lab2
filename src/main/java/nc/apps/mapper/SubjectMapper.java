package nc.apps.mapper;

import nc.apps.model.Group;
import nc.apps.model.Subject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectMapper implements RowMapper<Subject> {
    public Subject mapRow(ResultSet resultSet, int i) throws SQLException {
        Subject subject = new Subject();
        subject.setId(resultSet.getLong("id"));
        subject.setName(resultSet.getString("name"));
        return subject;
    }
}