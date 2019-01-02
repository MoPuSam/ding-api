package com.oulam.dingding.aop;

import com.oulam.dingding.exception.DingYunDepartException;
import com.oulam.dingding.exception.DingYunUserException;
import com.oulam.dingding.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oulam on 2018/12/31.
 */
@ControllerAdvice(basePackages = "com.oulam.dingding.controller")
public class GlobalExceptionHandler {//全局错误处理器
    protected static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    /**
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map<String, Object> errorResult() {
        Map<String, Object> errorResult = new HashMap<String, Object>();
        errorResult.put("errorCode", "500");
        errorResult.put("errorMsg", "AOP捕获全局异常。");
        return errorResult;
    }
     **/
    @ExceptionHandler(DingYunUserException.class)
    @ResponseBody
    public String userException(DingYunUserException e) {// 用户相关异常做出响应
        log.info("钉钉用户相关同步异常:"+e.getMessage());
        return e.getMessage();
    }
    @ExceptionHandler(DingYunDepartException.class)
    @ResponseBody
    public String departException(DingYunUserException e) {// 部门相关异常做出响应
        log.info("钉钉部门相关同步异常:"+e.getMessage());
        return e.getMessage();
    }


}
