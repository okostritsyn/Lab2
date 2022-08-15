package nc.apps.service;

import nc.apps.dao.SubjectDAO;
import nc.apps.model.Subject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectDAO subjectDAO;

    public SubjectService(SubjectDAO subjectDAO) {
        this.subjectDAO = subjectDAO;
    }

    public void save(Subject subject) {
        if (subject.isNew()) {
            subjectDAO.create(subject);
        } else {
            subjectDAO.update(subject);
        }
    }

    public List<Subject> listAll() {
        return subjectDAO.findAll();
    }

    public Subject get(Long id) {
        return subjectDAO.findById(id);
    }

    public void delete(Long id) {
        subjectDAO.delete(subjectDAO.findById(id));
    }

    public List<Subject> search(String keyword) {
        return subjectDAO.findByName(keyword);
    }
}
