package org.hotpotmaterial.code.security.filter;

import java.util.ArrayList;

import org.hotpotmaterial.code.security.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

public class RescenterUserProvider implements AuthenticationProvider {
  
  @Autowired
  private UserDetailsService userDetailsService;
  
  /**
   * 登录验证
   */
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String token = (String) authentication.getPrincipal();
    UserInfo user = (UserInfo)userDetailsService.loadUserByUsername(token);
    if (user == null) {
      throw new BadCredentialsException("login failed");
    }
    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return true;
  }

}