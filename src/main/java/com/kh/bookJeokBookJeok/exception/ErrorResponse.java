package com.kh.bookJeokBookJeok.exception;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ErrorResponse {
    private List<Error> errors;

    public ErrorResponse(Exception exception) {
        errors = new ArrayList<>();
        errors.add(new Error(
                "Authorization Filter",
                null,
                exception.getMessage()
        ));
    }
    public ErrorResponse(AuthenticationException authenticationException) {
        errors = new ArrayList<>();
        errors.add(new Error(
                "Authentication Filter",
                null,
                authenticationException.getMessage().toString()
        ));
    }
    public ErrorResponse(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        this.errors = errors.stream().map(
                error -> new Error(
                            error.getField(),
                            error.getRejectedValue(),
                            error.getDefaultMessage()
                )
        ).collect(Collectors.toList());
    }

    public ErrorResponse(ConstraintViolationException e) {
        this.errors = e.getConstraintViolations().stream().map(
                error -> new Error(
                        error.getPropertyPath().toString(),
                        error.getInvalidValue().toString(),
                        error.getMessage()
                )
            ).collect(Collectors.toList());
    }

    public ErrorResponse(BusinessLogicException e) {
        Error error = new Error(
                Arrays.stream(e.getStackTrace()).findFirst().get().getMethodName(),
                e.getRejectedValue(),
                e.getExceptionCode().getDescription()
        );
        this.errors = List.of(error);
    }
    @AllArgsConstructor
    @Getter
    private class Error {
        private String location;
        private Object rejectedValue;
        private String reason;
    }

    public void sendError(HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(gson.toJson(this, ErrorResponse.class));
    }
}
