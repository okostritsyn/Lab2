package ok.apps.service;

import ok.apps.model.Student;
import ok.apps.dao.StudentDAO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {

    private final StudentDAO studentDAO;

    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public boolean save(Student student) {
        if (student.isNew()) {
            return studentDAO.create(student);
        } else {
            return studentDAO.update(student);
        }
    }

    public List<Student> listAll() {
        return studentDAO.findAll();
    }

    public Student get(Long id) {
        return studentDAO.findById(id);
    }

    public boolean delete(Long id) {
        return studentDAO.delete(studentDAO.findById(id));
    }

    public List<Student> search(String keyword) {
        return studentDAO.findByName(keyword);
    }

    public boolean canBeDeleted(Long id) {
        return studentDAO.canBeDeleted(id);
    }
}
