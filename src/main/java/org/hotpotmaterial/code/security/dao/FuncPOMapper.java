package org.hotpotmaterial.code.security.dao;

import java.util.List;

import org.hotpotmaterial.code.security.entity.HotpotFunctionPO;
import org.hotpotmaterial.code.security.entity.HotpotRolePO;

public interface FuncPOMapper {
    
    /**
     * 获取所有菜单
     * @return
     */
    List<HotpotFunctionPO> getAllFunc();
    
    /**
     * 新增菜单
     * @param record
     * @return
     */
    int insert(HotpotFunctionPO record);
    
    /**
     * 更新菜单
     * @param record
     * @return
     */
    int update(HotpotFunctionPO record);
    
    /**
     * 删除菜单
     * @param id
     * @return
     */
    int delete(String id);
    
    /**
     * 获取所有子菜单
     * @param id
     * @return
     */
    List<HotpotFunctionPO> getAllChildren(String id);
    
    /**
     * 获取菜单的所有角色权限
     * @param functionId
     * @return
     */
    List<HotpotRolePO> selectRoleListByFuncId(String functionId);

}