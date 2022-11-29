package com.customfusionrestapi.interceptor;

import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.customfusionrestapi.constants.RelianceConstants;
import com.customfusionrestapi.dto.RoleResponse;
import com.customfusionrestapi.service.AuthenticateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class RelianceSecurityInterceptor implements HandlerInterceptor {

	@Autowired
	private AuthenticateService authService;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		System.out.println("[Inside PRE Handle interceptor][" + request + "]" + "[" + request.getMethod() + "]"
				+ request.getRequestURI());

		String basicAuthHeaderValue = request.getHeader(RelianceConstants.AUTH_HEADER_PARAMETER_AUTHERIZATION);

		System.out.println("Inside validation" + request.getRequestURI() + " :" + basicAuthHeaderValue);
		try {

			if (basicAuthHeaderValue == null) {
				response.setStatus(HttpStatus.FORBIDDEN.value());

				Map<String, Object> data = new HashMap<>();
				data.put("timestamp", Calendar.getInstance().getTimeInMillis());
				data.put("status", HttpStatus.FORBIDDEN.value());
				data.put("error", "Forbidden");
				data.put("message", "Authorization Header is Missing or Empty");
				data.put("path", request.getRequestURI().toString());

				OutputStream out = response.getOutputStream();
				mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
				mapper.writeValue(out, data);
				out.flush();
			}

			RoleResponse roleResponse = authService.validateBasicAuthentication(basicAuthHeaderValue);

			response.addHeader("User", roleResponse.getUser());
			response.addHeader("Role", roleResponse.getRole());

		} catch (Exception e) {
			System.out.println("Error occured while authenticating request : " + e.getMessage());

			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

			Map<String, Object> data = new HashMap<>();
			data.put("timestamp", Calendar.getInstance().getTimeInMillis()); // you can format your date here
			data.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			data.put("error", "Internal Server Error");
			data.put("message", "Something went wrong...");
			data.put("path", request.getRequestURI().toString());

			OutputStream out = response.getOutputStream();
			mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
			mapper.writeValue(out, data);
			out.flush();
		}

		return true;

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		System.out.println("[Inside POST Handle Interceptor]" + request.getRequestURI());

	}
}
