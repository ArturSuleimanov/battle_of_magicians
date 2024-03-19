package com.interview.magicians.service;


import com.interview.magicians.entity.User;
import com.interview.magicians.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.interview.magicians.handlers.WebSocketEventHandler.participiants;
import static com.interview.magicians.service.UserService.USER_NOT_FOUND;

@Service
@AllArgsConstructor
public class PunchService {

    private final UserRepository userRepository;

    private final SimpMessagingTemplate template;


    public void punch(String name) {
        if (participiants.contains(name)) {
            User user = userRepository.findUserByUsername(name).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
            if (user.getHealth() == 10) {
                user.setHealth(100);
            } else {
                user.setHealth(user.getHealth() - 10);
            }
            userRepository.save(user);
            template.convertAndSendToUser(name, "/queue/punch", user.getHealth());
        }
    }
}
