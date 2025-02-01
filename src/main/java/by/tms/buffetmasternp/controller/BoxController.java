package by.tms.buffetmasternp.controller;

import by.tms.buffetmasternp.dto.BoxDto;
import by.tms.buffetmasternp.dto.BoxItemDto;
import by.tms.buffetmasternp.entity.Box;
import by.tms.buffetmasternp.entity.GroupBox;
import by.tms.buffetmasternp.entity.Product;
import by.tms.buffetmasternp.enums.Status;
import by.tms.buffetmasternp.enums.Type;
import by.tms.buffetmasternp.service.BoxService;
import by.tms.buffetmasternp.service.GroupBoxService;
import by.tms.buffetmasternp.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/box")
public class BoxController {

    private final BoxService boxService;
    private final ProductService productService;
    private final GroupBoxService groupBoxService;

    public BoxController(BoxService boxService, ProductService productService, GroupBoxService groupBoxService) {
        this.boxService = boxService;
        this.productService = productService;
        this.groupBoxService = groupBoxService;
    }

    @GetMapping("/admin/all")
    public String allBoxes(Model model) {
        model.addAttribute("boxDtos", boxService.getAllBoxesByStatusAndType(Status.OPEN, Type.DEFAULT));
        return "box/all";
    }

    @GetMapping("/admin/archive")
    public String archiveBoxes(Model model) {
        model.addAttribute("boxDtos", boxService.getAllBoxesByStatusAndType(Status.CLOSED, Type.DEFAULT));
        return "box/archive";
    }

    @PostMapping("/admin/close")
    public String closeBox(@RequestParam("id") Long boxId) {
        boxService.closeBox(boxId);
        return "redirect:/box/admin/all";
    }

    @GetMapping("/admin/add")
    public String addBox(Model model, HttpSession session) {
        BoxDto boxDto = new BoxDto();
        List<GroupBox> groupBoxes = groupBoxService.getAllGroups();
        List<BoxItemDto> boxItemDtoList;
        if (session.getAttribute("boxItemDtoList") == null) {
            boxItemDtoList = new ArrayList<>();
        } else {
            boxItemDtoList = (List<BoxItemDto>) session.getAttribute("boxItemDtoList");
        }
        List<Long> ids = new ArrayList<>();
        for (BoxItemDto boxItemDto : boxItemDtoList) {
            ids.add(boxItemDto.getProductId());
        }
        List<BoxItemDto> boxItemDtos = productService.getAllBoxItemsDto(ids);
        boxDto.setPrice(boxService.getPriceBox(boxItemDtoList));
        model.addAttribute("boxDto", boxDto);
        model.addAttribute("groupsBox", groupBoxes);
        model.addAttribute("productsAll", boxItemDtos);
        session.setAttribute("boxItemDtoList", boxItemDtoList);
        return "box/add";
    }

    @PostMapping("/admin/add")
    public String addBox(@ModelAttribute("boxDto") BoxDto boxDto, HttpSession session, Authentication authentication) {
        boxDto.setBoxItemDtos((List<BoxItemDto>) session.getAttribute("boxItemDtoList"));
        boxService.addBox(boxDto, authentication);
        session.setAttribute("boxItemDtoList", null);
        return "redirect:/box/admin/all";
    }

    @PostMapping("/admin/product-add")
    public String addProductBox(HttpSession session,
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

    @GetMapping("/admin/edit/{id}")
    public String editBox(@PathVariable("id") Long id, Model model) {
        Box box = boxService.getBoxById(id);
        model.addAttribute("box", box);
        return "box/edit";
    }

    @PostMapping("/admin/edit")
    public String editProduct(@ModelAttribute Box box) {
        boxService.editBox(box);
        return "redirect:/box/admin/all";
    }

    @GetMapping("/user/all")
    public String allBoxCatalog(Model model) {
        List<BoxDto> boxDtos = boxService.getAllBoxesByStatusAndType(Status.OPEN, Type.DEFAULT);
        model.addAttribute("boxDtos", boxDtos);
        return "box/user/all";
    }

    @GetMapping("/user/{id}")
    public String userBox(@PathVariable("id") Long id, Model model) {
        BoxDto boxDto = boxService.getBoxDtoById(id);
        model.addAttribute("boxDto", boxDto);
        return "box/user/box";
    }

    @PostMapping("/cart/add")
    public String addCart(@RequestParam("id") Long id, HttpSession session) {
        BoxDto boxDto = boxService.getBoxDtoById(id);
        List<BoxDto> boxDtos = (List<BoxDto>) session.getAttribute("cart");
        boxDtos.add(boxDto);
        session.setAttribute("cart", boxDtos);
        return "redirect:/box/user/all";
    }

    @GetMapping("/cart")
    public String cart(Model model, HttpSession session) {
        List<BoxDto> boxDtos = (List<BoxDto>) session.getAttribute("cart");
        model.addAttribute("priceCart", boxService.getPriceCart(boxDtos));
        return "box/user/cart";
    }

    @PostMapping("/cart/drop")
    public String dropCart(@RequestParam("id") Long id, HttpSession session) {
        List<BoxDto> boxDtos = (List<BoxDto>) session.getAttribute("cart");
        boxDtos.removeIf(boxDto -> boxDto.getId().equals(id));
        session.setAttribute("cart", boxDtos);
        return "redirect:/box/cart";
    }
}
