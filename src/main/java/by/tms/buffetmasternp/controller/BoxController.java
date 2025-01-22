package by.tms.buffetmasternp.controller;

import by.tms.buffetmasternp.dto.BoxDto;
import by.tms.buffetmasternp.dto.BoxItemDto;
import by.tms.buffetmasternp.entity.GroupBox;
import by.tms.buffetmasternp.service.BoxService;
import by.tms.buffetmasternp.service.GroupBoxService;
import by.tms.buffetmasternp.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/box")
public class BoxController {

    @Autowired
    private BoxService boxService;
    @Autowired
    private ProductService productService;
    @Autowired
    private GroupBoxService groupBoxService;

    @GetMapping("/admin/add")
    public String addBox(Model model, HttpSession session) {
        BoxDto boxDto = new BoxDto();
        List<GroupBox> groupBoxes = groupBoxService.getAllGroups();
        List<BoxItemDto> boxItemDtos = productService.getAllBoxItemsDto();
        List<BoxItemDto> boxItemDtoList;
        if (session.getAttribute("boxItemDtoList") == null) {
            boxItemDtoList = new ArrayList<>();
        } else {
            boxItemDtoList = (List<BoxItemDto>) session.getAttribute("boxItemDtoList");
        }
        model.addAttribute("boxDto", boxDto);
        model.addAttribute("groupsBox", groupBoxes);
        model.addAttribute("productsAll", boxItemDtos);
        session.setAttribute("boxItemDtoList", boxItemDtoList);
        return "box/add";
    }

    @PostMapping("/admin/add")
    public String addBox(@ModelAttribute("boxDto") BoxDto boxDto, HttpSession session, Model model, Authentication authentication) {
        //boxService.addBox(boxDto, authentication);
        System.out.println(boxDto);
        //model.addAttribute("boxDto", boxDto);
        return "redirect:/box/admin/add";
    }

    @PostMapping("/admin/product-add")
    public String addProductBox(HttpSession session, Model model,
                                @RequestParam("productId") Long id,
                                @RequestParam("quantity") int quantity) {

        session.setAttribute("boxItemDtoList", boxService.getBoxItemDtoByBox((List<BoxItemDto>) session.getAttribute("boxItemDtoList"), id, quantity));
        return "redirect:/box/admin/add";
    }

    @PostMapping("/admin/product-drop")
    public String deleteProductBox(@RequestParam("boxItemDtoId") Long id, HttpSession session) {
        session.setAttribute("boxItemDtoList", boxService.deleteBoxItemDtoByBox((List<BoxItemDto>) session.getAttribute("boxItemDtoList"), id));
        return "redirect:/box/admin/add";
    }
}
