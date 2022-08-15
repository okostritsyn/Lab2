package nc.apps.mapper;

import nc.apps.model.SpecializationNode;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpecNodeMapper implements RowMapper<SpecializationNode> {
    public SpecializationNode mapRow(ResultSet resultSet, int i) throws SQLException {
        SpecializationNode specializationNode = new SpecializationNode();
        specializationNode.setId(Long.toString(resultSet.getLong("id")));
        specializationNode.setText(resultSet.getString("name"));
        specializationNode.setParent(Long.toString(resultSet.getLong("parentid")));
        return specializationNode;
    }
}


