/**
 * 
 */
package org.hotpotmaterial.code.service;

import java.util.List;

import org.hotpotmaterial.code.dto.ResultOfPartyProjectDTO;
import org.hotpotmaterial.code.entity.PartyProjectPO;
import org.hotpotmaterial.code.security.dto.UserInfo;

public interface IPartyProjectService {
	/**
	 * 添加或修改项目查看权限对象
	 * 
	 * @param partyprojectPO
	 */
	void savePartyProject(List<PartyProjectPO> partyprojectPO);
	
	/**
	 * 保存项目权限
	 * @param partyProjectPO
	 */
	void saveOnePartyPorject(PartyProjectPO partyProjectPO);

	/**
	 * 根据ID删除项目查看权限对象
	 * 
	 * @param partyprojectId
	 */
	void deletePartyProject(String partyprojectId);

	/**
	 * 根据项目Id查询用户信息
	 * 
	 * @param projectId
	 *            醒目ID
	 * @param style
	 *            0:用户 1:组织
	 * @return
	 */
	public ResultOfPartyProjectDTO findUserInfoByProjectId(String projectId, String style,UserInfo user);

}
