package by.tms.buffetmasternp.controller;

import by.tms.buffetmasternp.entity.GroupBox;
import by.tms.buffetmasternp.service.GroupBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/group")
public class GroupBoxController {

    @Autowired
    private GroupBoxService groupBoxService;

    @GetMapping("/all")
    public String getAllIngredients(Model model) {
        model.addAttribute("groups", groupBoxService.getAllGroups());
        return "group/all";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("groupName") String groupName, Model model) {
        groupBoxService.createGroup(groupName);
        model.addAttribute("groups", groupBoxService.getAllGroups());
        return "redirect:/admin/group/all";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        GroupBox group = groupBoxService.getGroupById(id);
        model.addAttribute("group", group);
        return "group/edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("group") GroupBox group, Model model) {
        groupBoxService.updateGroup(group);
        model.addAttribute("groups", groupBoxService.getAllGroups());
        return "redirect:/admin/group/all";
    }

}
