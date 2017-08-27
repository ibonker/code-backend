package com.changan.code.controller.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.common.Constants;
import com.changan.code.dto.ResultOfApiObjDTO;
import com.changan.code.entity.ApiObjPO;
import com.changan.code.service.IApiObjService;

/**
 * 
 * @author xuyufeng
 *
 */
@Controller
public class ApiObjApiController implements com.changan.code.controller.ApiObjApi {

	/**
	 * 注入apiObjService
	 */
	IApiObjService apiObjService;

	/**
	 * 保存apiObj
	 */
	@Override
	public ResponseEntity<ResultDTO> ApiObjSave(@RequestBody ApiObjPO apiObj) {
		// 判断apiObj是否存在
		if (apiObj.isNew()) {
			// 保存apiObj
			apiObjService.saveApiObj(apiObj);
		} else {
			// 更新apiObj
			apiObjService.updateApiObj(apiObj);
		}
		return new ResponseEntity<ResultDTO>(new ResultDTO().message("成功").statusCode(Constants.SUCCESS_API_CODE),
				HttpStatus.OK);
	}

	/**
	 * 查询指定Api所有的apiObj
	 */
	@Override
	public ResponseEntity<ResultDTO> ApiObjAllShowGet(String id) {
		// 查询指定Api所有的apiObj
		List<ApiObjPO> apiObjs = apiObjService.findAllApiObj(id);
		//返回成功信息
		return new ResponseEntity<ResultDTO>(
				new ResultOfApiObjDTO().apiObjs(apiObjs).message("成功").statusCode(Constants.SUCCESS_API_CODE),
				HttpStatus.OK);
	}

	/**
	 * 根据id查询apiObj
	 */
	@Override
	public ResponseEntity<ResultDTO> ApiObjFindOneGet(String id) {
		// 根据id查询apiObj
		ApiObjPO apiObj = apiObjService.findOneApiObj(id);
		//返回成功信息
		return new ResponseEntity<ResultDTO>(
				new ResultOfApiObjDTO().apiObj(apiObj).message("成功").statusCode(Constants.SUCCESS_API_CODE),
				HttpStatus.OK);
	}

}
