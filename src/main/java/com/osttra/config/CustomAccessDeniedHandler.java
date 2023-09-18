package com.osttra.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        response.getWriter().write("Access Denied: You don't have permission to access this resource.");
        
        final Map<String, Object> body = new HashMap<>();
	    body.put("status", HttpServletResponse.SC_FORBIDDEN);
	    body.put("error", "You don't have permission to access this resource");
	    body.put("message", accessDeniedException.getMessage());
	    body.put("path", request.getServletPath());

	    final ObjectMapper mapper = new ObjectMapper();
	    mapper.writeValue(response.getOutputStream(), body);
    }
}
