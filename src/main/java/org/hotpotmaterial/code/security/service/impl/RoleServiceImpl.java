package org.hotpotmaterial.code.security.service.impl;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hotpotmaterial.anywhere.common.mvc.page.rest.request.PageDTO;
import org.hotpotmaterial.anywhere.common.mvc.page.rest.response.ResultPageDTO;
import org.hotpotmaterial.anywhere.common.utils.MybatisFilterUtils;
import org.hotpotmaterial.code.security.WebSecurityConfig;
import org.hotpotmaterial.code.security.dao.RolePOMapper;
import org.hotpotmaterial.code.security.dto.HotpotUserSeniorDTO;
import org.hotpotmaterial.code.security.dto.UserInfo;
import org.hotpotmaterial.code.security.entity.HotpotFunctionPO;
import org.hotpotmaterial.code.security.entity.HotpotOrganizationPO;
import org.hotpotmaterial.code.security.entity.HotpotRolePO;
import org.hotpotmaterial.code.security.entity.HotpotUserPO;
import org.hotpotmaterial.code.security.service.IRoleService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class RoleServiceImpl implements IRoleService {
  
  @Autowired
  private RolePOMapper roleDao;

  /**
   * 根据角色获取目录
   */
  @Override
  public List<HotpotFunctionPO> selectFuncByRoleId(String roleId) {
    return roleDao.selectFunctionByRoleId(roleId);
  }
  
  /**
   * 获取用户信息
   */
  @Override
  public List<HotpotUserSeniorDTO> getUsersByRoleId(String roleId, UserInfo user) {
    
    RestTemplate rest = new RestTemplate();
    
    Map<String, Object> param = new HashMap<>();
    param.put("roleId", roleId);
    param.put("type", "0");
    List<String> idList = roleDao.selectPartyIdByRoleId(param);
    
    List<HotpotUserSeniorDTO> resultList = idList.stream().map((id)->{
      UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://"+ WebSecurityConfig.getServer() +"/rescenter-rest/ResCenterApi/getUserByID").queryParam("identityToken", user.getToken()).queryParam("userId", id);
      ResponseEntity<String> result = rest.getForEntity(builder.toUriString(), String.class);
      try {
        JSONObject jo = new JSONObject(result.getBody()).getJSONObject("data");
        HotpotUserPO hotUser = new HotpotUserPO();
        hotUser.setId(jo.getString("userId"));
        hotUser.setName(jo.getString("userFullName"));
        hotUser.setDelFlag("0");
        hotUser.setOrgId(jo.getString("departmentId"));
        hotUser.setUserName(jo.getString("loginID"));
        String desc = jo.getString("userDesc");
        Decoder de64 = Base64.getDecoder();
        JSONObject ext = new JSONObject(new String(de64.decode(desc)));
        
        HotpotOrganizationPO org = new HotpotOrganizationPO();
        org.setOrgFullName(ext.getString("departmentName").replaceAll("_", "/"));
        HotpotUserSeniorDTO userSe = new HotpotUserSeniorDTO();
        userSe.setHotpotUser(hotUser);
        userSe.setHotpotOrganization(org);
        return userSe;
      } catch (JSONException e) {
        return null;
      }
    }).collect(Collectors.toList());
    return resultList;
  }
  
  /**
   * 根据角色获取组织
   */
  @Override
  public List<HotpotOrganizationPO> getOrgsByRoleId(String roleId, UserInfo user) {
    RestTemplate rest = new RestTemplate();
    
    Map<String, Object> param = new HashMap<>();
    param.put("roleId", roleId);
    param.put("type", "1");
    List<String> idList = roleDao.selectPartyIdByRoleId(param);
    
    List<HotpotOrganizationPO> resultList = idList.stream().map((id)->{
      UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://"+ WebSecurityConfig.getServer() +"/rescenter-rest/ResCenterApi/getDepartmentByID").queryParam("identityToken", user.getToken()).queryParam("departmentID", id);
      ResponseEntity<String> result = rest.getForEntity(builder.toUriString(), String.class);
      try {
        JSONObject jo = new JSONObject(result.getBody()).getJSONObject("data");
        JSONObject desc = new JSONObject(jo.getString("departmentDesc"));
        HotpotOrganizationPO org = new HotpotOrganizationPO();
        org.setOrgFullName(desc.getString("fullDepartmentName").replaceAll("_", "/"));
        org.setOrgName(jo.getString("departmentName"));
        org.setId(jo.getString("departmentId"));
        return org;
      } catch (JSONException e) {
        return null;
      }
    }).collect(Collectors.toList());
    return resultList;
  }
  
  /**
   * 保存角色目录
   */
  @Override
  @Transactional
  public void saveRoleFuncs(String roleId, List<String> functionIds, UserInfo user) {
    if (roleDao.deleteRoleFunc(roleId) >= 0) {
      Date date = new Date();
      functionIds.stream().forEach((item) -> {
        Map<String, Object> param = new HashMap<>();
        param.put("functionId", item);
        param.put("roleId", roleId);
        param.put("createdBy", user.getUsername());
        param.put("createdAt", date);
        roleDao.saveRoleFunc(param);
      });
    }
  }
  
  /**
   * 保存角色-人员
   */
  @Override
  @Transactional
  public void saveRolePartys(String roleId,String type, List<String> partyIds, UserInfo user) {
    Date date = new Date();
    partyIds.stream().forEach((item) -> {
      Map<String, Object> param = new HashMap<>();
      param.put("roleId", roleId);
      param.put("partyId", item);
      param.put("type", type);
      param.put("createdBy", user.getUsername());
      param.put("createdAt", date);
      roleDao.saveRoleParty(param);
    });
  } 
  
  /**
   * 删除角色人员
   */
  @Override
  @Transactional
  public void deleteRoleParty(String roleId, List<String> partyIds) {
    partyIds.stream().forEach((item) -> {
      Map<String, Object> param = new HashMap<>();
      param.put("roleId", roleId);
      param.put("partyId", item);
      roleDao.deleteRoleParty(param);
    });
  }

  /**
   * 查询角色
   */
  @Override
  public ResultPageDTO<HotpotRolePO> getHotpotRoleList(PageDTO page) {

    ResultPageDTO<HotpotRolePO> result = new ResultPageDTO<HotpotRolePO>();
    
    String _parameter = MybatisFilterUtils.transSearchForMybatisSenoir(page);

    // 分页
    PageHelper.startPage(page.getPageParms().getPageIndex(), page.getPageParms().getPageSize());
    PageInfo<HotpotRolePO> list = new PageInfo<>(roleDao.searchRole(_parameter));

    result.setData(list.getList());
    result.setPageNumber(list.getPageNum());
    result.setPageSize(list.getPageSize());
    result.setTotalElements(list.getTotal());
    return result;
  }
  
  /**
   * 保存角色
   */
  @Override
  public int saveRole(HotpotRolePO hotpotRolePO, UserInfo user) {
    hotpotRolePO.preInsert();
    hotpotRolePO.setCreatedBy(user.getUsername());
    return roleDao.insertRole(hotpotRolePO);
  }

  /**
   * 更新角色
   */
  @Override
  public int updateRole(String id, HotpotRolePO hotpotRolePO, UserInfo user) {
    hotpotRolePO.setId(id);
    hotpotRolePO.preUpdate();
    hotpotRolePO.setUpdatedBy(user.getUsername());
    
    return roleDao.updateRole(hotpotRolePO);
  }

  /**
   * 删除角色
   */
  @Override
  @Transactional
  public int deleteRole(String id) {
    roleDao.deleteRole(id);
    roleDao.deleteRoleFunc(id);
    
    Map<String, Object> param = new HashMap<>();
    param.put("roleId", id);
    roleDao.deleteRoleParty(param);
    return 1;
  }

}