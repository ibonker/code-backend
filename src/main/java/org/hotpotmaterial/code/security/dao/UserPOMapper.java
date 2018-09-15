package org.hotpotmaterial.code.security.dao;

import java.util.List;

import org.hotpotmaterial.code.security.entity.HotpotFunctionPO;

public interface UserPOMapper {
  
  /**
   * 用户获取菜单
   * @param partyIdList
   * @return
   */
  List<HotpotFunctionPO> selectFunctionByPartyIdList(List<String> partyIdList);
  
}