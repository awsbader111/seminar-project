package org.example.seminarproject.controller;

import org.example.seminarproject.entity.Category;
import org.example.seminarproject.entity.Pizza;
import org.example.seminarproject.repository.CategoryRepository;
import org.example.seminarproject.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminPizzaController {
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Display pizzas
    @GetMapping("/admin/pizzas")
    public String pizzas(Model model) {
        model.addAttribute("pizzas", pizzaRepository.findAll());
        return "admin-pizzas";
    }

    // Display the add form
    @GetMapping("/admin/pizzas/new")
    public String newPizza(Model model) {
        Pizza pizza = new Pizza();
        pizza.setVegetarian(false);
        model.addAttribute("pizza", pizza);
        model.addAttribute("categories", categoryRepository.findAll());
        return "newpizza";
    }

    // Save a new pizza
    @PostMapping("/admin/pizzas/save")
    public String savePizza(
            @ModelAttribute("pizza") Pizza pizza,
            @RequestParam("categoryName") String categoryName,
            Model model,
            RedirectAttributes redirectAttributes) {

        String name = pizza.getPname() == null
                ? "" : pizza.getPname().trim();
        pizza.setPname(name);

        Category category =
                categoryRepository.findById(categoryName).orElse(null);

        if (name.isBlank() || name.length() > 255) {
            model.addAttribute("error", "Enter a name of 1–255 characters.");
        } else if (pizzaRepository.existsById(name)) {
            model.addAttribute("error", "This pizza already exists.");
        } else if (category == null) {
            model.addAttribute("error", "Select a valid category.");
        }

        if (model.containsAttribute("error")) {
            model.addAttribute("categories", categoryRepository.findAll());
            return "newpizza";
        }

        pizza.setCategory(category);
        pizza.setVegetarian(Boolean.TRUE.equals(pizza.getVegetarian()));
        pizzaRepository.save(pizza);

        redirectAttributes.addFlashAttribute("success", "Pizza added.");
        return "redirect:/admin/pizzas";
    }

    // Display the update form
    @GetMapping("/admin/pizzas/edit")
    public String editPizza(
            @RequestParam("name") String name,
            Model model,
            RedirectAttributes redirectAttributes) {

        Pizza pizza = pizzaRepository.findById(name).orElse(null);

        if (pizza == null) {
            redirectAttributes.addFlashAttribute("error", "Pizza not found.");
            return "redirect:/admin/pizzas";
        }

        model.addAttribute("pizza", pizza);
        model.addAttribute("categories", categoryRepository.findAll());
        return "updatepizza";
    }

    // Update an existing pizza
    @PostMapping("/admin/pizzas/update")
    public String updatePizza(
            @ModelAttribute("pizza") Pizza pizza,
            @RequestParam("categoryName") String categoryName,
            RedirectAttributes redirectAttributes) {

        Pizza existing = pizza.getPname() == null
                ? null
                : pizzaRepository.findById(pizza.getPname()).orElse(null);

        Category category =
                categoryRepository.findById(categoryName).orElse(null);

        if (existing == null || category == null) {
            redirectAttributes.addFlashAttribute("error",
                    "Pizza or category not found.");
            return "redirect:/admin/pizzas";
        }

        existing.setCategory(category);
        existing.setVegetarian(Boolean.TRUE.equals(pizza.getVegetarian()));
        pizzaRepository.save(existing);

        redirectAttributes.addFlashAttribute("success", "Pizza updated.");
        return "redirect:/admin/pizzas";
    }

    // Delete a pizza if no other records reference it
    @PostMapping("/admin/pizzas/delete")
    public String deletePizza(
            @RequestParam("name") String name,
            RedirectAttributes redirectAttributes) {

        Pizza pizza = pizzaRepository.findById(name).orElse(null);

        if (pizza == null) {
            redirectAttributes.addFlashAttribute("error", "Pizza not found.");
            return "redirect:/admin/pizzas";
        }

        try {
            pizzaRepository.delete(pizza);
            redirectAttributes.addFlashAttribute("success", "Pizza deleted.");
        } catch (DataIntegrityViolationException exception) {
            redirectAttributes.addFlashAttribute("error",
                    "Cannot delete this pizza because other records reference it.");
        }

        return "redirect:/admin/pizzas";
    }
}