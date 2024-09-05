package com.ylab.repository;

import com.ylab.entity.Car;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для хранения автомобилей
 */
@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {

    /**
     * Поиск автомобиля по модели и брэнду
     * @param brand - брэнд
     * @param model - модель
     * @return автомобиль в виде Optional<Car>
     */
    Optional<Car> findByModelAndBrand(String model, String brand);

    /**
     * Поиск всех автомобилей
     * @param pageable - объект Pagable для задания параметров выборки значений
     * @return список автомобилей
     */
    List<Car> findAll(Pageable pageable);

    /**
     * Поиск автомобиля по модели
     * @param model - модель
     * @return автомобиль в виде Optional<Car>
     */
    Optional<Car> findByModel(String model);

    /**
     * Проверка наличия автомобиля по брэнду и модели
     * @param brand - брэнд
     * @param model - модель
     * @return true или false
     */
    boolean existsByBrandAndModel(String brand, String model);
}
