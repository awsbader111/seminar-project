package org.example.seminarproject.controller;

import org.example.seminarproject.repository.PizzaOrderRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DatabaseController {
    private final PizzaOrderRepository pizzaOrderRepository;

    public DatabaseController(PizzaOrderRepository pizzaOrderRepository) {
        this.pizzaOrderRepository = pizzaOrderRepository;
    }

    @GetMapping("/database")
    public String database(Model model) {
        model.addAttribute("orders",
                pizzaOrderRepository.findAll(
                        PageRequest.of(0, 50, Sort.by("id"))
                ).getContent());

        return "database";
    }
}