package com.changan.code.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.dto.ResultOfProjectDTO;
import com.changan.code.entity.ApiObjPO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @author xuyufeng
 *
 */
public interface ApiObjApi {

	  @ApiOperation(value = "保存apiObj", notes = "保存apiObj", response = ResultOfProjectDTO.class, tags = {"ApiObj"})
	  @ApiResponses(
	      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfProjectDTO.class),
	          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfProjectDTO.class)})
	  @RequestMapping(value = "/ApiObj/save", produces = {"application/json"}, method = RequestMethod.POST)
	  ResponseEntity<ResultDTO> ApiObjSave(@RequestBody ApiObjPO apiObj);
	  
	  @ApiOperation(value = "查询指定api的所有apiObj", notes = "查询指定api的所有apiObj", response = ResultOfProjectDTO.class, tags = {"ApiObj"})
	  @ApiResponses(
	      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfProjectDTO.class),
	          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfProjectDTO.class)})
	  @RequestMapping(value = "/ApiObj/allShow", produces = {"application/json"}, method = RequestMethod.GET)
	  ResponseEntity<ResultDTO> ApiObjAllShowGet(@ApiParam(value = "id") @RequestParam(value = "id", required = true) String id);
	  
	  @ApiOperation(value = "根据id查询apiObj", notes = "根据id查询apiObj", response = ResultOfProjectDTO.class, tags = {"ApiObj"})
	  @ApiResponses(
	      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfProjectDTO.class),
	          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfProjectDTO.class)})
	  @RequestMapping(value = "/ApiObj/{id}/FindOne", produces = {"application/json"}, method = RequestMethod.GET)
	  ResponseEntity<ResultDTO> ApiObjFindOneGet(@ApiParam(value = "id") @RequestParam(value = "id", required = true) String id);
}
