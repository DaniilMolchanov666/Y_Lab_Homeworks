package com.ylab.mapper;

import com.ylab.entity.Car;
import com.ylab.entity.dto.CarDto;
import com.ylab.entity.dto.CarFindDto;
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

    Car toCar(CarDto carDto);

    Car findCarDtoToCar(CarFindDto carFindDto);

    CarDto toCarDto(Car car);

    void update(CarDto carDto, @MappingTarget Car car);
}
