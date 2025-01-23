package by.tms.buffetmasternp.service;

import by.tms.buffetmasternp.entity.GroupBox;
import by.tms.buffetmasternp.repository.GroupBoxRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupBoxService {

    private final GroupBoxRepository groupRepository;

    public GroupBoxService(GroupBoxRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<GroupBox> getAllGroups() {
        return groupRepository.findAll();
    }

    public void createGroup(String groupName) {
        GroupBox group = new GroupBox();
        group.setName(groupName);
        groupRepository.save(group);
    }

    public GroupBox getGroupById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    public void updateGroup(GroupBox group) {
        groupRepository.save(group);
    }
}
