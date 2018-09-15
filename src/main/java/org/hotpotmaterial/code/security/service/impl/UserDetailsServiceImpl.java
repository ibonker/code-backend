package org.hotpotmaterial.code.security.service.impl;

import java.util.ArrayList;

import org.hotpotmaterial.code.security.WebSecurityConfig;
import org.hotpotmaterial.code.security.dto.UserInfo;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  
  /**
   * 登录验证
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    
    RestTemplate rest = new RestTemplate();
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://"+ WebSecurityConfig.getServer() +"/rescenter-rest/ResCenterApi/getUserByToken").queryParam("identityToken", username);
    ResponseEntity<String> result = rest.getForEntity(builder.toUriString(), String.class);
    
    try {
      JSONObject resUserInfo = new JSONObject(result.getBody()).getJSONObject("data");
      if (resUserInfo != null) {
        return new UserInfo(resUserInfo, username, new ArrayList<>());
      }
    } catch (JSONException e) {
      return null;
    }
    return null;
  }

}