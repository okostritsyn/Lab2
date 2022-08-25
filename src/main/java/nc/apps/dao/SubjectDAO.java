package nc.apps.dao;

import lombok.extern.log4j.Log4j;
import nc.apps.mapper.GroupMapper;
import nc.apps.mapper.SpecMapper;
import nc.apps.mapper.SubjectMapper;
import nc.apps.model.Group;
import nc.apps.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Log4j
public class SubjectDAO {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public SubjectDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final String SQL_FIND = "select * from subjects where id = ?";
    private final String SQL_DELETE = "delete from subjects where id = ?";
    private final String SQL_UPDATE = "update subjects set name = ? where id = ?";
    private final String SQL_GET_ALL = "select * from subjects";
    private final String SQL_INSERT = "insert into subjects(name) values(?)";
    private final String SQL_FIND_NAME = "select * from subjects where name LIKE '%' || ? || '%'";

    private final String SQL_CAN_DELETE = "select subjects.* from subjects " +
            " inner join marks on subjects.id = marks.subjectid " +
            " where subjects.id = ? ";

    public boolean update(Subject subject) {
        log.info(SQL_UPDATE);
        return jdbcTemplate.update(SQL_UPDATE, subject.getName(),
                subject.getId()) > 0;
    }

    public boolean create(Subject subject) {
        log.info(SQL_INSERT);
        return jdbcTemplate.update(SQL_INSERT, subject.getName()) > 0;    }

    public List<Subject> findAll() {
        log.info(SQL_GET_ALL);
        return jdbcTemplate.query(SQL_GET_ALL, new SubjectMapper());
    }

    public Subject findById(long id) {
        log.info(SQL_FIND);

        try {
            return jdbcTemplate.queryForObject(SQL_FIND, new SubjectMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public boolean delete(Subject subject) {
        log.info(SQL_DELETE);
        return jdbcTemplate.update(SQL_DELETE, subject.getId()) > 0;
    }

    public List<Subject> findByName(String name) {
        log.info(SQL_FIND_NAME);
        return jdbcTemplate.query(SQL_FIND_NAME, new SubjectMapper(),name);
    }

    public boolean canBeDeleted(long id) {
        log.info(SQL_CAN_DELETE);
        List<Subject> currList = jdbcTemplate.query(SQL_CAN_DELETE, new SubjectMapper(), id);
        return currList.size() == 0;
    }
}
