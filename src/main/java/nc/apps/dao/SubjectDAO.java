package nc.apps.dao;

import nc.apps.mapper.SubjectMapper;
import nc.apps.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
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

    public boolean update(Subject subject) {
        return jdbcTemplate.update(SQL_UPDATE, subject.getName(),
                subject.getId()) > 0;
    }

    public boolean create(Subject subject) {
        return jdbcTemplate.update(SQL_INSERT, subject.getName()) > 0;    }

    public List<Subject> findAll() {
        return jdbcTemplate.query(SQL_GET_ALL, new SubjectMapper());
    }

    public Subject findById(long id) {
        return jdbcTemplate.queryForObject(SQL_FIND,  new SubjectMapper(), id);
    }

    public boolean delete(Subject subject) {
        return jdbcTemplate.update(SQL_DELETE, subject.getId()) > 0;
    }

    public List<Subject> findByName(String name) {
        return jdbcTemplate.query(SQL_FIND_NAME, new SubjectMapper(),name);
    }

}
