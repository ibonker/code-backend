package org.hotpotmaterial.code.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hotpotmaterial.code.security.WebSecurityConfig;
import org.hotpotmaterial.code.security.dao.UserPOMapper;
import org.hotpotmaterial.code.security.dto.UserInfo;
import org.hotpotmaterial.code.security.entity.HotpotFunctionPO;
import org.hotpotmaterial.code.security.service.IUserService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class UserServiceImpl implements IUserService {
  
  @Autowired
  private UserPOMapper userDao;
  
  /**
   * 根据用户获取目录
   */
  @Override
  public List<HotpotFunctionPO> selectFuncByPartyIdList(UserInfo user) {
    // 添加用户id
    List<String> partyIdList = new ArrayList<>();
    partyIdList.add(user.getId());
    
    // 添加用户所有组织id
    RestTemplate rest = new RestTemplate();
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://"+ WebSecurityConfig.getServer() +"/rescenter-rest/ResCenterApi/getParentDepartmentList").queryParam("identityToken", user.getToken()).queryParam("departmentId", user.getOrgId());
    ResponseEntity<String> result = rest.getForEntity(builder.toUriString(), String.class);
    
    List<String> orgIds = new ArrayList<>();
    orgIds.add(user.getOrgId());
    try {
      JSONObject jo = new JSONObject(result.getBody());
      JSONArray array = jo.getJSONObject("data").getJSONArray("departmentList");
      if (array != null && array.length() > 0) {
        for (int i = 0; i < array.length(); i++) {
          orgIds.add(array.getJSONObject(i).getString("departmentId"));
        }
      }
      
    } catch (JSONException e) {
      e.printStackTrace();
    }
    
    partyIdList.addAll(orgIds);
    
    return userDao.selectFunctionByPartyIdList(partyIdList);
  }

}