package org.example.seminarproject.controller;

import org.example.seminarproject.repository.CategoryOrderTotal;
import org.example.seminarproject.repository.PizzaOrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ChartController {
    private final PizzaOrderRepository pizzaOrderRepository;

    public ChartController(PizzaOrderRepository pizzaOrderRepository) {
        this.pizzaOrderRepository = pizzaOrderRepository;
    }

    // Display pizza quantities ordered by category
    @GetMapping("/chart")
    public String chart(Model model) {
        List<CategoryOrderTotal> totals =
                pizzaOrderRepository.findCategoryOrderTotals();
        List<String> categories = new ArrayList<>();
        List<Long> quantities = new ArrayList<>();

        for (CategoryOrderTotal total : totals) {
            categories.add(total.getCategoryName());
            quantities.add(total.getTotalQuantity());
        }

        model.addAttribute("categories", categories);
        model.addAttribute("quantities", quantities);
        return "chart";
    }
}