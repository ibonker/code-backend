package org.hotpotmaterial.code.controller;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.dto.ResultOfColumnsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "project", description = "the project API")
@RequestMapping(value = "/codegen/api/v1")
public interface PreviewApi {
  
  /**
   * 预览代码
   */
  @ApiOperation(value = "预览", notes = "预览", response = ResultOfColumnsDTO.class, tags = {"Preview"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfColumnsDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfColumnsDTO.class)})
  @RequestMapping(value = "/preview/{id}", produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> previewDTO(
      @ApiParam(value = "表id", required = false) @PathVariable String id);
  
}
