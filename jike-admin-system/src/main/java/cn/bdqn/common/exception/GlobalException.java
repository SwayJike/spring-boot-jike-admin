package cn.bdqn.common.exception;

import cn.bdqn.common.lang.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @title:GlobleException
 * @Author SwayJike
 * @Date:2021/9/4 15:51
 * @Version 1.0
 */

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {RuntimeException.class})
    public CommonResult handler(RuntimeException e){
        log.error("运行时异常....",e.getMessage());
        return CommonResult.failure().setMessage(e.getMessage());
    }
}
