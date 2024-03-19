package com.interview.magicians.service;

import com.interview.magicians.dto.RegistrationRequest;
import com.interview.magicians.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final UserService userService;

    public boolean register(RegistrationRequest registrationRequest) {
        try {
            userService.signUpUser(
                    User
                            .builder()
                            .username(registrationRequest.getUsername())
                            .health(100)
                            .password(registrationRequest.getPassword())
                            .build()
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
