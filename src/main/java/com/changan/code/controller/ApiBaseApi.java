package com.changan.code.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.dto.ResultOfApiBaseDTO;
import com.changan.code.entity.ApiBasePO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "apiBase", description = "the apiBase API")
@RequestMapping(value = "/codegen/api/v1")
public interface ApiBaseApi {

  @ApiOperation(value = "查询所有API", notes = "查询所有API", response = ResultOfApiBaseDTO.class,
      tags = {"ApiBase"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfApiBaseDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfApiBaseDTO.class)})
  @RequestMapping(value = "/apibases/{projectId}/show", produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> apiBaseAllShowGet(
      @ApiParam(value = "projectId") @PathVariable(value = "projectId", required = true) String id);

  @ApiOperation(value = "保存API", notes = "保存API", response = ResultOfApiBaseDTO.class,
      tags = {"ApiBase"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfApiBaseDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfApiBaseDTO.class)})
  @RequestMapping(value = "/apibase/save", produces = {"application/json"},
      method = RequestMethod.POST)
  ResponseEntity<ResultDTO> apiBaseSavePost(
      @ApiParam(value = "Api对象", required = true) @RequestBody ApiBasePO apiBase);

  @ApiOperation(value = "根据id删除API", notes = "根据id删除API", response = ResultOfApiBaseDTO.class,
      tags = {"ApiBase"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfApiBaseDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfApiBaseDTO.class)})
  @RequestMapping(value = "/apibase/{id}/delete", produces = {"application/json"},
      method = RequestMethod.DELETE)
  ResponseEntity<ResultDTO> apiBaseDelete(
      @ApiParam(value = "id", required = true) @PathVariable String id);
}
