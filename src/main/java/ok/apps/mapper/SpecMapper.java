package ok.apps.mapper;

import ok.apps.model.Specialization;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpecMapper implements RowMapper<Specialization> {
    public Specialization mapRow(ResultSet resultSet, int i) throws SQLException {
        Specialization specialization = new Specialization();
        specialization.setId(resultSet.getLong("id"));
        specialization.setName(resultSet.getString("name"));
        specialization.setParentId(resultSet.getLong("parentid"));
        specialization.setParent(resultSet.getString("parent"));
        return specialization;
    }
}