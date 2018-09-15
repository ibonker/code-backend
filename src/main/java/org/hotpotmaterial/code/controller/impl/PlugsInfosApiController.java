package org.hotpotmaterial.code.controller.impl;

import org.hotpotmaterial.anywhere.common.mvc.page.rest.request.PageDTO;
import org.hotpotmaterial.anywhere.common.mvc.page.rest.response.ResultPageDTO;
import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.common.RestStatus;
import org.hotpotmaterial.code.controller.PlugsInfosApi;
import org.hotpotmaterial.code.dto.ResultOfPlugsInfoDTO;
import org.hotpotmaterial.code.entity.PlugsInfoPO;
import org.hotpotmaterial.code.service.IPlugsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 控制器实现类
 * 
 * @author Hotpotmaterial-Code2
 */
@Controller
public class PlugsInfosApiController extends BaseController implements PlugsInfosApi {

	// 注入业务bean - IPlugsInfoService
	@Autowired
	private IPlugsInfoService plugsInfoService;

	/**
	 * 实体new.titancode.test.PlugsInfoPO新增
	 */
	@Override
	public ResponseEntity<ResultDTO> plugsInfosPost(@RequestBody(required = true) PlugsInfoPO plugsInfo) {
		plugsInfoService.insertPlugsInfo(plugsInfo);
		return new ResponseEntity<ResultDTO>(new ResultOfPlugsInfoDTO().message(RestStatus.RESULT_SUCCESS.message())
				.statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
	}

	/**
	 * 实体new.titancode.test.PlugsInfoPO删除
	 */
	@Override
	public ResponseEntity<ResultDTO> plugsInfosDelete(@PathVariable(required = true) String plugsInfoId) {
		plugsInfoService.deleteById(plugsInfoId);
		return new ResponseEntity<ResultDTO>(new ResultDTO().message(RestStatus.RESULT_SUCCESS.message())
				.statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
	}

	/**
	 * 实体new.titancode.test.PlugsInfoPO更新
	 */
	@Override
	public ResponseEntity<ResultDTO> plugsInfosPut(@PathVariable(required = true) String plugsInfoId,
			@RequestBody(required = true) PlugsInfoPO plugsInfo) {
		plugsInfoService.updatePlugsInfo(plugsInfoId, plugsInfo);
		return new ResponseEntity<ResultDTO>(new ResultOfPlugsInfoDTO().message(RestStatus.RESULT_SUCCESS.message())
				.statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
	}

	/**
	 * 实体new.titancode.test.PlugsInfoPO详情
	 */
	@Override
	public ResponseEntity<ResultDTO> plugsInfosGET(@PathVariable(required = true) String plugsInfoId) {
		return new ResponseEntity<ResultDTO>(
				new ResultOfPlugsInfoDTO().plugsInfo(plugsInfoService.findById(plugsInfoId))
						.message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
				HttpStatus.OK);
	}

	/**
	 * 实体new.titancode.test.PlugsInfoPO分页列表
	 */
	@Override
	public ResponseEntity<ResultDTO> plugsInfosPagesPost(@RequestBody(required = true) PageDTO searchParams) {
		Page<PlugsInfoPO> result = plugsInfoService.getPlugsInfoList(searchParams);
		return new ResponseEntity<>(
				new ResultPageDTO<PlugsInfoPO>().totalElements(result.getTotalElements()).pageNumber(result.getNumber())
						.pageSize(result.getSize()).data(result.getContent())
						.message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
				HttpStatus.OK);
	}

}