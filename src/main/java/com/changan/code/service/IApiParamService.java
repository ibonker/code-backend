package com.changan.code.service;

import java.util.List;

import com.changan.code.entity.ApiParamPO;

public interface IApiParamService {
	
	/**
	 * 更新Api方法参数
	 * @param apiParam
	 */
	public void updateApiParam(ApiParamPO apiParam);
	
	/**
	 * 新增Api方法参数
	 * @param apiParam
	 */
	public void saveApiParam(ApiParamPO apiParam);
	
	/**
	 * 删除Api方法参数
	 * @param id
	 */
	public void deleteApiParam(String id);
	
	/**
	 * 查询所有Api参数
	 * @return
	 */
	public List<ApiParamPO> findAllApiParam(String apiObjId);
}
