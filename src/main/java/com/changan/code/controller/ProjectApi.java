/**
 * 
 */
package com.changan.code.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.changan.anywhere.common.mvc.page.rest.request.PageDTO;
import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.anywhere.common.mvc.page.rest.response.ResultPageDTO;
import com.changan.code.dto.ResultOfComponentsDTO;
import com.changan.code.dto.ResultOfProjectDTO;
import com.changan.code.dto.ResultOfTypeDTO;
import com.changan.code.entity.ProjectPO;
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
public interface ProjectApi {

  @ApiOperation(value = "项目列表schema", notes = "项目列表schema", response = JsonSchema.class,
      tags = {"Project"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = JsonSchema.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = JsonSchema.class)})
  @RequestMapping(value = "/projects", produces = {"application/schema+json"},
      method = RequestMethod.GET)
  ResponseEntity<JsonSchema> projectsSchemaGet();

  @ApiOperation(value = "分页获取项目列表", notes = "分页获取项目列表", response = ResultPageDTO.class,
      tags = {"Project"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultPageDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultPageDTO.class)})
  @RequestMapping(value = "/projects", produces = {"application/json"}, method = RequestMethod.POST)
  ResponseEntity<ResultDTO> projectsGet(
      @ApiParam(value = "查询参数", required = false) @RequestBody PageDTO searchParams);

  @ApiOperation(value = "获取项目", notes = "获取项目", response = ResultOfProjectDTO.class,
      tags = {"Project"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfProjectDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfProjectDTO.class)})
  @RequestMapping(value = "/projects/{id}/show", produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> projectsShowGet(
      @ApiParam(value = "id", required = true) @PathVariable String id);

  @ApiOperation(value = "保存项目", notes = "保存项目", response = ResultDTO.class, tags = {"Project"})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultDTO.class),
      @ApiResponse(code = 200, message = "返回错误信息", response = ResultDTO.class)})
  @RequestMapping(value = "/project/save", produces = {"application/json"},
      method = RequestMethod.POST)
  ResponseEntity<ResultDTO> projectSavePost(@RequestBody ProjectPO project);

  @ApiOperation(value = "项目实体", notes = "项目实体", response = ResultOfTypeDTO.class,
      tags = {"Project"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfTypeDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfTypeDTO.class)})
  @RequestMapping(value = "/project/{id}/dto_po", produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> projectDtoPoGet(
      @ApiParam(value = "id", required = true) @PathVariable String id);

  @ApiOperation(value = "项目实体以及基本类型", notes = "项目实体以及基本类型", response = ResultOfTypeDTO.class,
      tags = {"Project"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfTypeDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfTypeDTO.class)})
  @RequestMapping(value = "/project/{id}/data_type", produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> projectDataTypeGet(
      @ApiParam(value = "id", required = true) @PathVariable String id);
  
  @ApiOperation(value = "项目组件列表", notes = "项目组件列表", response = ResultOfComponentsDTO.class,
      tags = {"Project"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfComponentsDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfComponentsDTO.class)})
  @RequestMapping(value = "/projects/components/default", produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> projectsComponentsDefaultGet();

}
