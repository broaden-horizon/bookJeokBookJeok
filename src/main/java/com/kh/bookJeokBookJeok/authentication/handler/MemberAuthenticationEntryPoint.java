package com.kh.bookJeokBookJeok.authentication.handler;

import com.kh.bookJeokBookJeok.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class MemberAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Exception exception = (Exception) request.getAttribute("exception");
        ErrorResponse errorResponse = new ErrorResponse(exception == null ? authException : exception);
        errorResponse.sendError(response);
        logException(exception, authException);
    }

    public void logException(Exception exception, AuthenticationException authException) {
        String message = exception == null ? authException.getMessage() : exception.getMessage();
        log.warn("Unauthorized error occured : {}", message);
    }

}
