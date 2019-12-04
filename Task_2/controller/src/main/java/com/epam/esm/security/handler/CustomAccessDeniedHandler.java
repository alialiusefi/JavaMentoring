package com.epam.esm.security.handler;

import com.epam.esm.exception.APIError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException,
            ServletException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(403);
        List<String> errors = new ArrayList<>();

        errors.add("timestamp: " + System.currentTimeMillis());
        errors.add("status: " + 403);
        errors.add("message: " + "Access denied");
        response.getWriter().write(mapper.writeValueAsString(new APIError(errors)));

    }
}
