package com.itheima.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

import static com.itheima.reggie.common.Constants.SQL_ERROR;

@Slf4j
@ResponseBody
@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandle(SQLIntegrityConstraintViolationException exception) {
        String message = exception.getMessage();
        log.info(message);
        if (message.contains(SQL_ERROR)) {
            String[] split = exception.getMessage().split(" ");
            String msg = split[2] + "这个用户名已经存在";
            return R.error(msg);
        }
        return R.error("未知错误");
    }
}
