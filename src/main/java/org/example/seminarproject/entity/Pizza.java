package org.example.seminarproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pizza")
public class Pizza {
    @Id
    @Column(name = "pname", length = 255)
    private String pname;

    @ManyToOne
    @JoinColumn(name = "categoryname", nullable = false)
    private Category category;

    @Column(nullable = false)
    private Boolean vegetarian;

    public Pizza() {
    }

    public Pizza(String pname, Category category, Boolean vegetarian) {
        this.pname = pname;
        this.category = category;
        this.vegetarian = vegetarian;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Boolean getVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(Boolean vegetarian) {
        this.vegetarian = vegetarian;
    }
}