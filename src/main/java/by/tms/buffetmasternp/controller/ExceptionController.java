package by.tms.buffetmasternp.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFoundException(EntityNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

}
