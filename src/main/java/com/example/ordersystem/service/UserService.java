package com.example.ordersystem.service;

import com.example.ordersystem.dto.response.BaseResponse;
import com.example.ordersystem.entity.MstUser;
import org.hibernate.mapping.Any;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<BaseResponse<Any>> addUser(MstUser mstUser);
    ResponseEntity<BaseResponse<List<MstUser>>> getUsers();
    ResponseEntity<BaseResponse<MstUser>> getUserById(Integer idUser);
    ResponseEntity<BaseResponse<Any>> updateUser(MstUser existUser,MstUser updateUser);
    ResponseEntity<BaseResponse<Any>> deleteUser(MstUser mstUser);
}
