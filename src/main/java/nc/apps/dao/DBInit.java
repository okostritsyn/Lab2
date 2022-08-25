package nc.apps.dao;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Component
@Log4j
public class DBInit {
    JdbcTemplate jdbcTemplate;

    private final String SQL_INIT_SUBJECTS = "CREATE TABLE IF NOT EXISTS subjects "+
           " (id SERIAL PRIMARY KEY, " +
            " name VARCHAR(50) NOT NULL);";

    private final String SQL_INIT_SPECS = "CREATE TABLE IF NOT EXISTS specializations" +
            "(id SERIAL PRIMARY KEY," +
            "name VARCHAR(50) NOT NULL," +
            "parentid INTEGER REFERENCES specializations);";

    private final String SQL_INIT_GROUPS = "CREATE TABLE IF NOT EXISTS groups"+
            "(id SERIAL PRIMARY KEY,"+
            "name VARCHAR(50) NOT NULL,"+
            "specid INTEGER REFERENCES specializations);";

    private final String SQL_INIT_STUDENTS = "CREATE TABLE IF NOT EXISTS students"+
            "(id SERIAL PRIMARY KEY,"+
            " name VARCHAR(10) NOT NULL,"+
            "age NUMERIC(4) NOT NULL,"+
            "groupid INTEGER REFERENCES groups);";

    private final String SQL_INIT_MARKS = "CREATE TABLE IF NOT EXISTS marks"+
            " (id SERIAL PRIMARY KEY,"+
            " subjectid INTEGER REFERENCES subjects,"+
            " mark NUMERIC(4) NOT NULL,"+
            " studentid INTEGER REFERENCES students);";

    @Autowired
    public DBInit(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @PostConstruct
    void postConstruct(){
        log.info(SQL_INIT_SUBJECTS);
        jdbcTemplate.update(SQL_INIT_SUBJECTS);
        log.info(SQL_INIT_SPECS);
        jdbcTemplate.update(SQL_INIT_SPECS);
        log.info(SQL_INIT_GROUPS);
        jdbcTemplate.update(SQL_INIT_GROUPS);
        log.info(SQL_INIT_STUDENTS);
        jdbcTemplate.update(SQL_INIT_STUDENTS);
        log.info(SQL_INIT_MARKS);
        jdbcTemplate.update(SQL_INIT_MARKS);
    }
}
