package com.wzh.myapp.exception;

import com.wzh.myapp.common.BaseResponse;
import com.wzh.myapp.common.ErrorCode;
import com.wzh.myapp.common.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 统一异常处理器
 *      捕获异常，将异常内部消化，不将错误返回前端
 *
 * @author wzh
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e) {
        log.error("businessException: " + e.getMessage(), e);
        return ResultUtil.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException: ", e);
        return ResultUtil.error(ErrorCode.SYSTEM_ERROR, "");
    }
}
