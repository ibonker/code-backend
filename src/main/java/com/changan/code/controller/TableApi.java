/**
 * 
 */
package com.changan.code.controller;

import java.io.FileNotFoundException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.dto.RequestOfTableIdsDTO;
import com.changan.code.dto.ResultOfColumnsDTO;
import com.changan.code.dto.ResultOfMsgDataDTO;
import com.changan.code.dto.ResultOfTransferObjDTO;
import com.changan.code.entity.TablePO;
import com.changan.code.entity.TableRelationPO;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author wenxing
 *
 */
@Api(value = "project", description = "the project API")
@RequestMapping(value = "/codegen/api/v1")
public interface TableApi {

  @ApiOperation(value = "表列表schema", notes = "表列表schema", response = JsonSchema.class,
      tags = {"Table"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = JsonSchema.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = JsonSchema.class)})
  @RequestMapping(value = "/tables", produces = {"application/schema+json"},
      method = RequestMethod.GET)
  ResponseEntity<JsonSchema> tablesSchemaGet();

  /**
   * 数据源表分页
   */
  @ApiOperation(value = "表字段", notes = "表字段", response = ResultOfColumnsDTO.class, tags = {"Table"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfColumnsDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfColumnsDTO.class)})
  @RequestMapping(value = "/tables/{id}/columns", produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> tablesColumnsGet(
      @ApiParam(value = "表id", required = false) @PathVariable String id);

  /**
   * 数据源表分页
   */
  @ApiOperation(value = "更新表字段", notes = "更新表字段", response = ResultDTO.class, tags = {"Table"})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultDTO.class),
      @ApiResponse(code = 200, message = "返回错误信息", response = ResultDTO.class)})
  @RequestMapping(value = "/tables/{id}/save", produces = {"application/json"},
      method = RequestMethod.PUT)
  ResponseEntity<ResultDTO> tablesSavePut(
      @ApiParam(value = "表id", required = false) @PathVariable String id,
      @ApiParam(value = "表对象", required = false) @RequestBody TablePO table);

  /**
   * 数据源表分页
   */
  @ApiOperation(value = "表字段实体详情", notes = "表字段实体详情", response = ResultOfTransferObjDTO.class,
      tags = {"Table"})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfTransferObjDTO.class),
      @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfTransferObjDTO.class)})
  @RequestMapping(value = "/tables/{id}/dto", produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> tablesDtoGet(
      @ApiParam(value = "表id", required = false) @PathVariable String id);

  /**
   * 更改表自动生成crud接口状态
   * 
   * @param id
   * @return
   */
  @ApiOperation(value = "更改表自动生成crud接口状态", notes = "更改表自动生成crud接口状态", response = ResultDTO.class,
      tags = {"Table"})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultDTO.class),
      @ApiResponse(code = 200, message = "返回错误信息", response = ResultDTO.class)})
  @RequestMapping(value = "/tables/autocrud/change/{status}", produces = {"application/json"},
      method = RequestMethod.PUT)
  ResponseEntity<ResultDTO> tablesAutocrudChangeGet(
      @ApiParam(value = "状态", allowableValues = "active,inactive",
          required = true) @PathVariable String status,
      @ApiParam(value = "表id列表", required = false) @RequestBody RequestOfTableIdsDTO tableIds);

  @ApiOperation(value = "生成项目实体代码", notes = "生成项目实体代码", response = ResultOfMsgDataDTO.class,
      tags = {"Table"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfMsgDataDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfMsgDataDTO.class)})
  @RequestMapping(value = "/tables/{tableId}/generate/code", produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> columnsGenerateCodeGet(
      @ApiParam(value = "tableId", required = true) @PathVariable String tableId);

  @ApiOperation(value = "下载项目代码", notes = "下载项目代码", response = InputStreamResource.class,
      tags = {"Table"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = InputStreamResource.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = InputStreamResource.class)})
  @RequestMapping(value = "/tables/download",
      produces = {"application/octet-stream"}, method = RequestMethod.GET)
  ResponseEntity<InputStreamResource> projectsDownloadGet(
      @ApiParam(value = "tableId", required = true) @RequestParam String tableId)
      throws FileNotFoundException;
  
  /**
   * 新增restFul关联表
   */
  @ApiOperation(value = "新增restFul关联表", notes = "新增restFul关联表", response = ResultDTO.class, tags = {"Table"})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultDTO.class),
      @ApiResponse(code = 200, message = "返回错误信息", response = ResultDTO.class)})
  @RequestMapping(value = "/tables/relations/save", produces = {"application/json"},
      method = RequestMethod.PUT)
  ResponseEntity<ResultDTO> tablesRelationSavePut(
      @ApiParam(value = "表关联关系对象", required = false) @RequestBody TableRelationPO tableRelation);
  
  /**
   * 根据id删除tableRelation
   * @param id
   * @return
   */
  @ApiOperation(value = "根据id删除tableRelation", notes = "根据id删除tableRelation", response = ResultDTO.class,
      tags = {"Table"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultDTO.class)})
  @RequestMapping(value = "/tables/relations/{id}/delete", produces = {"application/json"},
      method = RequestMethod.DELETE)
  ResponseEntity<ResultDTO> deletTableRelation(
      @ApiParam(value = "id") @PathVariable(value = "id", required = true) String id);
  
  /**
   * 根据id获取所有关联关系表
   * @param id
   * @return
   */
  @ApiOperation(value = "根据id获取tableRelations", notes = "根据id获取tableRelations", response = ResultDTO.class,tags = {"Table"})
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "返回操作成功信息", response = ResultDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultDTO.class)})
  @RequestMapping(value = "/tables/{id}/relations", produces = {"application/json"},method = RequestMethod.GET)
  ResponseEntity<ResultDTO> tableRelationList(@ApiParam(value = "id", required = true) @PathVariable(name="id") String id);
}
