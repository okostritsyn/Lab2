package ok.apps.service;

import ok.apps.dao.SubjectDAO;
import ok.apps.model.Subject;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SubjectService {

    private final SubjectDAO subjectDAO;

    public SubjectService(SubjectDAO subjectDAO) {
        this.subjectDAO = subjectDAO;
    }

    public boolean save(Subject subject) {
        if (subject.isNew()) {
            return subjectDAO.create(subject);
        } else {
            return subjectDAO.update(subject);
        }
    }

    public List<Subject> listAll() {
        return subjectDAO.findAll();
    }

    public Subject get(Long id) {
        return subjectDAO.findById(id);
    }

    public boolean delete(Long id) {
        return subjectDAO.delete(subjectDAO.findById(id));
    }

    public List<Subject> search(String keyword) {
        return subjectDAO.findByName(keyword);
    }

    public boolean canBeDeleted(Long id) {
        return subjectDAO.canBeDeleted(id);
    }
}
