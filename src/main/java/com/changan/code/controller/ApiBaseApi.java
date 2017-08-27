package com.changan.code.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.dto.ResultOfProjectDTO;
import com.changan.code.entity.ApiBasePO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public interface ApiBaseApi {

	  @ApiOperation(value = "查询所有API", notes = "查询所有API", response = ResultOfProjectDTO.class, tags = {"ApiBase"})
	  @ApiResponses(
	      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfProjectDTO.class),
	          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfProjectDTO.class)})
	  @RequestMapping(value = "/ApiBase/allShow", produces = {"application/json"}, method = RequestMethod.GET)
	  ResponseEntity<ResultDTO> ApiBaseAllShowGet();
	  
	  @ApiOperation(value = "保存API", notes = "保存API", response = ResultOfProjectDTO.class, tags = {"ApiBase"})
	  @ApiResponses(
	      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfProjectDTO.class),
	          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfProjectDTO.class)})
	  @RequestMapping(value = "/ApiBase/Save", produces = {"application/json"}, method = RequestMethod.GET)
	  ResponseEntity<ResultDTO> ApiBaseSaveGet(
			  @ApiParam(value = "id", required = true) @RequestBody ApiBasePO apiBase);
}
