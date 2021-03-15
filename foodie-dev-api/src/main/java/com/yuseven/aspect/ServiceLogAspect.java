package com.yuseven.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Author Yu Qifeng
 * @Date 2021/3/1 19:25
 * @Version v1.0
 */
@Aspect
@Component
public class ServiceLogAspect {

    public static final Logger log = LoggerFactory.getLogger(Serializable.class);

    /**
     * 切面表达式
     * execution - 代表所要执行的表达式主体
     * 第一处 * 号表示方法返回类型，* 代表所有类型
     * 第二处 包名 代表apo监控的类所在的包
     * 第三处 .. 代表该包以及其子包下的所有类方法
     * 第四处 * 代表类名，*代表所有类
     * 第五处 *(..) *代表类中的方法名，(..)表示方法中的任何参数
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.yuseven.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("=================方法开始执行了 {}.{} ==================",
                joinPoint.getTarget().getClass(),
                joinPoint.getSignature());
        // 记录开始时间
        long begin = System.currentTimeMillis();
        // 执行目标 service
        Object result = joinPoint.proceed();
        // 记录结束时间
        long end = System.currentTimeMillis();
        // 记录开始到结束的花费时间
        long takeTime = end - begin;
        if (takeTime > 3000) {
            log.error("=============== 执行结束，耗时：{} 毫秒 ========================", takeTime);
        } else if (takeTime > 2000) {
            log.warn("=============== 执行结束，耗时：{} 毫秒 ========================", takeTime);
        } else  {
            log.info("=============== 执行结束，耗时：{} 毫秒 ========================", takeTime);
        }
        return result;
    }

}
