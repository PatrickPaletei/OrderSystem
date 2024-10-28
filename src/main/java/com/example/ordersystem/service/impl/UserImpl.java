package com.example.ordersystem.service.impl;

import com.example.ordersystem.dto.response.BaseResponse;
import com.example.ordersystem.entity.MstUser;
import com.example.ordersystem.repository.UserRepository;
import com.example.ordersystem.service.UserService;
import org.hibernate.mapping.Any;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserImpl implements UserService {

    private final UserRepository userRepository;

    public UserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<BaseResponse<Any>> addUser(MstUser mstUser) {
        MstUser newUser = new MstUser();
        newUser.setName(mstUser.getName());
        newUser.setAddress(mstUser.getAddress());
        userRepository.save(newUser);
        BaseResponse<Any> response = new BaseResponse<>();
        response.setMessage("Status "+ mstUser.getName() + " added successfully");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BaseResponse<List<MstUser>>> getUsers() {
        List<MstUser> usersList = userRepository.findAll();
        BaseResponse<List<MstUser>> response = new BaseResponse<>();
        if(usersList.isEmpty()){
            response.setMessage("No users found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.setMessage("Found " + usersList.size() + " users");
        response.setData(usersList);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BaseResponse<MstUser>> getUserById(Integer idUser) {
        Optional<MstUser> mstUser = userRepository.findById(idUser);
        BaseResponse<MstUser> response = new BaseResponse<>();
        if(mstUser.isEmpty()){
            response.setMessage("No user found with id "+idUser);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.setMessage(mstUser.get().getName() + " found successfully");
        response.setData(mstUser.get());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BaseResponse<Any>> updateUser(
            MstUser existUser, MstUser updateUser) {
        if (!existUser.getName().equals(updateUser.getName())) {
            existUser.setName(updateUser.getName());
        }
        if (!existUser.getAddress().equals(updateUser.getAddress())) {
            existUser.setAddress(updateUser.getAddress());
        }
        MstUser updatedUser = userRepository.save(existUser);
        BaseResponse<Any> response = new BaseResponse<>();
        response.setMessage("Status "+ updatedUser.getName() + " updated successfully");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BaseResponse<Any>> deleteUser(MstUser mstUser) {
        userRepository.delete(mstUser);
        BaseResponse<Any> response = new BaseResponse<>();
        response.setMessage("Status "+ mstUser.getName() + " deleted successfully");
        return ResponseEntity.ok(response);
    }
}
