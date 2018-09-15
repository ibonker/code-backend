package org.hotpotmaterial.code.service.impl;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;
import java.util.stream.Collectors;

import org.hotpotmaterial.code.dto.ResultOfPartyProjectDTO;
import org.hotpotmaterial.code.entity.PartyProjectPO;
import org.hotpotmaterial.code.repository.PartyPorjectRepository;
import org.hotpotmaterial.code.security.WebSecurityConfig;
import org.hotpotmaterial.code.security.dto.HotpotUserSeniorDTO;
import org.hotpotmaterial.code.security.dto.UserInfo;
import org.hotpotmaterial.code.security.entity.HotpotOrganizationPO;
import org.hotpotmaterial.code.security.entity.HotpotUserPO;
import org.hotpotmaterial.code.service.IPartyProjectService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PartyProjectServiceImpl implements IPartyProjectService {
	@Autowired
	private PartyPorjectRepository partyporjectRepository;

	@Override
	@Transactional("jpaTransactionManager")
	public void savePartyProject(List<PartyProjectPO> partyprojectPO) {
	  
	  if (partyprojectPO != null && partyprojectPO.size() > 0) {
	    for (PartyProjectPO ppp: partyprojectPO) {
	      // 是否为新增
	      if (ppp.isNew()) {
	        // 初始化数据
	        ppp.preInsert();
	        // 保存数据
	        partyporjectRepository.save(partyprojectPO);
	      }
	    }
	  }
	}

	@Override
	@Transactional("jpaTransactionManager")
	public void deletePartyProject(String partyprojectId) {
		partyporjectRepository.delete(partyprojectId);

	}

	@Override
	public ResultOfPartyProjectDTO findUserInfoByProjectId(String projectId, String style, UserInfo user) {
		RestTemplate rest = new RestTemplate();
		ResultOfPartyProjectDTO resultDto = new ResultOfPartyProjectDTO();
		List<Object[]> idList = partyporjectRepository.findByProjectIdAndStyle(projectId, style);
		if ("0".equals(style)) {
			List<HotpotUserSeniorDTO> resultList = idList.stream().map((id) -> {
				UriComponentsBuilder builder = UriComponentsBuilder
						.fromHttpUrl(
								"http://" + WebSecurityConfig.getServer() + "/rescenter-rest/ResCenterApi/getUserByID")
						.queryParam("identityToken", user.getToken()).queryParam("userId", id[0]);
				ResponseEntity<String> result = rest.getForEntity(builder.toUriString(), String.class);
				try {
					JSONObject jo = new JSONObject(result.getBody()).getJSONObject("data");
					HotpotUserPO hotUser = new HotpotUserPO();
					hotUser.setId(jo.getString("userId"));
					hotUser.setName(jo.getString("userFullName"));
					hotUser.setDelFlag("0");
					hotUser.setOrgId(jo.getString("departmentId"));
					hotUser.setUserName(jo.getString("loginID"));
					hotUser.setFlag((String)id[1]);
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
			resultDto.setInfoList(resultList);
		} else if ("1".equals(style)) {
			List<HotpotOrganizationPO> resultList = idList.stream().map((id) -> {
				UriComponentsBuilder builder = UriComponentsBuilder
						.fromHttpUrl("http://" + WebSecurityConfig.getServer()
								+ "/rescenter-rest/ResCenterApi/getDepartmentByID")
						.queryParam("identityToken", user.getToken()).queryParam("departmentID", id[0]);
				ResponseEntity<String> result = rest.getForEntity(builder.toUriString(), String.class);
				try {
					JSONObject jo = new JSONObject(result.getBody()).getJSONObject("data");
					JSONObject desc = new JSONObject(jo.getString("departmentDesc"));
					HotpotOrganizationPO org = new HotpotOrganizationPO();
					org.setOrgFullName(desc.getString("fullDepartmentName").replaceAll("_", "/"));
					org.setOrgName(jo.getString("departmentName"));
					org.setId(jo.getString("departmentId"));
					org.setFlag((String)id[1]);
					return org;
				} catch (JSONException e) {
					return null;
				}
			}).collect(Collectors.toList());
			resultDto.setOrgsList(resultList);
		}
		return resultDto;
	}

  @Override
  public void saveOnePartyPorject(PartyProjectPO partyProjectPO) {
    partyporjectRepository.save(partyProjectPO);
  }

}
