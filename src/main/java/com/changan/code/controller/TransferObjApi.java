package com.changan.code.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.dto.ResultOfTransferObjDTO;
import com.changan.code.entity.TransferObjPO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * TransferObj API
 * 
 * @author xuyufeng
 *
 */
@Api(value = "transferObj", description = "the TransferObj API")
@RequestMapping(value = "/codegen/api/v1")
public interface TransferObjApi {

	@ApiOperation(value = "根据id获取DTO", notes = "根据id获取DTO", response = ResultOfTransferObjDTO.class, tags = { "TransferObj" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfTransferObjDTO.class),
			@ApiResponse(code = 200, message = "返回错误信息", response = ResultOfTransferObjDTO.class) })
	@RequestMapping(value = "/transferObj/show", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<ResultDTO> transferObjShowGet(
			@ApiParam(value = "id") @RequestParam(value = "id", required = true) String id);

	@ApiOperation(value = "保存DTO", notes = "保存DTO", response = ResultOfTransferObjDTO.class, tags = { "TransferObj" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfTransferObjDTO.class),
			@ApiResponse(code = 200, message = "返回错误信息", response = ResultOfTransferObjDTO.class) })
	@RequestMapping(value = "/transferObj/save", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<ResultDTO> transferObjSavePost(@RequestBody TransferObjPO transferObj);

	@ApiOperation(value = "删除DTO", notes = "删除DTO", response = ResultOfTransferObjDTO.class, tags = { "TransferObj" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfTransferObjDTO.class),
			@ApiResponse(code = 200, message = "返回错误信息", response = ResultOfTransferObjDTO.class) })
	@RequestMapping(value = "/transferObj/{id}/delete", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<ResultDTO> transferObjDeletePost(@ApiParam(value = "id", required = true) @PathVariable String id);
	
	@ApiOperation(value = "查询所有项目下所有DTO", notes = "查询所有项目下所有DTO", response = ResultOfTransferObjDTO.class, tags = { "TransferObj" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfTransferObjDTO.class),
			@ApiResponse(code = 200, message = "返回错误信息", response = ResultOfTransferObjDTO.class) })
	@RequestMapping(value = "/transferObj/allShow", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<ResultDTO> transferObjallShowGet(
			@ApiParam(value = "projectId") @RequestParam(value = "projectId", required = true) String id);
}
