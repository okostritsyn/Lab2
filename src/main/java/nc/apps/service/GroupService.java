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

    public void save(Group group) {
        if (group.isNew()) {
            groupDAO.create(group);
        } else {
            groupDAO.update(group);
        }
    }

    public List<Group> listAll() {
        return groupDAO.findAll();
    }

    public Group get(Long id) {
        return groupDAO.findById(id);
    }

    public void delete(Long id) {
        groupDAO.delete(groupDAO.findById(id));
    }

    public List<Group> search(String keyword) {
        return groupDAO.findByName(keyword);
    }
}
