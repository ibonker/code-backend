package com.changan.code.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.dto.ResultOfProjectDTO;
import com.changan.code.entity.ApiParamPO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @author xuyufeng
 *
 */
public interface ApiParamApi {


	  @ApiOperation(value = "保存apiParam", notes = "保存apiParam", response = ResultOfProjectDTO.class, tags = {"ApiParam"})
	  @ApiResponses(
	      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfProjectDTO.class),
	          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfProjectDTO.class)})
	  @RequestMapping(value = "/ApiParam/save", produces = {"application/json"}, method = RequestMethod.POST)
	  ResponseEntity<ResultDTO> ApiObjSave(@RequestBody ApiParamPO apiParam);
	  
	  @ApiOperation(value = "删除apiParam", notes = "删除apiParam", response = ResultOfProjectDTO.class, tags = {"ApiParam"})
	  @ApiResponses(
	      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfProjectDTO.class),
	          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfProjectDTO.class)})
	  @RequestMapping(value = "/ApiParam/{id}/delete", produces = {"application/json"}, method = RequestMethod.GET)
	  ResponseEntity<ResultDTO> ApiObjDelete(@ApiParam(value = "id", required = true) @PathVariable String id);
	  
	  @ApiOperation(value = "查询指定apiObj所有的apiParam", notes = "查询指定apiObj所有的apiParam", response = ResultOfProjectDTO.class, tags = {"ApiParam"})
	  @ApiResponses(
	      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfProjectDTO.class),
	          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfProjectDTO.class)})
	  @RequestMapping(value = "/ApiParam/allShow", produces = {"application/json"}, method = RequestMethod.GET)
	  ResponseEntity<ResultDTO> ApiObjAllShow(@ApiParam(value = "id") @RequestParam(value = "id", required = true) String id);
}
