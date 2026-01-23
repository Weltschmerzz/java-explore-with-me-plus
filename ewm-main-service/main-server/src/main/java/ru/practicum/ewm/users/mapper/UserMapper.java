package ru.practicum.ewm.users.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.users.model.UserEntity;
import ru.practicum.ewm.users.dto.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(NewUserDto newUserDto);

    UserDto toUserDto(UserEntity userEntity);
}
