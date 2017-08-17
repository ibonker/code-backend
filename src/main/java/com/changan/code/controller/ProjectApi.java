/**
 * 
 */
package com.changan.code.controller;

import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.changan.code.dto.ResultDTO;

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
  
  @ApiOperation(value = "修改项目", notes = "修改项目", response = ResultDTO.class, tags = {"Project"})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultDTO.class),
      @ApiResponse(code = 200, message = "返回错误信息", response = ResultDTO.class)})
  @RequestMapping(value = "/project/{id}/edit", produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> projectEditGet(@NotNull @ApiParam(value = "项目id",
      required = true) @PathVariable(value = "id", required = true) String id);

}
