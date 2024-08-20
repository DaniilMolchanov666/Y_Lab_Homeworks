package com.ylab.mapper;

import com.ylab.entity.Car;
import com.ylab.entity.dto.CarDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
      unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface CarMapper {

    public CarMapper carMapper = Mappers.getMapper(CarMapper.class);

    public abstract Car toCar(CarDto carDto);

    public abstract CarDto toCarDto(Car car);

    public abstract void update(CarDto carDto, @MappingTarget Car car);
}
