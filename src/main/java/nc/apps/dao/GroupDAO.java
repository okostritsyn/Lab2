package nc.apps.dao;

import lombok.extern.log4j.Log4j;
import nc.apps.errors.ResourceNotFoundException;
import nc.apps.mapper.GroupMapper;
import nc.apps.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Log4j
public class GroupDAO {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final String SQL_FIND_GROUP = "select ident from groups where groups.id = ?";
    private final String SQL_DELETE_GROUP = "delete from groups where id = ?";
    private final String SQL_UPDATE_GROUP = "update groups set name = ?, specid = ? where id = ?";
    private final String SQL_GET_ALL = "select ident from groups";
    private final String SQL_INSERT_GROUP = "insert into groups(name,specid) values(?,?)";
    private final String SQL_FIND_GROUP_NAME = "select ident from groups where groups.name LIKE '%' || ? || '%'";

    private final String SQL_CAN_DELETE = "select ident from groups " +
            " inner join students on groups.id = students.groupid" +
            " where groups.id =?";

    private final String SQL_JOIN_GROUP = "groups.*, " +
            "specializations.name as specname" +
            " from groups" +
            "    left join" +
            "    specializations on groups.specid = specializations.id ";

    public boolean update(Group group) {
        log.info(SQL_UPDATE_GROUP);
        return jdbcTemplate.update(SQL_UPDATE_GROUP, group.getName(),group.getSpecId(),
                group.getId()) > 0;
    }

    public boolean create(Group group) {
        log.info(SQL_INSERT_GROUP);
        return jdbcTemplate.update(SQL_INSERT_GROUP, group.getName(),group.getSpecId()) > 0;    }

    public List<Group> findAll() {
        log.info(SQL_GET_ALL.replaceAll("ident from groups",SQL_JOIN_GROUP));
        return jdbcTemplate.query(SQL_GET_ALL.replaceAll("ident from groups",SQL_JOIN_GROUP), new GroupMapper());
    }

    public Group findById(long id) {
        try {
            log.info(SQL_FIND_GROUP.replaceAll("ident from groups", SQL_JOIN_GROUP));
            return jdbcTemplate.queryForObject(SQL_FIND_GROUP.replaceAll("ident from groups", SQL_JOIN_GROUP), new GroupMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Group with id " + id + " not found",e);
        }
    }

    public boolean delete(Group group) {
        log.info(SQL_DELETE_GROUP);
        return jdbcTemplate.update(SQL_DELETE_GROUP, group.getId()) > 0;
    }

    public List<Group> findByName(String name) {
        log.info(SQL_FIND_GROUP_NAME.replaceAll("ident from groups",SQL_JOIN_GROUP));
        return jdbcTemplate.query(SQL_FIND_GROUP_NAME.replaceAll("ident from groups",SQL_JOIN_GROUP), new GroupMapper(),name);
    }

    public boolean canBeDeleted(long id) {
        log.info(SQL_CAN_DELETE.replaceAll("ident from groups",SQL_JOIN_GROUP));
        List<Group> currList = jdbcTemplate.query(SQL_CAN_DELETE.replaceAll("ident from groups",SQL_JOIN_GROUP), new GroupMapper(), id);
        return currList.size() == 0;
    }
}
