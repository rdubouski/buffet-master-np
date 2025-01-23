package by.tms.buffetmasternp.controller;

import by.tms.buffetmasternp.dto.ProfileDto;
import by.tms.buffetmasternp.service.ProfileService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public String profile(Model model, Authentication authentication) {
        ProfileDto profileDto = profileService.getProfile(authentication);
        model.addAttribute("profileDto", profileDto);
        return "profile/profile";
    }

    @PostMapping
    public String profileUpdate(@ModelAttribute ProfileDto profileDto, Authentication authentication, Model model) {
        ProfileDto profileDtoTmp = profileService.updateProfile(profileDto, authentication);
        model.addAttribute("profileDto", profileDtoTmp);
        return "redirect:/profile";
    }

}
