package com.ylab.mapper;

import com.ylab.entity.Car;
import com.ylab.entity.dto.car.CarForCreateDto;
import com.ylab.entity.dto.car.CarFindDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * Маппер для перевода сущностей, связанных с автомобилями
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CarMapper {

    Car toCar(CarForCreateDto carDto);

    Car findCarDtoToCar(CarFindDto carFindDto);

    CarForCreateDto toCarDto(Car car);

    void update(CarForCreateDto carDto, @MappingTarget Car car);
}
