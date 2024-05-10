package com.wzh.myapp.service;

import com.wzh.myapp.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzh.myapp.model.request.UserLoginRequest;
import com.wzh.myapp.model.request.UserRegisterRequest;
import com.wzh.myapp.model.response.UserInfoResponse;

import javax.servlet.http.HttpServletRequest;

/**
* @author 75654
* @description 针对表【user(用户信息表)】的数据库操作Service
* @createDate 2024-05-09 10:58:04
*/
public interface UserService extends IService<User> {

    Long userRegister(UserRegisterRequest userRegisterRequest);

    UserInfoResponse userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request);

    UserInfoResponse getSafetyUser(User user);

    String getCode();

    int userLogout(HttpServletRequest request);
}
