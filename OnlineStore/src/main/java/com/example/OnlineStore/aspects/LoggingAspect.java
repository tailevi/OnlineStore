package com.example.OnlineStore.aspects;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import com.example.OnlineStore.payload.request.ProductRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @AfterReturning(value = "@annotation(com.example.OnlineStore.annotations.LogUpdate)", returning = "result")
    public void logUpdateingMethod(JoinPoint joinPoint) {

        String methodName = joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();

        if (args.length > 0 && args[0] instanceof ProductRequest) {
            ProductRequest productRequest = (ProductRequest) args[0];
            Long productId = productRequest.getId();
            logger.info("Method '{}' successfully updated product with ID: {}", methodName, productId);
        }
    }

    @AfterThrowing(pointcut = "@annotation(com.example.OnlineStore.annotations.LogUpdate)", throwing = "exception")
    public void logUpdateException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();

        if (args.length > 0 && args[0] instanceof ProductRequest) {
            ProductRequest productRequest = (ProductRequest) args[0];
            Long productId = productRequest.getId();

            logger.error("Method '{}' threw an exception while updating product with ID: {}. Exception: {}",
                    methodName, productId, exception.getMessage());
        }
    }
}


