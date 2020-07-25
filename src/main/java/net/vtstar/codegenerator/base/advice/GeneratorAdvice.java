package net.vtstar.codegenerator.base.advice;

import freemarker.core.InvalidReferenceException;
import lombok.extern.slf4j.Slf4j;
import net.vtstar.codegenerator.base.advice.exception.GeneratorException;
import net.vtstar.codegenerator.generate.domain.Return;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.List;

/**
 * @Auther: liuxu
 * @Date: 2019/3/15
 * @Description:
 */
@Slf4j
@RestControllerAdvice("net.vtstar.codegenerator.generate.controller")
public class GeneratorAdvice {

    @ExceptionHandler(Exception.class)
    public Return handleSocketException(Exception ex) {
        String msg = ex.getMessage();
        if (ex instanceof IllegalArgumentException) {
            log.warn(msg);
            return Return.failed(msg);
        } else if (ex instanceof GeneratorException) {
            log.warn(msg);
            return Return.failed(msg);
        } else if (ex instanceof BindException) {
            BindException be = (BindException) ex;
            List<ObjectError> allErrors = be.getAllErrors();//捕获的所有错误对象
            ObjectError error = allErrors.get(0);
            msg = error.getDefaultMessage();//异常内容
            return Return.failed(msg);
        } else if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException e = (MethodArgumentNotValidException) ex;
            List<ObjectError> errors = e.getBindingResult().getAllErrors();
            msg = errors.get(0).getDefaultMessage();
            return Return.failed(msg);
        } else if (ex instanceof SQLException) {
            log.warn(msg);
            return Return.failed(msg);
        } else if (ex instanceof HttpMessageNotReadableException) {
            log.warn(msg);
            return Return.failed("请求JSON格式不正确");
        } else if (ex instanceof InvalidReferenceException) {
            log.warn(msg);
            return Return.failed("请求json缺少参数，具体报错内容为: " + msg);
        } else {
            log.warn(msg, ex);
            return Return.failed("内部错误！");
        }
    }

}
