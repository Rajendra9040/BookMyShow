package com.scaler.bookmyshow.mapper;

import com.scaler.bookmyshow.dto.SignUpRequest;
import com.scaler.bookmyshow.model.userAuth.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-25T19:21:36+0530",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public User mapUserFromSignUpRequest(SignUpRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.hashedPassword( request.getPassword() );
        user.name( request.getName() );
        user.email( request.getEmail() );

        return user.build();
    }
}
