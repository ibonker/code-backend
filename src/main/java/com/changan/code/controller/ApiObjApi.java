package com.changan.code.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.changan.anywhere.common.mvc.rest.basic.ResultDTO;
import com.changan.code.dto.ResultOfApiObjDTO;
import com.changan.code.entity.ApiObjPO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * ApiObj RESTful接口
 * @author xuyufeng
 *
 */
@Api(value = "apiObj", description = "the apiObj API")
@RequestMapping(value = "/codegen/api/v1")
public interface ApiObjApi {

  /**
   * 保存ApiObj
   * @param apiObj
   * @return
   */
  @ApiOperation(value = "保存apiObj", notes = "保存apiObj", response = ResultOfApiObjDTO.class,
      tags = {"ApiObj"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfApiObjDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfApiObjDTO.class)})
  @RequestMapping(value = "/apiobj/save", produces = {"application/json"},
      method = RequestMethod.POST)
  ResponseEntity<ResultDTO> apiObjSavePost(
      @ApiParam(value = "Api方法", required = true) @RequestBody ApiObjPO apiObj);

  /**
   * 查询指定api下所有的apiObj
   * @param apiBaseId
   * @return
   */
  @ApiOperation(value = "查询指定api的所有apiObj", notes = "查询指定api的所有apiObj",
      response = ResultOfApiObjDTO.class, tags = {"ApiObj"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfApiObjDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfApiObjDTO.class)})
  @RequestMapping(value = "/apiobjs/{apiBaseId}/show", produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> apiObjAllShowGet(
      @ApiParam(value = "apiBaseId") @PathVariable(value = "apiBaseId", required = true) String apiBaseId);

  /**
   * 根据id查询apiObj
   * @param id
   * @return
   */
  @ApiOperation(value = "根据id查询apiObj", notes = "根据id查询apiObj", response = ResultOfApiObjDTO.class,
      tags = {"ApiObj"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfApiObjDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfApiObjDTO.class)})
  @RequestMapping(value = "/apiobj/{id}/find", produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> apiObjFindOneGet(
      @ApiParam(value = "id") @PathVariable(value = "id", required = true) String id);

  /**
   * 根据id删除apiObj
   * @param id
   * @return
   */
  @ApiOperation(value = "根据id删除apiObj", notes = "根据id删除apiObj", response = ResultDTO.class,
      tags = {"ApiObj"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultDTO.class)})
  @RequestMapping(value = "/apiobj/{id}/delete", produces = {"application/json"},
      method = RequestMethod.DELETE)
  ResponseEntity<ResultDTO> apiObjDelete(
      @ApiParam(value = "id") @PathVariable(value = "id", required = true) String id);
}
