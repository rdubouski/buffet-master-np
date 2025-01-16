package by.tms.buffetmasternp.controller;

import by.tms.buffetmasternp.service.CloseDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/close-date")
public class CloseDateController {

    @Autowired
    private CloseDateService closeDateService;

    @GetMapping
    public String closeDatePage(Model model) {
        List<String> closeDates = closeDateService.getAllCloseDates();
        model.addAttribute("closeDates", closeDates);
        return "close-date/admin";
    }

    @PostMapping("/add")
    public String addCloseDate(@ModelAttribute("closeDate") String closeDate, Model model) {
        closeDateService.addCloseDate(closeDate);
        return "redirect:/admin/close-date";
    }
}
