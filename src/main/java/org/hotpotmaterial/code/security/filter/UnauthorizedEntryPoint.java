package org.hotpotmaterial.code.security.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hotpotmaterial.code.security.dto.LoginResponseDTO;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
  
  /**
   * 登录失败返回信息
   */
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
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
        result.setErrorMessage(authException.getMessage());
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