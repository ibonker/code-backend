package com.changan.code.service;

import java.util.List;

import com.changan.code.entity.ApiBasePO;

/**
 * API 接口
 * @author xuyufeng
 *
 */
public interface IApiBaseService {
	
	/**
	 * 保存API
	 * @param apiBase
	 */
	public void saveApiBase(ApiBasePO apiBase);
	
	/**
	 * 更新API
	 * @param apiBase
	 */
	public void updateApiBase(ApiBasePO apiBase);
	
	/**
	 * 查询所有的API
	 * @return
	 */
	public List<ApiBasePO> findAllApiBase();
	
	/**
	 * 查找出项目最早的有效的api base
	 */
	public ApiBasePO findEarlestApiBaseByProjectId(String projectId);
	
	/**
	 * 创建默认api base
	 * @param projectId
	 * @return
	 */
	public ApiBasePO createDefaultApiBase(String projectId, String packageName);

}
