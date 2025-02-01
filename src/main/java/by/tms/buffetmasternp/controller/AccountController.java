package by.tms.buffetmasternp.controller;

import by.tms.buffetmasternp.dto.AccountLoginDto;
import by.tms.buffetmasternp.dto.AccountRegDto;
import by.tms.buffetmasternp.service.AccountService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/reg")
    public String reg(Model model) {
        model.addAttribute("accountRegDto", new AccountRegDto());
        return "account/reg";
    }

    @PostMapping("/reg")
    public String reg(@Valid @ModelAttribute AccountRegDto accountRegDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "account/reg";
        }
        if (accountService.checkUsernameExist(accountRegDto.getUsername())) {
            model.addAttribute("errorMessage", "Такой пользователь уже существует");
            return "account/reg";
        } else {
            accountService.create(accountRegDto);
            return "redirect:/";
        }
    }

    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        session.setAttribute("cart", new ArrayList<>());
        model.addAttribute("loginDto", new AccountLoginDto());
        return "account/login";
    }
}
