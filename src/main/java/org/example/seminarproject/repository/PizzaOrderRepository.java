
package org.example.seminarproject.repository;

import org.example.seminarproject.entity.PizzaOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PizzaOrderRepository extends JpaRepository<PizzaOrder, Long> {

    @Query("""
        SELECT o.pizza.category.cname, SUM(o.amount)
        FROM PizzaOrder o
        GROUP BY o.pizza.category.cname
        ORDER BY o.pizza.category.cname
        """)
    List<Object[]> getPizzaQuantityByCategory();
}
