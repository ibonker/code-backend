package org.hotpotmaterial.code.security.filter;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hotpotmaterial.code.security.dto.LoginResponseDTO;
import org.hotpotmaterial.code.security.service.IUserTokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthenLogoutFilter implements LogoutHandler {
  
  private IUserTokenService tokenService;
  
  public AuthenLogoutFilter(IUserTokenService tokenService){
    this.tokenService = tokenService;
  }
  
  /**
   * 注销用户
   */
  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {
    
    String token = request.getHeader("AUTH_TOKEN");  
    tokenService.deleteToken(token);
    
    //清空SecurityContext
    SecurityContextHolder.clearContext();
    
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);
    
    LoginResponseDTO result = new LoginResponseDTO();
    result.setSuccess(true);
    result.setStatus(HttpServletResponse.SC_OK);
    PrintWriter out = null;
    //转JSON
    ObjectMapper mapper = new ObjectMapper();
    //将RESULT对象转为JSON字符串
    try {
      String resultJson = mapper.writeValueAsString(result);
      out = response.getWriter();
      out.append(resultJson);
    } catch (IOException e) {
    } finally {
      if (out != null) {
        //关闭WRITER
        out.close();
      }
    }
    
  }

}