package org.example.seminarproject.repository;

import org.example.seminarproject.entity.PizzaOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PizzaOrderRepository extends JpaRepository<PizzaOrder, Long> {
    // Calculate the total quantity ordered for each category
    @Query("""
            SELECT o.pizza.category.cname AS categoryName,
                   SUM(o.amount) AS totalQuantity
            FROM PizzaOrder o
            GROUP BY o.pizza.category.cname
            ORDER BY o.pizza.category.cname
            """)
    List<CategoryOrderTotal> findCategoryOrderTotals();
}