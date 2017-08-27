package com.changan.code.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changan.code.entity.ApiParamPO;
import com.changan.code.exception.CodeCommonException;
import com.changan.code.repository.ApiParamRepository;
import com.changan.code.service.IApiParamService;

@Service
public class ApiParamServiceImpl implements IApiParamService{

	/**
	 * 注入ApiParam Repository
	 */
	@Autowired
	private ApiParamRepository apiParamRepo;
	
	/**
	 * 更新Api参数
	 */
	@Override
	public void updateApiParam(ApiParamPO apiParam) {
		//查询Api参数是否存在
		ApiParamPO updateApiParam = apiParamRepo.findOne(apiParam.getId());
		if(updateApiParam != null){
			//更新属性
			updateApiParam.updateAttrs(apiParam);
			//保存Api参数
			this.saveApiParam(updateApiParam);
		}else{
			throw new CodeCommonException("更新失败！");
		}
		
	}

	/**
	 * 保存Api参数
	 */
	@Override
	public void saveApiParam(ApiParamPO apiParam) {
		//重复数
		int sameCount = 0;
		//查询重复数
		sameCount = apiParamRepo.countApiParamByName(apiParam.getName(), apiParam.getApiObjId());
		if(sameCount != 0){
			//保存Api参数
			apiParamRepo.save(apiParam);
		}else{
			throw new CodeCommonException("保存失败！");
		}
	}

	/**
	 * 逻辑删除Api参数
	 */
	@Override
	public void deleteApiParam(String id) {
		//查询数据是否存在
		ApiParamPO deleteApiParams = apiParamRepo.findOne(id);
		if(deleteApiParams != null){
			//执行逻辑删除
			deleteApiParams.setDelFlag("1");
			//保存Api参数信息
			this.updateApiParam(deleteApiParams);
		}else{
			throw new CodeCommonException("删除失败！");
		}
	}

	/**
	 * 查询指定ApiObj所有Api参数
	 */
	@Override
	public List<ApiParamPO> findAllApiParam(String apiObjId) {
		//获取所有Api参数
		List<ApiParamPO> apiParams = apiParamRepo.findByApiObjId(apiObjId);
		//返回结果
		return apiParams;
	}

}
