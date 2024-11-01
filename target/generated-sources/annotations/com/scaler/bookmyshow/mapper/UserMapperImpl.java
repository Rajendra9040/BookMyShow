package com.scaler.bookmyshow.mapper;

import com.scaler.bookmyshow.dto.SignUpRequest;
import com.scaler.bookmyshow.model.userAuth.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-01T12:04:37+0530",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public User mapUserFromSignUpRequest(SignUpRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.name( request.getName() );
        user.email( request.getEmail() );

        return user.build();
    }
}
