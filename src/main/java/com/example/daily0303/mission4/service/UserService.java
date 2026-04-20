package com.example.daily0303.mission4.service;

import com.example.daily0303.mission4.domain.User;
import com.example.daily0303.mission4.dto.UserRequestDto;
import com.example.daily0303.mission4.dto.UserResponseDto;
import com.example.daily0303.mission4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto create(UserRequestDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다: " + dto.getUsername());
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다: " + dto.getEmail());
        }
        User user = new User(dto.getUsername(), dto.getEmail(), dto.getPassword());
        return new UserResponseDto(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. id: " + id));
        return new UserResponseDto(user);
    }

    @Transactional
    public UserResponseDto update(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. id: " + id));
        user.update(dto.getEmail(), dto.getPassword());
        return new UserResponseDto(user);
    }

    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다. id: " + id);
        }
        userRepository.deleteById(id);
    }
}
