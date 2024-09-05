package com.ylab.mapper;

import com.ylab.entity.User;
import com.ylab.entity.dto.UserForShowDto;
import com.ylab.entity.dto.UserDto;
import com.ylab.entity.dto.UserUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Маппер для перевода сущностей, связанных с пользователями
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    UserMapper carMapper = Mappers.getMapper(UserMapper.class);

    User toUSer(UserDto userDto);

    User toUSer(UserForShowDto userDto);

    UserDto toUserDto(User user);

    UserForShowDto toUserForShowDto(User user);

    void updateOwnProfile(UserUpdateDto userDto, @MappingTarget User user);

    void updateRole(UserForShowDto userDto, @MappingTarget User user);
}
