package lv.bootcamp.shelter.controller;

import jakarta.validation.Valid;
import lv.bootcamp.shelter.form.AnimalForm;
import lv.bootcamp.shelter.service.AnimalService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AnimalPageController {

    private final AnimalService animalService;
    private final Authentication authentication;

    public AnimalPageController(AnimalService animalService, Authentication authentication) {
        this.animalService = animalService;
        this.authentication = authentication;
    }

    @GetMapping("/")
    public String home(Model model) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        boolean isUser = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isUser", isUser);
        return "index";
    }

    @GetMapping("/animals")
    public String listAnimals(Model model) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        boolean isUser = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isUser", isUser);
        model.addAttribute("animals", animalService.findAll());
        return "animals";
    }

    @GetMapping("/animals/new")
    public String newAnimalForm(Model model) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            return "redirect:/animals";
        }
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute(
                "animalForm",
                new AnimalForm(null, null, null, null, null, null)
        );
        return "animals-new";
    }

    @PostMapping("/animals")
    public String createAnimal(
            @ModelAttribute("animalForm") @Valid AnimalForm form,
            BindingResult bindingResult,
            Model model) {


        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            return "redirect:/animals";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("isAdmin", isAdmin);
            return "animals-new";
        }

        animalService.createFromForm(form);
        return "redirect:/animals";
    }
}