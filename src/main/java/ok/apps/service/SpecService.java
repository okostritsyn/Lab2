package ok.apps.service;

import ok.apps.dao.SpecDAO;
import ok.apps.model.Specialization;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecService {

    private final SpecDAO specDAO;

    public SpecService(SpecDAO specDAO) {
        this.specDAO = specDAO;
    }

    public boolean save(Specialization spec) {
        if (spec.isNew()) {
            return specDAO.create(spec);
        } else {
            return specDAO.update(spec);
        }
    }

    public List<Specialization> listAll() {
        return specDAO.findAll();
    }

    public Specialization get(Long id) {
        return specDAO.findById(id);
    }

    public boolean delete(Long id) {
        return specDAO.delete(specDAO.findById(id));
    }

    public List<Specialization> search(String keyword) {
        return specDAO.findByName(keyword);
    }

    public boolean canBeDeleted(Long id) {
        return specDAO.canBeDeleted(id);
    }
}
