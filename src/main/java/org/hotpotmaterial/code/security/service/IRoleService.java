package org.hotpotmaterial.code.security.service;

import java.util.List;

import org.hotpotmaterial.anywhere.common.mvc.page.rest.request.PageDTO;
import org.hotpotmaterial.anywhere.common.mvc.page.rest.response.ResultPageDTO;
import org.hotpotmaterial.code.security.dto.HotpotUserSeniorDTO;
import org.hotpotmaterial.code.security.dto.UserInfo;
import org.hotpotmaterial.code.security.entity.HotpotFunctionPO;
import org.hotpotmaterial.code.security.entity.HotpotOrganizationPO;
import org.hotpotmaterial.code.security.entity.HotpotRolePO;

  
public interface IRoleService {
  /**
   * 角色获取所有菜单
   * @param roleId
   * @return
   */
  public List<HotpotFunctionPO> selectFuncByRoleId(String roleId);
  
  /**
   * 获取角色所含人员
   * @param roleId
   * @return
   */
  public List<HotpotUserSeniorDTO> getUsersByRoleId(String roleId, UserInfo user);
  
  /**
   * 获取角色所含公司
   * @param roleId
   * @return
   */
  public List<HotpotOrganizationPO> getOrgsByRoleId(String roleId, UserInfo user);
  
  /**
   * 保存角色菜单
   * @param roleId
   * @param functionIds
   */
  public void saveRoleFuncs(String roleId, List<String> functionIds, UserInfo user);
  
  /**
   * 保存角色菜单
   * @param roleId
   * @param functionIds
   */
  public void saveRolePartys(String roleId,String type, List<String> partyIds, UserInfo user);
  
  /**
   * 删除角色 人员 组织
   * @param roleId
   * @param partyId
   */
  public void deleteRoleParty(String roleId, List<String> partyIds);
  
  /**
   * datatable 分页查询
   * 
   * @param page
   * @return
   */
  public ResultPageDTO<HotpotRolePO> getHotpotRoleList(PageDTO page);
  
  /**
   * 保存橘色
   * @param hotpotRolePO
   * @param user
   * @return
   */
  public int saveRole(HotpotRolePO hotpotRolePO, UserInfo user);
  
  /**
   * 更新角色
   * @param id
   * @param hotpotRolePO
   * @param user
   * @return
   */
  public int updateRole(String id, HotpotRolePO hotpotRolePO, UserInfo user);
  
  /**
   * 删除橘色
   * @param id
   * @return
   */
  public int deleteRole(String id);
  
  
}