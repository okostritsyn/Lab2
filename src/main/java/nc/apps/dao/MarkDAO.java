package nc.apps.dao;

import lombok.extern.log4j.Log4j;
import nc.apps.mapper.GroupMapper;
import nc.apps.mapper.MarkMapper;
import nc.apps.model.Group;
import nc.apps.model.Mark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Log4j
public class MarkDAO {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public MarkDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final String SQL_FIND = "select ident from marks where marks.id = ?";
    private final String SQL_DELETE = "delete from marks where id = ?";
    private final String SQL_UPDATE = "update marks set studentid = ?,subjectid = ?,mark = ? where id = ?";
    private final String SQL_FIND_STUDENT = "select ident from marks where marks.studentid = ?";
    private final String SQL_INSERT = "insert into marks(studentid,subjectid,mark) values(?,?,?)";

    private final String SQL_JOIN = "marks.*, " +
            "subjects.name as subject, " +
            "students.name as student " +
            " from marks" +
            "    left join" +
            "    subjects on marks.subjectid = subjects.id "+
            "    left join " +
            "    students on marks.studentid = students.id ";


    public boolean update(Mark mark) {
        log.info(SQL_UPDATE);
        return jdbcTemplate.update(SQL_UPDATE, mark.getStudentId(),mark.getSubjectId(),mark.getMark(),
                mark.getId()) > 0;
    }

    public boolean create(Mark mark) {
        log.info(SQL_INSERT);
       return jdbcTemplate.update(SQL_INSERT, mark.getStudentId(),mark.getSubjectId(),mark.getMark()) > 0;
    }

    public List<Mark> findByStudent(long studentId) {
        log.info(SQL_FIND_STUDENT.replaceAll("ident from marks",SQL_JOIN));
        return jdbcTemplate.query(SQL_FIND_STUDENT.replaceAll("ident from marks",SQL_JOIN), new MarkMapper(), studentId);
    }

    public Mark findById(long id) {
        log.info(SQL_FIND.replaceAll("ident from marks",SQL_JOIN));
        try {
            return jdbcTemplate.queryForObject(SQL_FIND.replaceAll("ident from marks", SQL_JOIN), new MarkMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public boolean delete(Mark mark) {
        log.info(SQL_DELETE);
        return jdbcTemplate.update(SQL_DELETE, mark.getId()) > 0;
    }

    public boolean canBeDeleted(long id) {
        return true;
    }

}
