package com.ylab.entity.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ylab.entity.CarShopEntity;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@JsonSerialize
@JsonDeserialize
@ToString
public class CarDto implements CarShopEntity {

    private String brand;
    private String model;
    private String year;
    private String price;
    private String condition;
}
