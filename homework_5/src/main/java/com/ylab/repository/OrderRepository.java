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

    /**
     * Поиск заказа по модели и брэнду автомобиля, который в него входит
     * @param brand - брэнд
     * @param model - модель
     * @return - заказ с найденным автомобилем
     */
    @Query("SELECT o FROM Order o JOIN o.car c " +
            "WHERE c.model = :model AND c.brand = :brand")
    Order findOrderByModelAndBrand(@Param("model") String model, @Param("brand") String brand);
}
