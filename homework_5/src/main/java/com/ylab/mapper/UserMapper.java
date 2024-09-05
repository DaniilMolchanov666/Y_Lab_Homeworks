package com.ylab.mapper;

import com.ylab.entity.CarShopUser;
import com.ylab.entity.dto.user.UserForRegisterDto;
import com.ylab.entity.dto.user.UserForShowAndUpdateRoleDto;
import com.ylab.entity.dto.user.UserUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * Маппер для перевода сущностей, связанных с пользователями
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    /**
     * Маппинг из UserForRegisterDto в CarShopUser
     * @param userDto - обьект типа UserForRegisterDto
     * @return обьект типа CarShopUser
     */
    CarShopUser toUser(UserForRegisterDto userDto);

    /**
     * Маппинг из обновления CarShopUser
     * @param userDto - обьект типа UserUpdateDto, для обновления
     * @param user - обьект CarShopUser, который обновляется
     */
    void updateOwnProfile(UserUpdateDto userDto, @MappingTarget CarShopUser user);

    /**
     * Маппинг из обновления роли CarShopUser
     * @param userForShowDto - обьект типа UserForShowAndUpdateRoleDto, для обновления
     * @param user - обьект CarShopUser, который обновляется
     */
    void updateRole(UserForShowAndUpdateRoleDto userForShowDto, @MappingTarget CarShopUser user);

    /**
     * Маппинг из CarShopUser в UserForShowAndUpdateRoleDto
     * @param user - обьект типа CarShopUser
     * @return обьект типа UserForShowAndUpdateRoleDto
     */
    UserForShowAndUpdateRoleDto toUserForShowDto(CarShopUser user);
}
