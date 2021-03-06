package org.hotpotmaterial.code.security.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hotpotmaterial.code.security.dto.LoginResponseDTO;
import org.hotpotmaterial.code.security.dto.UserInfo;
import org.hotpotmaterial.code.security.service.IUserTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationFilter extends BasicAuthenticationFilter {
  
  private IUserTokenService tokenService;

  public AuthenticationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }
  
  public AuthenticationFilter(AuthenticationManager authenticationManager, IUserTokenService tokenService) {
    super(authenticationManager);
    this.tokenService = tokenService;
  }
  
  /**
   * 拦截所有请求
   */
  @Override  
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {  
      String header = request.getHeader("AUTH_TOKEN");  

      if (StringUtils.isEmpty(header)) {  
        chain.doFilter(request, response);
        return;
      }  

      UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

      SecurityContextHolder.getContext().setAuthentication(authentication);  
      chain.doFilter(request, response);  

  }  
  
  /**
   * 获取用户信息
   * @param request
   * @return
   */
  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {  
      String token = request.getHeader("AUTH_TOKEN");  
      if (token != null && tokenService != null) {  
          UserInfo user = tokenService.getUser(token);
          if (user != null) {  
            return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
          }  
          return null;
      }  
      return null;  
  }
  
  /**
   * 用户信息获取失败
   */
  @Override
  protected void onUnsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed)
              throws IOException {

    SecurityContextHolder.clearContext();

    //设置ContentType
    response.setContentType("application/json;charset=UTF-8");
    //设置HTTP协议CODE
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    //初始化RESPONSE消息体的对象
    LoginResponseDTO result = new LoginResponseDTO();
    result.setSuccess(true);
    //返回状态403
    result.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    //设置错误消息
    result.setErrorMessage(failed.getMessage());
    //写入RESPONSE
    PrintWriter out = null;
    //转JSON
    ObjectMapper mapper = new ObjectMapper();
    //将RESULT对象转为JSON字符串
    String resultJson = mapper.writeValueAsString(result);
    try {
      //写入RESPONSE
      out = response.getWriter();
      out.append(resultJson);
    } catch (IOException e) {
      log.error("", e);
    } finally {
      if (out != null) {
        //关闭WRITER
        out.close();
      }
    }
  
  }

}