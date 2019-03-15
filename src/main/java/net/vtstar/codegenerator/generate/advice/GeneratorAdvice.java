package net.vtstar.codegenerator.generate.advice;

import lombok.extern.slf4j.Slf4j;
import net.vtstar.utils.domain.Return;
import net.vtstar.utils.exception.KnownException;
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
        } else if (ex instanceof KnownException) {
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
        } else {
            log.warn(msg, ex);
            return Return.failed("内部错误！");
        }
    }

}
