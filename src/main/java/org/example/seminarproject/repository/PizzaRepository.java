package org.example.seminarproject.repository;

import org.example.seminarproject.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, String> {
}