package org.hotpotmaterial.code.security.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hotpotmaterial.anywhere.common.mvc.page.rest.request.PageDTO;
import org.hotpotmaterial.anywhere.common.mvc.page.rest.response.ResultPageDTO;
import org.hotpotmaterial.code.security.dao.FuncPOMapper;
import org.hotpotmaterial.code.security.dao.RolePOMapper;
import org.hotpotmaterial.code.security.dto.UserInfo;
import org.hotpotmaterial.code.security.entity.HotpotFunctionPO;
import org.hotpotmaterial.code.security.entity.HotpotRolePO;
import org.hotpotmaterial.code.security.service.IFuncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class FuncServiceImpl implements IFuncService {
  
  @Autowired
  private FuncPOMapper funcDao;
  
  @Autowired
  private RolePOMapper roleDao;
  
  /**
   * 查询目录
   */
  @Override
  public ResultPageDTO<HotpotFunctionPO> getHotpotFunctionList(PageDTO page) {

    ResultPageDTO<HotpotFunctionPO> result = new ResultPageDTO<HotpotFunctionPO>();

    PageHelper.startPage(page.getPageParms().getPageIndex(), page.getPageParms().getPageSize());
    PageInfo<HotpotFunctionPO> list = new PageInfo<>(funcDao.getAllFunc());

    result.setData(list.getList());
    result.setPageNumber(list.getPageNum());
    result.setPageSize(list.getPageSize());
    result.setTotalElements(list.getTotal());
    return result;
  }
  
  /**
   * 保存目录
   */
  @Override
  public int saveFunc(HotpotFunctionPO hotpotFunctionPO, UserInfo user) {
    hotpotFunctionPO.preInsert();
    if (funcDao.insert(hotpotFunctionPO) > 0) {
      List<HotpotRolePO> roleList = funcDao.selectRoleListByFuncId("11");
      for (HotpotRolePO role : roleList) {
        Map<String, Object> param = new HashMap<>();
        param.put("functionId", hotpotFunctionPO.getId());
        param.put("roleId", role.getId());
        param.put("createdBy", user.getUsername());
        param.put("createdAt", new Date());
        roleDao.saveRoleFunc(param);
      }
    }
    return 1;
  }

  /**
   * 更新目录
   */
  @Override
  public int updateFunc(String id, HotpotFunctionPO hotpotFunction) {
    hotpotFunction.preUpdate();
    hotpotFunction.setId(id);
    return funcDao.update(hotpotFunction);    
  }
  
  /**
   * 删除目录
   */
  @Override
  public int delete(String id) {
    List<HotpotFunctionPO> children =  funcDao.getAllChildren(id);
    if (children != null && children.size() > 0) {
      return 0;
    }
    roleDao.deleteRoleFuncByFuncId(id);
    return funcDao.delete(id);
  }  
}