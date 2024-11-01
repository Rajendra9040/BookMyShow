package com.scaler.bookmyshow.mapper;

import com.scaler.bookmyshow.dto.SignUpRequest;
import com.scaler.bookmyshow.model.userAuth.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User mapUserFromSignUpRequest(SignUpRequest request);
}
