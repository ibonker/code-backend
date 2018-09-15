package org.hotpotmaterial.code.security.service.impl;

import org.hotpotmaterial.code.security.WebSecurityConfig;
import org.hotpotmaterial.code.security.dto.UserInfo;
import org.hotpotmaterial.code.security.service.IUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class UserTokenServiceImpl implements IUserTokenService{

//  private Map<String, Object> userCache = new HashMap<>();

  public void put(String token, UserInfo user) {
//    userCache.put(token, user);
  }
  
  @Autowired
  private UserDetailsService userDetailsService;
  

/**
   * 通过token获取用户信息
   */
  public UserInfo getUser(String token) {
    return (UserInfo) userDetailsService.loadUserByUsername(token);
  }

  /**
   * 获取用户token
   */
  public String createToken(UserInfo user) {
    return user.getToken();
  }

  /**
   * 删除token
   */
  @Override
  public void deleteToken(String token) {
    RestTemplate rest = new RestTemplate();
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://" + WebSecurityConfig.getServer() + "/rescenter-rest/ResCenterApi/userLogout").queryParam("identityToken", token);
    rest.getForEntity(builder.toUriString(), String.class);
  }
}