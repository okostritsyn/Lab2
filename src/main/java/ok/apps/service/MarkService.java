package ok.apps.service;

import ok.apps.dao.MarkDAO;
import ok.apps.model.Mark;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkService {

    private final MarkDAO markDAO;

    public MarkService(MarkDAO markDAO) {
        this.markDAO = markDAO;
    }

    public boolean save(Mark mark) {
        if (mark.isNew()) {
           return markDAO.create(mark);
        } else {
            return markDAO.update(mark);
        }
    }

    public List<Mark> listByStudent(Long studentId) {
        return markDAO.findByStudent(studentId);
    }

    public Mark get(Long id) {
        return markDAO.findById(id);
    }

    public boolean delete(Long id) {
        return markDAO.delete(markDAO.findById(id));
    }
}
