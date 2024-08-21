package com.ylab.mapper;

import com.ylab.entity.Car;
import com.ylab.entity.dto.CarDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Маппер для перевода сущностей, связанных с автомобилями
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface CarMapper {

    CarMapper carMapper = Mappers.getMapper(CarMapper.class);

    Car toCar(CarDto carDto);

    CarDto toCarDto(Car car);

    void update(CarDto carDto, @MappingTarget Car car);
}
