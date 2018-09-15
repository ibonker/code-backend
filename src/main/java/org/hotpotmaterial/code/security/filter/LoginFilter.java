package org.hotpotmaterial.code.security.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hotpotmaterial.code.entity.LoginInfoPO;
import org.hotpotmaterial.code.repository.LoginInfoRepository;
import org.hotpotmaterial.code.security.WebSecurityConfig;
import org.hotpotmaterial.code.security.dto.CurrentUserDTO;
import org.hotpotmaterial.code.security.dto.LoginResponseDTO;
import org.hotpotmaterial.code.security.dto.UserInfo;
import org.hotpotmaterial.code.security.service.IUserTokenService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	private IUserTokenService tokenService;
	
	private LoginInfoRepository loginInfoRepository;

	public LoginFilter(AuthenticationManager authenticationManager, IUserTokenService tokenService,LoginInfoRepository loginInfoRepository) {
		this.authenticationManager = authenticationManager;
		this.tokenService = tokenService;
		this.loginInfoRepository = loginInfoRepository;
	}

	/**
	 * 接收并解析用户凭证
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		String userId = (String) req.getParameter("username");
		String password = (String) req.getParameter("password");

		RestTemplate rest = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl("http://" + WebSecurityConfig.getServer() + "/rescenter-rest/ResCenterApi/userLogin")
				.queryParam("LoginID", userId).queryParam("Password", password);
		ResponseEntity<String> result = rest.getForEntity(builder.toUriString(), String.class);
		String token = "";
		try {
			JSONObject jo = new JSONObject(result.getBody());
			token = jo.getJSONObject("data").getString("msg");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(token, password, new ArrayList<>()));
	}
	
	/**
	 * 获取当前登陆的IP地址
	 * @param req
	 */
	private String getAdress(HttpServletRequest req) {
		String ip = req.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 用户成功登录后，这个方法会被调用，我们在这个方法里生成token
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		// 从Authentication中获取用户
		Object principal = auth.getPrincipal();
		// 初始化当前登录用户对象
		CurrentUserDTO currentUser = new CurrentUserDTO();
		if (principal != null && principal instanceof UserInfo) {
			UserInfo user = (UserInfo) principal;
			user.setPassword(null);
			String token = tokenService.createToken(user);
			tokenService.put(token, user);
			res.setHeader("AUTH_TOKEN", token);

			// 设值当前用户登录名
			currentUser.setUsername(user.getUsername());
			currentUser.setId(user.getId());
			currentUser.setOrgId(user.getOrgId());
			currentUser.setOrgName(user.getOrgName());
			currentUser.setName(user.getName());
			// currentUser.setOrgFullId(user.getOrgFullId());
			currentUser.setOrgFullName(user.getOrgFullName());
		}
		
		//查询该用户是否登陆过
		List<LoginInfoPO> loginInfos = loginInfoRepository.findByUserId(currentUser.getId());
		if(loginInfos!=null||loginInfos.size()>0) {
			currentUser.setFlag("0");
		}else {
			currentUser.setFlag("1");
		}
		//保存用户登陆信息
		LoginInfoPO loginInfo = new LoginInfoPO();
		String ip = getAdress(req);
		loginInfo.setIp(ip);
		loginInfo.setUserId(currentUser.getId());
		loginInfo.setUserName(currentUser.getName());
		loginInfoRepository.save(loginInfo);
		
		// 设值ContentType
		res.setContentType("application/json;charset=UTF-8");
		// 初始化RESPONSE BODY的对象
		LoginResponseDTO result = new LoginResponseDTO();
		result.setSuccess(true);
		// 设置HTTP CODE
		result.setStatus(res.getStatus());
		// 将当前用户放入RESPONSE中
		result.setData(currentUser);
		// 用PrintWriter写入RESPONSE
		PrintWriter out = null;
		// 转JSON
		ObjectMapper mapper = new ObjectMapper();
		// 将LoginResponseDTO转为JSON字符串
		String resultJson = mapper.writeValueAsString(result);
		try {
			// 获取PrintWriter
			out = res.getWriter();
			// 写入
			out.append(resultJson);
		} catch (IOException e) {
			log.error("", e);
		} finally {
			if (out != null) {
				// 关闭PrintWriter
				out.close();
			}
		}
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		SecurityContextHolder.clearContext();

		// 设置ContentType
		response.setContentType("application/json;charset=UTF-8");
		// 设置HTTP协议CODE
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		// 初始化RESPONSE消息体的对象
		LoginResponseDTO result = new LoginResponseDTO();
		result.setSuccess(true);
		// 返回状态403
		result.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		// 设置错误消息
		result.setErrorMessage(failed.getMessage());
		// 写入RESPONSE
		PrintWriter out = null;
		// 转JSON
		ObjectMapper mapper = new ObjectMapper();
		// 将RESULT对象转为JSON字符串
		String resultJson = mapper.writeValueAsString(result);
		try {
			// 写入RESPONSE
			out = response.getWriter();
			out.append(resultJson);
		} catch (IOException e) {
			log.error("", e);
		} finally {
			if (out != null) {
				// 关闭WRITER
				out.close();
			}
		}
	}
}