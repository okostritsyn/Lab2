package nc.apps.dao;

import nc.apps.mapper.MarkMapper;
import nc.apps.model.Mark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
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
            "subjects.name as subject," +
            "students.name as student" +
            " from marks\n" +
            "    left join\n" +
            "    subjects on marks.subjectid = subjects.id\n"+
            "    left join\n" +
            "    students on marks.studentid = students.id\n";


    public boolean update(Mark mark) {
        return jdbcTemplate.update(SQL_UPDATE, mark.getStudentId(),mark.getSubjectId(),mark.getMark(),
                mark.getId()) > 0;
    }

    public boolean create(Mark mark) {
       return jdbcTemplate.update(SQL_INSERT, mark.getStudentId(),mark.getSubjectId(),mark.getMark()) > 0;
    }

    public List<Mark> findByStudent(long studentId) {
        return jdbcTemplate.query(SQL_FIND_STUDENT.replaceAll("ident from marks",SQL_JOIN), new MarkMapper(), studentId);
    }

    public Mark findById(long id) {
        return jdbcTemplate.queryForObject(SQL_FIND.replaceAll("ident from marks",SQL_JOIN),  new MarkMapper(), id);
    }

    public boolean delete(Mark mark) {
        return jdbcTemplate.update(SQL_DELETE, mark.getId()) > 0;
    }

}
