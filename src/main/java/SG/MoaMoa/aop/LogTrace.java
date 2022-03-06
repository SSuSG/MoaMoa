package SG.MoaMoa.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@Aspect
public class LogTrace {

    @Around("execution(* SG.MoaMoa..*(..))")
    public Object doLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("[log] : {}",proceedingJoinPoint.getSignature());
        return proceedingJoinPoint.proceed();
    }


}
