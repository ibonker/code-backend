package org.hotpotmaterial.code.service;

import java.util.List;

import org.hotpotmaterial.code.entity.LoginInfoPO;

public interface ILoginInfoService {

	/**
	 * 保存或修改版本信息
	 * 
	 * @param apiViewPO
	 * @return
	 */
	public void saveLoginInfo(LoginInfoPO loginInfoPO);
	
	/**
	 * 根据版本ID删除对应的版本信息
	 * @param loginInfoId
	 */
	public void deleteLoginInfo(String loginInfoId);
	
	
}
