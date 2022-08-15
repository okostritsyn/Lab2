package nc.apps.service;

import nc.apps.model.Student;
import nc.apps.dao.StudentDAO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {

    private final StudentDAO studentDAO;

    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public void save(Student student) {
        if (student.isNew()) {
            studentDAO.create(student);
        } else {
            studentDAO.update(student);
        }
    }

    public List<Student> listAll() {
        return studentDAO.findAll();
    }

    public Student get(Long id) {
        return studentDAO.findById(id);
    }

    public void delete(Long id) {
        studentDAO.delete(studentDAO.findById(id));
    }

    public List<Student> search(String keyword) {
        return studentDAO.findByName(keyword);
    }
}
