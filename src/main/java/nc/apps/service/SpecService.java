package nc.apps.service;

import nc.apps.dao.SpecDAO;
import nc.apps.model.Specialization;
import nc.apps.model.SpecializationNode;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class SpecService {

    private final SpecDAO specDAO;

    public SpecService(SpecDAO specDAO) {
        this.specDAO = specDAO;
    }

    public void save(Specialization spec) {
        if (spec.isNew()) {
            specDAO.create(spec);
        } else {
            specDAO.update(spec);
        }
    }

    public List<Specialization> listAll() {
        return specDAO.findAll();
    }

    public Specialization get(Long id) {
        return specDAO.findById(id);
    }

    public void delete(Long id) {
        specDAO.delete(specDAO.findById(id));
    }

    public List<Specialization> search(String keyword) {
        return specDAO.findByName(keyword);
    }

    public List<SpecializationNode> listAllAsTree() {
        return specDAO.findAllAsTree();
    }
}
