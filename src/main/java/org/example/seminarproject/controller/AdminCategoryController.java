
package org.example.seminarproject.controller;

import org.example.seminarproject.entity.Category;
import org.example.seminarproject.repository.CategoryRepository;
import org.example.seminarproject.repository.PizzaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryRepository categoryRepository;
    private final PizzaRepository pizzaRepository;

    public AdminCategoryController(
            CategoryRepository categoryRepository,
            PizzaRepository pizzaRepository) {
        this.categoryRepository = categoryRepository;
        this.pizzaRepository = pizzaRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin-categories";
    }

    @PostMapping("/add")
    public String add(@RequestParam String cname,
                      @RequestParam Integer price,
                      Model model) {

        cname = cname.trim();

        if (cname.isEmpty() || cname.length() > 50 || price < 0) {
            model.addAttribute("error", "Invalid category name or price.");
            return list(model);
        }

        if (categoryRepository.existsById(cname)) {
            model.addAttribute("error", "Category already exists!");
            return list(model);
        }

        categoryRepository.save(new Category(cname, price));
        return "redirect:/admin/categories";
    }

    @PostMapping("/update")
    public String update(@RequestParam String cname,
                         @RequestParam Integer price,
                         Model model) {

        if (price < 0) {
            model.addAttribute("error", "Price cannot be negative.");
            return list(model);
        }

        Category category = categoryRepository.findById(cname).orElse(null);

        if (category == null) {
            model.addAttribute("error", "Category not found.");
            return list(model);
        }

        category.setPrice(price);
        categoryRepository.save(category);

        return "redirect:/admin/categories";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam String cname, Model model) {

        if (!categoryRepository.existsById(cname)) {
            model.addAttribute("error", "Category not found.");
            return list(model);
        }

        boolean used = pizzaRepository.findAll().stream()
                .anyMatch(p -> p.getCategory().getCname().equals(cname));

        if (used) {
            model.addAttribute("error",
                    "Cannot delete this category because pizzas use it.");
            return list(model);
        }

        categoryRepository.deleteById(cname);

        return "redirect:/admin/categories";
    }
}
