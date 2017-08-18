/**
 * 
 */
package com.changan.code.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.changan.code.dto.ResultOfProjectDTO;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author wenxing
 *
 */
@Api(value = "project", description = "the project API")
@RequestMapping(value = "/codegen/api/v1")
public interface ProjectApi {

  @ApiOperation(value = "修改项目", notes = "修改项目", response = JsonSchema.class, tags = {"Project"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = JsonSchema.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = JsonSchema.class)})
  @RequestMapping(value = "/project/example", produces = {"application/schema+json"}, method = RequestMethod.GET)
  ResponseEntity<JsonSchema> jsonSchemaExampleGet();
  
  @ApiOperation(value = "修改项目", notes = "修改项目", response = ResultOfProjectDTO.class, tags = {"Project"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfProjectDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfProjectDTO.class)})
  @RequestMapping(value = "/project/example", produces = {"application/json"}, method = RequestMethod.GET)
  ResponseEntity<ResultOfProjectDTO> projectExampleGet();

}
