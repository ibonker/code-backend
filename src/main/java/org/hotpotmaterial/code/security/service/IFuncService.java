package org.hotpotmaterial.code.security.service;

import org.hotpotmaterial.anywhere.common.mvc.page.rest.request.PageDTO;
import org.hotpotmaterial.anywhere.common.mvc.page.rest.response.ResultPageDTO;
import org.hotpotmaterial.code.security.dto.UserInfo;
import org.hotpotmaterial.code.security.entity.HotpotFunctionPO;

public interface IFuncService {
  /**
   * 查询目录
   */
  public ResultPageDTO<HotpotFunctionPO> getHotpotFunctionList(PageDTO page);
  /**
   * 保存目录
   */
  public int saveFunc(HotpotFunctionPO hotpotFunctionPO, UserInfo user);
  /**
   * 更新目录
   */
  public int updateFunc(String id, HotpotFunctionPO hotpotFunction);
  /**
   * 删除目录
   */
  public int delete(String id);

}