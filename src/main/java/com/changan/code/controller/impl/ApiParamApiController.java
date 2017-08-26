package com.changan.code.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.common.Constants;
import com.changan.code.controller.ApiParamApi;
import com.changan.code.dto.ResultOfApiParamDTO;
import com.changan.code.entity.ApiParamPO;
import com.changan.code.service.IApiParamService;

public class ApiParamApiController implements ApiParamApi {

	/**
	 * 注入ApiParam接口
	 */
	@Autowired
	private IApiParamService apiParamService;

	/**
	 * 保存ApiParam
	 */
	@Override
	public ResponseEntity<ResultDTO> ApiObjSave(@RequestBody ApiParamPO apiParam) {
		if (apiParam.isNew()) {
			// 保存ApiParam
			apiParamService.saveApiParam(apiParam);
		} else {
			// 更新ApiParam
			apiParamService.updateApiParam(apiParam);
		}
		// 返回成功信息
		return new ResponseEntity<ResultDTO>(new ResultDTO().message("成功").statusCode(Constants.SUCCESS_API_CODE),
				HttpStatus.OK);
	}

	/**
	 * 删除ApiParam
	 */
	@Override
	public ResponseEntity<ResultDTO> ApiObjDelete(@PathVariable String id) {
		// 删除ApiParam
		apiParamService.deleteApiParam(id);
		// 返回成功信息
		return new ResponseEntity<ResultDTO>(new ResultDTO().message("成功").statusCode(Constants.SUCCESS_API_CODE),
				HttpStatus.OK);
	}

	/**
	 * 查询指定ApiObj的ApiParam
	 */
	@Override
	public ResponseEntity<ResultDTO> ApiObjAllShow(@RequestParam String apiObjId) {
		// 查询指定ApiObj的ApiParam
		List<ApiParamPO> apiParams = apiParamService.findAllApiParam(apiObjId);

		return new ResponseEntity<ResultDTO>(
				new ResultOfApiParamDTO().apiParams(apiParams).message("成功").statusCode(Constants.SUCCESS_API_CODE),
				HttpStatus.OK);
	}

}
