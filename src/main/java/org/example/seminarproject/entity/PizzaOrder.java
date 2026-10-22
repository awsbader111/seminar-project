package org.example.seminarproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "`order`")
public class PizzaOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pizzaname", nullable = false)
    private Pizza pizza;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private LocalDateTime taken;

    @Column(nullable = false)
    private LocalDateTime dispatched;

    public PizzaOrder() {
    }

    public PizzaOrder(Pizza pizza, Integer amount,
                      LocalDateTime taken, LocalDateTime dispatched) {
        this.pizza = pizza;
        this.amount = amount;
        this.taken = taken;
        this.dispatched = dispatched;
    }

    public Long getId() {
        return id;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public LocalDateTime getTaken() {
        return taken;
    }

    public void setTaken(LocalDateTime taken) {
        this.taken = taken;
    }

    public LocalDateTime getDispatched() {
        return dispatched;
    }

    public void setDispatched(LocalDateTime dispatched) {
        this.dispatched = dispatched;
    }
}