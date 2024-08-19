package com.ylab.mapper;

import com.ylab.entity.Car;
import com.ylab.entity.dto.CarDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        uses = {Car.class, CarDto.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.JAKARTA,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class CarMapper {

    public abstract Car toCar(CarDto carDto);

    public abstract CarDto toCarDto(Car car);

    public abstract void update(CarDto carDto, @MappingTarget Car car);
}
