package com.example.daily0303.mission1.service;

import com.example.daily0303.mission1.domain.Role;
import com.example.daily0303.mission1.domain.User;
import com.example.daily0303.mission1.dto.SignupRequestDto;
import com.example.daily0303.mission1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }

        Role role = dto.isAdmin() ? Role.ROLE_ADMIN : Role.ROLE_USER;
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        User user = new User(dto.getUsername(), encodedPassword, role);
        userRepository.save(user);
    }
}
