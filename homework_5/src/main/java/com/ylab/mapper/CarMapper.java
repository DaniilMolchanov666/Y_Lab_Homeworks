package com.ylab.mapper;

import com.ylab.entity.Car;
import com.ylab.entity.dto.car.request.CarRequestDto;
import com.ylab.entity.dto.car.response.CarFindDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
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

    /**
     * Маппинг из CarRequestDto в Car
     * @param carDto - обьект типа CarRequestDto
     * @return обьект типа Car
     */
    Car toCar(CarRequestDto carDto);

    /**
     * Маппинг из CarFindDto в Car
     * @param carFindDto - обьект типа CarFindDto
     * @return обьект типа Car
     */
    Car findCarDtoToCar(CarFindDto carFindDto);

    /**
     * Маппинг из Car в CarRequestDto
     * @param car - обьект типа Car
     * @return обьект типа CarRequestDto
     */
    CarRequestDto toCarRequestDto(Car car);
}
