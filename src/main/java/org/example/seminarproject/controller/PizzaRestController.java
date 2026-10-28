
package org.example.seminarproject.controller;

import org.example.seminarproject.entity.Pizza;
import org.example.seminarproject.repository.PizzaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PizzaRestController {

    private final PizzaRepository pizzaRepository;

    public PizzaRestController(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @GetMapping("/api/pizzas")
    public List<Pizza> getPizzas() {
        return pizzaRepository.findAll();
    }
}
