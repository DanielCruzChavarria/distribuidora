package com.almaximo.distribuidora.webController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeWebController {
    @GetMapping("/")
    public String redirectToProductos(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("flashMessage", "Bienvenido a la gestión de productos");
        return "redirect:/productos";
    }
}
