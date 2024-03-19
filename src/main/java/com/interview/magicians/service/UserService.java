package com.interview.magicians.service;

import com.interview.magicians.dto.UserDto;
import com.interview.magicians.entity.User;
import com.interview.magicians.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    public final static String USER_NOT_FOUND = "no such user %s";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).orElseThrow(()-> new UsernameNotFoundException(USER_NOT_FOUND));
    }

    public String signUpUser(User user) {
        boolean userExists = userRepository.findUserByUsername(user.getUsername()).isPresent();
        if (userExists) throw new IllegalStateException("username taken");
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return "success";
    }

    public UserDto getUser(String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow(()-> new UsernameNotFoundException(USER_NOT_FOUND));
        return UserDto.builder().username(user.getUsername()).health(user.getHealth()).build();
    }
}
