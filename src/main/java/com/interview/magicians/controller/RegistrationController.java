package com.interview.magicians.controller;

import com.interview.magicians.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.interview.magicians.dto.RegistrationRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private RegistrationService registrationService;
    @PostMapping
    public boolean register(@RequestBody @Valid RegistrationRequest request) {
        return registrationService.register(request);
    }


}
