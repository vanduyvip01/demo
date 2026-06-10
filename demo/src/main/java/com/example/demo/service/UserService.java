package com.example.demo.service;

import com.example.demo.ErrorCodeEnum.ErrorCode;
import com.example.demo.dto.requests.CreateUserRequest;
import com.example.demo.dto.requests.UpdateUserRequest;
import com.example.demo.entity.User;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import com.example.demo.dto.response.UserStatsResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
@Service

public class UserService {

    private final UserRepository userRepository;

    public void testLazy(){

        User user = userRepository.findAll().getFirst();
        System.out.println(user.getName());
        System.out.println(user.getRoles());
    }
    // Constructor Injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // ARRAY List
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // CREATE
    @Transactional
    public User createUser(CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setAge(request.getAge());
        user.setGender(request.getGender());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

    }
    @Transactional
    public void updateUser(
            Long id,
            UpdateUserRequest request
    ) {

        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));

        user.setName(request.getName());
        user.setAge(request.getAge());
        user.setGender(request.getGender());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        userRepository.save(user);
    }
    public Page<User> getUsers(
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);

    }
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.delete(user);
    }
    // STREAM API

    public Map<String,Object>
    getUserStats(){

        List<User> users = userRepository.findAll();
        Map<String,Object> result = new HashMap<>();
// total

        result.put("totalUsers", users.size());
// stream map

        result.put("userNames", users.stream().map(User::getName).toList());


// stream filter

        result.put("adultUsers", users.stream().filter(u-> u.getAge() >=18).toList());


// stream count

        long maleCount = users.stream().filter(u-> u.getGender() !=null).count();
        result.put("maleCount", maleCount);


// grouping

        List<UserStatsResponse> group = users.stream().collect(Collectors.groupingBy(u-> u.getGender().toString(), Collectors.counting()))
                .entrySet().stream().map(e-> new UserStatsResponse(e.getKey(), e.getValue())).toList();
        result.put("groupByGender", group);
        result.put("users", users);
        return result;
    }
}