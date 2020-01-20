package com.dao.sso.reactive.exception;

import com.holderzone.framework.exception.unchecked.BusinessException;
import com.holderzone.framework.exception.unchecked.ParameterException;
import com.holderzone.framework.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author HuChiHui
 * @date 2020/01/17 下午 17:00
 * @description
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(value = ParameterException.class)
    public Result<String> handleParameterException(ParameterException e) {
        String message = e.getMessage();
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error(message, e);
        }

        return Result.buildFailResult(400, message);
    }

    @ExceptionHandler(value = BusinessException.class)
    public Result<String> handleBusinessException(BusinessException e) {
        String message = e.getMessage();
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error(message, e);
        }
        return Result.buildFailResult(400, message);
    }

    @ExceptionHandler(value = Exception.class)
    public Result<String> handleException(RuntimeException e) {
        String message = e.getMessage();
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error(message, e);
        }
        return Result.buildOpFailedResult(message);
    }

}
