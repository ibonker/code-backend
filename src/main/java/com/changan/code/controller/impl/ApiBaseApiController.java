package com.changan.code.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.common.Constants;
import com.changan.code.controller.ApiBaseApi;
import com.changan.code.dto.ResultOfApiBaseDTO;
import com.changan.code.entity.ApiBasePO;
import com.changan.code.service.IApiBaseService;

/**
 * API controller
 * 
 * @author xuyufeng
 *
 */
@Controller
public class ApiBaseApiController implements ApiBaseApi {

	/**
	 * 注入api 接口
	 */
	@Autowired
	IApiBaseService apiBaseService;

	/**
	 * 查询所有api
	 */
	@Override
	public ResponseEntity<ResultDTO> ApiBaseAllShowGet(@RequestParam String projectId) {
		// 获得所有的api
		List<ApiBasePO> allApiBases = apiBaseService.findAllApiBase(projectId);
		// 返回成功信息
		return new ResponseEntity<ResultDTO>(
				new ResultOfApiBaseDTO().apiBases(allApiBases).message("成功").statusCode(Constants.SUCCESS_API_CODE),
				HttpStatus.OK);
	}
	/**
	 * 保存api/修改api,当api版本号相同时操作失败
	 */
	@Override
	public ResponseEntity<ResultDTO> ApiBaseSaveGet(@RequestBody ApiBasePO apiBase) {
		ApiBasePO newApiBase =  new ApiBasePO();
		if(apiBase.isNew()){
			// 保存api
			newApiBase = apiBaseService.saveApiBase(apiBase);
		}else{
			// 更新api
			newApiBase = apiBaseService.updateApiBase(apiBase);
		}
		// 返回成功信息
		return new ResponseEntity<ResultDTO>(
				new ResultOfApiBaseDTO().apiBase(newApiBase).message("成功").statusCode(Constants.SUCCESS_API_CODE),
				HttpStatus.OK);
	}
}
