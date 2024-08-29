package com.ylab.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс-сущность представляет форму лога активности пользователя
 */
@Entity
@Table(name = "logs", schema = "car_shop_schema")
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize
public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String message;

    private String endPoint;

    private String createdAt;
}
