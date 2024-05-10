package com.wzh.myapp.controller;

import com.wzh.myapp.common.BaseResponse;
import com.wzh.myapp.common.ErrorCode;
import com.wzh.myapp.common.ResultUtil;
import com.wzh.myapp.exception.BusinessException;
import com.wzh.myapp.model.request.UserLoginRequest;
import com.wzh.myapp.model.request.UserRegisterRequest;
import com.wzh.myapp.model.response.UserInfoResponse;
import com.wzh.myapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 75654
 * @date 2024/5/9 11:13
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    // region 注册、登录、退出

    /**
     * 用户注册
     * @param userRegisterRequest 用户注册请求
     * @return 用户id
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 获取参数
        Long userId = userService.userRegister(userRegisterRequest);
        return ResultUtil.success(userId);
    }

    @PostMapping("/login")
    public BaseResponse<UserInfoResponse> userLogin(@RequestBody UserLoginRequest userLoginRequest,
                                                    HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 获取参数
        UserInfoResponse userInfoResponse = userService.userLogin(userLoginRequest, request);
        return ResultUtil.success(userInfoResponse);
    }

    @GetMapping("/getCode")
    public BaseResponse<String> getCode() {
        String code = userService.getCode();
        return ResultUtil.success(code);
    }

    // endregion
}
