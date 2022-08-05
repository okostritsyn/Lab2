package nc.apps.entity.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentDAO studentDAO;

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

        return null;
    }
}
