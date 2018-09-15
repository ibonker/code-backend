package org.hotpotmaterial.code.security.dao;

import java.util.List;
import java.util.Map;

import org.hotpotmaterial.code.security.entity.HotpotFunctionPO;
import org.hotpotmaterial.code.security.entity.HotpotRolePO;

public interface RolePOMapper {
  
   /**
    * 角色所有的菜单
    * @param roleId
    * @return
    */
   List<HotpotFunctionPO> selectFunctionByRoleId(String roleId);
  
   /**
    * 删除角色的菜单
    * @param roleId
    * @return
    */
   int deleteRoleFunc(String roleId);
   
   /**
    * 保存角色-菜单
    * @param param
    * @return
    */
   int saveRoleFunc(Map<String, Object> param);
   
   /**
    * 保存角色-用户
    * @param param
    * @return
    */
   int saveRoleParty(Map<String, Object> param);
   
   /**
    * 删除角色-用户
    * @param param
    * @return
    */
   int deleteRoleParty(Map<String, Object> param);
   
   /**
    * 删除角色-菜单
    * @param functionId
    * @return
    */
   int deleteRoleFuncByFuncId(String functionId);
   
   /**
    * 查询角色
    * @param _parameter
    * @return
    */
   List<HotpotRolePO> searchRole(String _parameter);
   
   /**
    * 新增角色
    * @param hotpotRolePO
    * @return
    */
   int insertRole(HotpotRolePO hotpotRolePO);
   
   /**
    * 更新角色
    * @param hotpotRolePO
    * @return
    */
   int updateRole(HotpotRolePO hotpotRolePO);
   
   /**
    * 删除角色
    * @param id
    * @return
    */
   int deleteRole(String id);
   
   /**
    * 查询角色下所有人员
    * @param param
    * @return
    */
   List<String> selectPartyIdByRoleId(Map<String, Object> param);
}