package by.tms.buffetmasternp.service;

import by.tms.buffetmasternp.entity.GroupBox;
import by.tms.buffetmasternp.repository.GroupBoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupBoxService {

    @Autowired
    private GroupBoxRepository groupRepository;

    public List<GroupBox> getAllGroups() {
        return groupRepository.findAll();
    }

    public GroupBox createGroup(String groupName) {
        GroupBox group = new GroupBox();
        group.setName(groupName);
        return groupRepository.save(group);
    }

    public GroupBox getGroupById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    public GroupBox updateGroup(GroupBox group) {
        return groupRepository.save(group);
    }
}
