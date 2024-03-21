package com.interview.magicians.controller;

import com.interview.magicians.dto.Punch;
import com.interview.magicians.service.PunchService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class PunchController {

    private final PunchService punchService;

    @MessageMapping("/punch")
    public void greeting(Punch message) {
        punchService.punch(message.getName());
    }

}