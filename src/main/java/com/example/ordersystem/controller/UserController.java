package com.example.ordersystem.controller;

import com.example.ordersystem.dto.response.BaseResponse;
import com.example.ordersystem.entity.MstUser;
import com.example.ordersystem.repository.UserRepository;
import com.example.ordersystem.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.mapping.Any;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
@Tag(name="User API")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping
    ResponseEntity<BaseResponse<Any>> createUser(@RequestBody MstUser newUser) {
        BaseResponse<Any> response = new BaseResponse<>();
        if (newUser == null) {
            response.setStatus("F");
            response.setMessage("Request Body is empty!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        MstUser existUserName = userRepository.findByNameIgnoreCase(newUser.getName());
        if (existUserName != null) {
            response.setStatus("F");
            response.setMessage("User name already exists!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return userService.addUser(newUser);
    }
    @GetMapping("all")
    ResponseEntity<BaseResponse<List<MstUser>>> getAllUsers() {
        return userService.getUsers();
    }
    @GetMapping("{id-user}")
    ResponseEntity<BaseResponse<MstUser>> getUserById(@PathVariable("id-user") Integer idUser) {
        BaseResponse<Any> response = new BaseResponse<>();
        if (idUser == null) {
            response.setStatus("F");
            response.setMessage("id user is empty!");
        }
        return userService.getUserById(idUser);
    }
    @PutMapping
    ResponseEntity<BaseResponse<Any>> updateUser(@RequestBody MstUser updateUser) {
        BaseResponse<Any> response = new BaseResponse<>();
        if(updateUser.getIdUser() == null || updateUser.getIdUser() == 0) {
            response.setStatus("F");
            response.setMessage("id user is empty!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Optional<MstUser> userOptional = userRepository.findById(updateUser.getIdUser());
        if(userOptional.isEmpty()) {
            response.setStatus("F");
            response.setMessage("id user not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        MstUser existUser = userOptional.get();
        if (existUser.getName().equals(updateUser.getName()) &&
                existUser.getAddress().equals(updateUser.getAddress())) {
            response.setStatus("F");
            response.setMessage("Nothing is needed to be updated!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return userService.updateUser(existUser, updateUser);
    }
    @DeleteMapping("{id-user}")
    ResponseEntity<BaseResponse<Any>> deleteUserById(@PathVariable("id-user") Integer idUser) {
        Optional<MstUser> userOptional = userRepository.findById(idUser);
        if(userOptional.isEmpty()) {
            BaseResponse<Any> response = new BaseResponse<>();
            response.setStatus("F");
            response.setMessage("id user not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return userService.deleteUser(userOptional.get());
    }
}
