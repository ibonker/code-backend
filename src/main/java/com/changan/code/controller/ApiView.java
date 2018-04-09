/**
 * 
 */
package com.changan.code.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.dto.RequestApiViewConfigDTO;
import com.changan.code.dto.ResultOfApiViewDTO;
import com.changan.code.entity.ApiViewPO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author cyj
 *
 */
@Api(value = "ViewConfig", description = "the project API")
@RequestMapping(value = "/codegen/api/v1")
public interface ApiView {

  @ApiOperation(value = "页面配置目录列表", notes = "页面配置目录列表", response = ResultOfApiViewDTO.class,tags = {"ViewConfig"})
  @ApiResponses(value = {
		  @ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfApiViewDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfApiViewDTO.class)})
  @RequestMapping(value = "/viewconfig/{id}/show", produces = {"application/json"},method = RequestMethod.GET)
  ResponseEntity<ResultDTO> viewConfigList(@ApiParam(value = "id", required = true) @PathVariable(name="id") String projectId);

  
  @ApiOperation(value = "各个表对应的Api", notes = "各个表对应的Api", response = ResultOfApiViewDTO.class,tags = {"ViewConfig"})
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfApiViewDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfApiViewDTO.class)})
  @RequestMapping(value = "/viewconfig/{id}/dtoApiList", produces = {"application/json"},method = RequestMethod.GET)
  ResponseEntity<ResultDTO> dtoApiList(@ApiParam(value = "id", required = true) @PathVariable(name="id") String projectId);
  
  @ApiOperation(value = "Api页面表格配置列表", notes = "Api页面表格配置列表", response = ResultOfApiViewDTO.class,tags = {"ViewConfig"})
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfApiViewDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfApiViewDTO.class)})
  @RequestMapping(value = "/viewconfig/{id}/tableConfigs", produces = {"application/json"},method = RequestMethod.GET)
  ResponseEntity<ResultDTO> tableConfigs(@ApiParam(value = "id", required = true) @PathVariable(name="id") String tableId);
  
  @ApiOperation(value = "Api页面表单配置列表", notes = "Api页面表单配置列表", response = ResultOfApiViewDTO.class,tags = {"ViewConfig"})
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfApiViewDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfApiViewDTO.class)})
  @RequestMapping(value = "/viewconfig/{id}/formConfigs", produces = {"application/json"},method = RequestMethod.GET)
  ResponseEntity<ResultDTO> formConfigs(@ApiParam(value = "id", required = true) @PathVariable(name="id") String tableId);
  
  @ApiOperation(value = "保存Api页面配置", notes = "保存Api页面配置", response = ResultDTO.class,tags = {"ViewConfig"})
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "返回操作成功信息", response = ResultDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultDTO.class)})
  @RequestMapping(value = "/viewconfig/save", produces = {"application/json"},method = RequestMethod.POST)
  ResponseEntity<ResultDTO> apiViewSavePost(@ApiParam(value = "apiView对象") @RequestBody ApiViewPO apiViewPO);
  
  @ApiOperation(value = "保存Api表单、表格配置", notes = "保存Api表单、表格配置", response = ResultDTO.class,tags = {"ViewConfig"})
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "返回操作成功信息", response = ResultDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultDTO.class)})
  @RequestMapping(value = "/viewconfig/saveAllConfigs", produces = {"application/json"},method = RequestMethod.POST)
  ResponseEntity<ResultDTO> apiViewConfigSavePost(@ApiParam(value = "apiViewConfig对象") @RequestBody RequestApiViewConfigDTO requestApiViewConfigDTO);
}
