package com.kh.bookJeokBookJeok.aop;

import com.kh.bookJeokBookJeok.authentication.AuthenticationUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

@Aspect
@Component
@AllArgsConstructor
@Slf4j
public class AssignMemberIdAspect {
    private final AuthenticationUtils authenticationUtils;

    @Before("@annotation(com.kh.bookJeokBookJeok.aop.AssignMemberId)")
    private void assignMemberId(JoinPoint joinPoint) {
        Arrays.stream(joinPoint.getArgs())
                .forEach(arg -> getMethod(arg.getClass(), "setMemberId")
                        .ifPresent(setMemberIdMethod -> invokeMethod(arg, setMemberIdMethod, authenticationUtils.extractMemberId())));
    }
    private void invokeMethod(Object obj, Method method, Long memberId) {
        try {
            method.invoke(obj, memberId);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    private Optional<Method> getMethod(Class<?> clazz, String methodName) {
        try {
            return Optional.of(clazz.getMethod(methodName, Long.class));
        } catch (NoSuchMethodException e) {
            return Optional.empty();
        }
    }

}
