package com.java1234.common.exception;

import com.java1234.entity.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 配置全局异常处理，捕获到所有异常
 * @author Yiming
 */
//日志
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    //指定捕获运行时异常，做运行时异常输出
    @ExceptionHandler(value = RuntimeException.class)
    public R handler(RuntimeException e){
        log.error("运行时异常：----------------{}", e.getMessage());
        System.out.println("运行时异常：");
//        返回到前端去
        return R.error(e.getMessage());
    }
}
