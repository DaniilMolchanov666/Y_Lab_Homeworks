package com.ylab.entity.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@JsonSerialize
@JsonDeserialize
@ToString
public class CarForEditDto {
    private String brand;
    private String model;
}
