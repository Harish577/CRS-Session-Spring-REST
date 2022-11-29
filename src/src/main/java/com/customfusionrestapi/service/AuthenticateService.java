package com.customfusionrestapi.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.customfusionrestapi.dto.JwtToken;
import com.customfusionrestapi.dto.RoleResponse;
import com.customfusionrestapi.dto.RolesResponse;
import com.customfusionrestapi.dto.Values;
import com.customfusionrestapi.util.HttpUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@ComponentScan({ "com.customfusionrestapi.util" })
public class AuthenticateService {

	@Value("${myApp.rolesApi}")
	private String rolesApiUrl;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private HttpUtil httpUtil;

	public RoleResponse validateBasicAuthentication(String basicAuthHeaderValue) throws JsonParseException,
			JsonMappingException, IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		String[] split_string = basicAuthHeaderValue.split("\\.");
		String base64EncodedHeader = split_string[0];
		String base64EncodedBody = split_string[1];
		// String base64EncodedSignature = split_string[2];

		System.out.println(base64EncodedHeader + " " + base64EncodedBody);
		Base64 base64Url = new Base64(true);
		String header = new String(base64Url.decode(base64EncodedHeader));
		System.out.println("JWT Header : " + header);

		String body = new String(base64Url.decode(base64EncodedBody));

		JwtToken user = objectMapper.readValue(body, JwtToken.class);

		System.out.println("JWT user : " + user.toString());

		HttpHeaders headers = httpUtil.getHttpHeaders();

		headers.add("Authorization", basicAuthHeaderValue);
		headers.add("X-Ingr-OnBehalfOf", "sujana");
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		RestTemplate restTemplate = httpUtil.ignoreSSLWithHost();

		ResponseEntity<RolesResponse> responseEntity = restTemplate.exchange(rolesApiUrl, HttpMethod.GET, entity,
				RolesResponse.class);

		List<Values> valueList = responseEntity.getBody().getValue();

		System.out.println("valueList:" + responseEntity.getBody().getValue());

		StringBuilder role = new StringBuilder();
		valueList.forEach(it -> {
			if (it.isSelected() == true) {
				role.append(it.getDisplayName() + ",");
			}

		});

		System.out.print("Harish" + role.substring(0, role.length() - 1));

		RoleResponse roleResponse = new RoleResponse();

		roleResponse.setUser(user.getName());
		roleResponse.setRole(role.substring(0, role.length() - 1));

		return roleResponse;

	}

}
