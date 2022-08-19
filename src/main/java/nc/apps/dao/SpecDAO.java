package nc.apps.dao;

import nc.apps.mapper.SpecMapper;
import nc.apps.model.Specialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class SpecDAO {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public SpecDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final String SQL_FIND = "select ident from specializations where specializations.id = ?";
    private final String SQL_DELETE = "delete from specializations where specializations.id = ?";
    private final String SQL_UPDATE = "update specializations set name = ?, parentid = ? where specializations.id = ?";
    private final String SQL_GET_ALL = "select ident from specializations";
    private final String SQL_INSERT = "insert into specializations(name,parentid) values(?,?)";
    private final String SQL_FIND_NAME = "select ident from specializations where specializations.name LIKE '%' || ? || '%'";

    private final String SQL_JOIN_GROUP = "specializations.*, " +
            "specparent.name as parent" +
            " from specializations\n" +
            "    left join\n" +
            "    specializations specparent on specializations.parentid = specparent.id\n";

    public List<Specialization> findAll() {
        return jdbcTemplate.query(SQL_GET_ALL.replaceAll("ident from specializations",SQL_JOIN_GROUP), new SpecMapper());
    }

    public boolean update(Specialization spec) {
        return jdbcTemplate.update(SQL_UPDATE, spec.getName(),spec.getParentId(),
                spec.getId()) > 0;
    }

    public boolean create(Specialization spec) {
        return jdbcTemplate.update(SQL_INSERT, spec.getName(),spec.getParentId()) > 0;    }


    public Specialization findById(long id) {
        return jdbcTemplate.queryForObject(SQL_FIND.replaceAll("ident from specializations",SQL_JOIN_GROUP),  new SpecMapper(), id);
    }

    public boolean delete(Specialization spec) {
        return jdbcTemplate.update(SQL_DELETE, spec.getId()) > 0;
    }

    public List<Specialization> findByName(String name) {
        return jdbcTemplate.query(SQL_FIND_NAME.replaceAll("ident from specializations",SQL_JOIN_GROUP), new SpecMapper(),name);
    }
}
