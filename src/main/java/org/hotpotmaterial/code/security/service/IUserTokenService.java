package org.hotpotmaterial.code.security.service;

import org.hotpotmaterial.code.security.dto.UserInfo;

public interface IUserTokenService {
  
  /**
   * 保存token
   * @param token
   * @param user
   */
  public void put(String token, UserInfo user);

  /**
   * 获取用户信息
   * @param token
   * @return
   */
  public UserInfo getUser(String token);
  
  /**
   * 创建token
   * @param user
   * @return
   */
  public String createToken(UserInfo user);
  
  /**
   * 删除token
   * @param token
   */
  public void deleteToken(String token);
}