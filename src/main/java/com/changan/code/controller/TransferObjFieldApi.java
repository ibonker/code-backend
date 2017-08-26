package com.changan.code.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.dto.ResultOfTransferObjFieldDTO;
import com.changan.code.entity.TransferObjFieldPO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * TransferObjField Api
 * @author xuyufeng
 *
 */
@Api(value = "transferObjField", description = "the TransferObjField API")
@RequestMapping(value = "/codegen/api/v1")
public interface TransferObjFieldApi {

	  @ApiOperation(value = "获取DTO属性", notes = "获取DTO属性", response = ResultOfTransferObjFieldDTO.class, tags = {"TransferObjField"})
	  @ApiResponses(
	      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfTransferObjFieldDTO.class),
	          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfTransferObjFieldDTO.class)})
	  @RequestMapping(value = "/transferObjField/show", produces = {"application/json"}, method = RequestMethod.GET)
	  ResponseEntity<ResultDTO> transferObjFieldShowGet(
			  @ApiParam(value = "id") @RequestParam(value = "id", required = true) String id);
	  
	  @ApiOperation(value = "删除DTO属性", notes = "删除DTO属性", response = ResultOfTransferObjFieldDTO.class, tags = {"TransferObjField"})
	  @ApiResponses(
	      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfTransferObjFieldDTO.class),
	          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfTransferObjFieldDTO.class)})
	  @RequestMapping(value = "/transferObjField/{id}/delete", produces = {"application/json"}, method = RequestMethod.POST)
	  ResponseEntity<ResultDTO> transferObjFieldDeleteGet(
	      @ApiParam(value = "id", required = true) @PathVariable String id);
	  
	  @ApiOperation(value = "保存DTO属性", notes = "保存DTO属性", response = ResultOfTransferObjFieldDTO.class, tags = {"TransferObjField"})
	  @ApiResponses(
	      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfTransferObjFieldDTO.class),
	          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfTransferObjFieldDTO.class)})
	  @RequestMapping(value = "/transferObjField/save", produces = {"application/json"}, method = RequestMethod.POST)
	  ResponseEntity<ResultDTO> transferObjFieldSavePost(@RequestBody TransferObjFieldPO transferObjField);
	  
	  @ApiOperation(value = "查询所有DTO属性", notes = "查询所有DTO属性", response = ResultOfTransferObjFieldDTO.class, tags = {"TransferObjField"})
	  @ApiResponses(
	      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfTransferObjFieldDTO.class),
	          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfTransferObjFieldDTO.class)})
	  @RequestMapping(value = "/transferObjField/allShow", produces = {"application/json"}, method = RequestMethod.GET)
	  ResponseEntity<ResultDTO> transferObjFieldAllShowGet();
	  
	  @ApiOperation(value = "批量添加DTO属性", notes = "批量添加DTO属性", response = ResultOfTransferObjFieldDTO.class, tags = {"TransferObjField"})
	  @ApiResponses(
	      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfTransferObjFieldDTO.class),
	          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfTransferObjFieldDTO.class)})
	  @RequestMapping(value = "/transferObjField/saveAll", produces = {"application/json"}, method = RequestMethod.POST)
	  ResponseEntity<ResultDTO> transferObjFielSaveAllPost(@RequestBody List<TransferObjFieldPO> transferObjFields);
}
