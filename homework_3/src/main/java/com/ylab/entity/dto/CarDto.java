package com.ylab.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ylab.entity.CarShopEntity;
import io.jsonwebtoken.io.Deserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@Builder
@JsonSerialize
@JsonDeserialize
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
    @JsonProperty
    private String brand;
    @JsonProperty
    private String model;
    @JsonProperty
    private String year;
    @JsonProperty
    private String price;
    @JsonProperty
    private String condition;
}
