package com.example.week4_hw.service;

import com.example.week4_hw.controller.dto.UserRequest;
import com.example.week4_hw.controller.dto.UserResponse;
import com.example.week4_hw.repository.UserRepository;
import com.example.week4_hw.repository.entity.User;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Create
    public UserResponse createUser(UserRequest userRequest) {
        User user = User.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword()) // 실제 프로젝트에서는 비밀번호 암호화 필요
                .email(userRequest.getEmail())
                .build();
        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    // Read
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(id + "를 찾을 수 가 없습니다."));
        return mapToResponse(user);
    }

    // Update
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(id + "를 찾을 수 가 없습니다."));
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword()); // 실제 프로젝트에서는 비밀번호 암호화 필요
        user.setEmail(userRequest.getEmail());
        User updatedUser = userRepository.save(user);
        return mapToResponse(updatedUser);
    }

    // Delete
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Get All Users
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Helper method to map User to UserResponse
    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}