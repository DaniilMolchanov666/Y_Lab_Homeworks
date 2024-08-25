package com.ylab.repository;

import com.ylab.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для хранения заказов
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM car_shop_schema.order o JOIN o.car c WHERE c.model = :model AND c.color = :color")
    Order findOrderByCarModelAndColor(@Param("model") String model, @Param("brand") String brand);
}
