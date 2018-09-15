package org.hotpotmaterial.code.security.service;

import java.util.List;

import org.hotpotmaterial.code.security.dto.UserInfo;
import org.hotpotmaterial.code.security.entity.HotpotFunctionPO;

public interface IUserService {
  
  /**
   * 用户获取对应菜单
   * @param user
   * @return
   */
  public List<HotpotFunctionPO> selectFuncByPartyIdList(UserInfo user);
  
}