package org.hotpotmaterial.code.service;

import java.util.List;

import org.hotpotmaterial.code.entity.VersionPO;

public interface IVersionService {

	/**
	 * 保存或修改版本信息
	 * 
	 * @param apiViewPO
	 * @return
	 */
	public void saveVersion(VersionPO versionPO);
	
	/**
	 * 根据版本ID删除对应的版本信息
	 * @param versionId
	 */
	public void deleteVersion(String versionId);
	
	/**
	 * 根据版本的类型,查询对应的版本信息
	 * @param flag   0:前端  1:后端
	 * @return
	 */
	public List<VersionPO> findByFlag(String flag);

}
