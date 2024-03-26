package org.itheima.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

/*
*
*全局异常处理器
* */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobaExceptionHandler {
/*
* 异常处理方法*/
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException exception){
        log.info(exception.getMessage());
if (exception.getMessage().contains("Duplicate entry")){
    String[] s = exception.getMessage().split(" ");
    String msg=s[2]+"已存在";
    return R.error(msg);
}
        return R.error("未知错误");
    }
}
