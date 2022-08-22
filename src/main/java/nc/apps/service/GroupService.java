package nc.apps.service;

import nc.apps.dao.GroupDAO;
import nc.apps.model.Group;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private final GroupDAO groupDAO;

    public GroupService(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public boolean save(Group group) {
        if (group.isNew()) {
            return groupDAO.create(group);
        } else {
            return groupDAO.update(group);
        }
    }

    public List<Group> listAll() {
        return groupDAO.findAll();
    }

    public Group get(Long id) {
        return groupDAO.findById(id);
    }

    public boolean delete(Long id) {
        return groupDAO.delete(groupDAO.findById(id));
    }

    public List<Group> search(String keyword) {
        return groupDAO.findByName(keyword);
    }

    public boolean canBeDeleted(long id) {
        return false;
    }
}
