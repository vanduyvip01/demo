package com.example.demo.service.interfaceService;

import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;

import java.util.List;

public interface IUserService {

    List<UserResponse> getAllUsers();

    UserResponse createUser(User user);
}