package nc.apps.dao;

import lombok.extern.log4j.Log4j;
import nc.apps.errors.ResourceNotFoundException;
import nc.apps.mapper.SpecMapper;
import nc.apps.model.Specialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Log4j
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

    private final String SQL_CAN_DELETE = "select ident from specializations " +
            " inner join groups on specializations.id = groups.specid " +
            " where specializations.id = ? " +
            " union all " +
            " select ident from specializations " +
            " inner join specializations spec on specializations.id = spec.parentid " +
            " where specializations.id = ? ";

    private final String SQL_JOIN = "specializations.*, " +
            "specparent.name as parent " +
            " from specializations " +
            "    left join " +
            "    specializations specparent on specializations.parentid = specparent.id ";

    public List<Specialization> findAll() {
        log.info(SQL_GET_ALL.replaceAll("ident from specializations",SQL_JOIN));
        return jdbcTemplate.query(SQL_GET_ALL.replaceAll("ident from specializations",SQL_JOIN), new SpecMapper());
    }

    public boolean update(Specialization spec) {
        log.info(SQL_UPDATE);
        return jdbcTemplate.update(SQL_UPDATE, spec.getName(),spec.getParentId(),
                spec.getId()) > 0;
    }

    public boolean create(Specialization spec) {
        log.info(SQL_INSERT);
        return jdbcTemplate.update(SQL_INSERT, spec.getName(),spec.getParentId()) > 0;    }


    public Specialization findById(long id) {
        log.info(SQL_FIND.replaceAll("ident from specializations",SQL_JOIN));
        try {
            return jdbcTemplate.queryForObject(SQL_FIND.replaceAll("ident from specializations", SQL_JOIN), new SpecMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Mark with id " + id + " not found",e);
        }
    }

    public boolean delete(Specialization spec) {
        log.info(SQL_DELETE);
        return jdbcTemplate.update(SQL_DELETE, spec.getId()) > 0;
    }

    public List<Specialization> findByName(String name) {
        log.info(SQL_FIND_NAME.replaceAll("ident from specializations",SQL_JOIN));
        return jdbcTemplate.query(SQL_FIND_NAME.replaceAll("ident from specializations",SQL_JOIN), new SpecMapper(),name);
    }

    public boolean canBeDeleted(long id) {
        log.info(SQL_CAN_DELETE.replaceAll("ident from specializations",SQL_JOIN));
        List<Specialization> currList = jdbcTemplate.query(SQL_CAN_DELETE.replaceAll("ident from specializations",SQL_JOIN), new SpecMapper(), id, id);
        return currList.size() == 0;
    }
}
