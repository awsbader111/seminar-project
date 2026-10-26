package org.example.seminarproject.controller;

import org.example.seminarproject.entity.Category;
import org.example.seminarproject.repository.CategoryRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CategoryRestController {
    private final CategoryRepository repo;

    public CategoryRestController(CategoryRepository repo) {
        this.repo = repo;
    }

    // Read all categories
    @GetMapping("/api/categories")
    public Iterable<Category> readAll() {
        return repo.findAll();
    }

    // Read one category
    @GetMapping("/api/categories/{name}")
    public Category readOne(@PathVariable("name") String name) {
        return repo.findById(name)
                .orElseThrow(() -> new CategoryNotFoundException(name));
    }

    // Create a category
    @PostMapping("/api/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public Category categorySave(@RequestBody Category category) {
        String name = category.getCname();

        if (name == null || name.isBlank() || name.trim().length() > 50) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Enter a category name of 1–50 characters.");
        }

        category.setCname(name.trim());
        checkPrice(category);

        if (repo.existsById(category.getCname())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Category already exists.");
        }

        return repo.save(category);
    }

    // Update a category, or create it if missing
    @PutMapping("/api/categories/{name}")
    public Category categoryUpdate(
            @RequestBody Category dataCategory,
            @PathVariable("name") String name) {

        if (name.isBlank() || name.length() > 50) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid category name.");
        }

        checkPrice(dataCategory);

        return repo.findById(name)
                .map(category -> {
                    category.setPrice(dataCategory.getPrice());
                    return repo.save(category);
                })
                .orElseGet(() -> {
                    dataCategory.setCname(name);
                    return repo.save(dataCategory);
                });
    }

    // Delete a category if no pizzas reference it
    @DeleteMapping("/api/categories/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("name") String name) {
        Category category = repo.findById(name)
                .orElseThrow(() -> new CategoryNotFoundException(name));

        try {
            repo.delete(category);
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Cannot delete a category used by pizzas.");
        }
    }

    // Check the price
    private void checkPrice(Category category) {
        if (category.getPrice() == null || category.getPrice() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Price must be greater than zero.");
        }
    }
}
