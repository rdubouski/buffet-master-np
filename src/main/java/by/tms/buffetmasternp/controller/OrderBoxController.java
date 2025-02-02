package by.tms.buffetmasternp.controller;

import by.tms.buffetmasternp.dto.BoxDto;
import by.tms.buffetmasternp.dto.OrderDto;
import by.tms.buffetmasternp.entity.Account;
import by.tms.buffetmasternp.enums.StatusOrder;
import by.tms.buffetmasternp.service.BoxService;
import by.tms.buffetmasternp.service.CloseDateService;
import by.tms.buffetmasternp.service.OrderBoxService;
import by.tms.buffetmasternp.service.ProfileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderBoxController {

    private final OrderBoxService orderService;
    private final BoxService boxService;
    private final ProfileService profileService;
    private final CloseDateService closeDateService;

    public OrderBoxController(OrderBoxService orderService, BoxService boxService, ProfileService profileService, CloseDateService closeDateService) {
        this.orderService = orderService;
        this.boxService = boxService;
        this.profileService = profileService;
        this.closeDateService = closeDateService;
    }

    @GetMapping("/user/add")
    public String userOrder(Model model, HttpSession session, Authentication authentication) {
        List<String> closeDates = closeDateService.getAllCloseDates();
        model.addAttribute("closeDates", closeDates);
        List<BoxDto> boxDtos = (List<BoxDto>) session.getAttribute("cart");
        OrderDto orderDto = new OrderDto();
        orderDto.setPrice(boxService.getPriceCart(boxDtos));
        orderDto.setPhone(profileService.getProfile(authentication).getPhone());
        orderDto.setAddress(profileService.getProfile(authentication).getAddress());
        model.addAttribute("orderDto", orderDto);
        return "order/user/add";
    }

    @PostMapping("/user/add")
    public String addOrder(@ModelAttribute("orderDto") OrderDto orderDto, HttpSession session, Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        orderDto.setAccountId(account.getId());
        orderService.save(orderDto, (List<BoxDto>) session.getAttribute("cart"));
        session.setAttribute("cart", new ArrayList<>());
        return "redirect:/box/user/all";
    }

    @GetMapping("/user")
    public String userBox(Model model, Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        model.addAttribute("orderDtoOpen", orderService.findAllByStatusAndAccountId(StatusOrder.OPEN, account.getId()));
        model.addAttribute("orderDtoClose", orderService.findAllByStatusAndAccountId(StatusOrder.CLOSED, account.getId()));
        return "order/user/all";
    }

    @GetMapping("/admin")
    public String adminBox(Model model) {
        model.addAttribute("orderDtoOpen", orderService.findAllByStatus(StatusOrder.OPEN));
        model.addAttribute("orderDtoClose", orderService.findAllByStatus(StatusOrder.CLOSED));
        return "order/all";
    }

    @PostMapping("/admin/close")
    public String closeOrder(@RequestParam("id") Long id) {
        orderService.closeOrder(id);
        return "redirect:/order/admin";
    }

}
