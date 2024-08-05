package com.ylab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс представляет заявку на обслуживание автомобиля.
 */
@Data
@AllArgsConstructor
public class ServiceRequest {

    private User customer;
    private Car car;
    private String description;
    private String status;
}
