package org.hotpotmaterial.code.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.common.Constants;
import org.hotpotmaterial.code.common.RestStatus;
import org.hotpotmaterial.code.dto.ResultOfApiObjDTO;
import org.hotpotmaterial.code.entity.ApiObjPO;
import org.hotpotmaterial.code.service.IApiObjService;

/**
 * 
 * @author xuyufeng
 *
 */
@Controller
public class ApiObjApiController implements org.hotpotmaterial.code.controller.ApiObjApi {

	/**
	 * 注入apiObjService
	 */
    @Autowired
	IApiObjService apiObjService;

	/**
	 * 保存apiObj
	 */
	@Override
	public ResponseEntity<ResultDTO> apiObjSavePost(@RequestBody ApiObjPO apiObj) {
	  //定义返回值对象
	  ApiObjPO newApiObj;
		// 判断apiObj是否存在
		if (apiObj.isNew()) {
			// 保存apiObj
		  newApiObj = apiObjService.saveApiObj(apiObj);
		} else {
			// 更新apiObj
		  newApiObj = apiObjService.updateApiObj(apiObj);
		}
		return new ResponseEntity<>(
				new ResultOfApiObjDTO().apiObj(newApiObj).message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE),
				HttpStatus.OK);
	}

	/**
	 * 查询指定Api所有的apiObj
	 */
	@Override
	public ResponseEntity<ResultDTO> apiObjAllShowGet(@PathVariable String apiBaseId) {
		// 查询指定Api所有的apiObj
		List<ApiObjPO> apiObjs = apiObjService.findAllApiObj(apiBaseId);
		// 返回成功信息
		return new ResponseEntity<>(
				new ResultOfApiObjDTO().apiObjs(apiObjs).message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE),
				HttpStatus.OK);
	}

	/**
	 * 根据id查询apiObj
	 */
	@Override
	public ResponseEntity<ResultDTO> apiObjFindOneGet(@PathVariable String id) {
		// 根据id查询apiObj
		ApiObjPO apiObj = apiObjService.findOneApiObj(id);
		// 返回成功信息
		return new ResponseEntity<>(
				new ResultOfApiObjDTO().apiObj(apiObj).message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE),
				HttpStatus.OK);
	}

	/**
	 * 根据id删除apiObj
	 */
	@Override
	public ResponseEntity<ResultDTO> apiObjDelete(@PathVariable String id) {
		// 执行删除
		apiObjService.deleteApiObj(id);
		return new ResponseEntity<>(new ResultDTO().message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE),
				HttpStatus.OK);
	}

}
