package com.hyl.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

//aop实现日志功能，在请求方法调用前获得请求的url,ip,方法，参数和最终的返回值
@Aspect
@Component
public class LogAspect {
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Pointcut("execution(* com.hyl.blog.web.*.*(..))")
    public void log(){}
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        //获得请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String requestMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog log = new RequestLog(url,ip,requestMethod,args);
        logger.info("Request : {}",log);
    }
     @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("Result : {}",result);
     }

     //产生日志的对象，包括请求的url,ip,请求的方法，参数
     private class RequestLog{
        private String url;
        private String ip;
        private String requestMethod;
        private Object[] args;

         public RequestLog(String url, String ip, String requestMethod, Object[] args) {
             this.url = url;
             this.ip = ip;
             this.requestMethod = requestMethod;
             this.args = args;
         }
         @Override
         public String toString() {
             return "RequestLog{" +
                     "url='" + url + '\'' +
                     ", ip='" + ip + '\'' +
                     ", requestMethod='" + requestMethod + '\'' +
                     ", args=" + Arrays.toString(args) +
                     '}';
         }
     }
}
