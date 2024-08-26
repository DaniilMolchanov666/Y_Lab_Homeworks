package com.ylab.repository;

import com.ylab.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для хранения автомобилей
 */
@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {

    Optional<Car> findByModelAndBrand(String model, String brand);

    List<Car> findAll();
}
