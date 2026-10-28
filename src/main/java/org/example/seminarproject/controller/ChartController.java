
package org.example.seminarproject.controller;

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

    @GetMapping("/chart")
    public String showChart(Model model) {

        List<String> categories = new ArrayList<>();
        List<Long> quantities = new ArrayList<>();

        for (Object[] row : pizzaOrderRepository.getPizzaQuantityByCategory()) {
            categories.add((String) row[0]);
            quantities.add(((Number) row[1]).longValue());
        }

        model.addAttribute("categories", categories);
        model.addAttribute("quantities", quantities);

        return "chart";
    }
}
