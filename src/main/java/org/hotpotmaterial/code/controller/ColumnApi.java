/**
 * 
 */
package org.hotpotmaterial.code.controller;

import java.util.List;

import org.hotpotmaterial.anywhere.common.mvc.page.rest.response.ResultPageDTO;
import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.dto.ResultOfColumnDTO;
import org.hotpotmaterial.code.entity.ColumnPO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
public interface ColumnApi {

  /**
   * 批量保存表字段配置
   */
  @ApiOperation(value = "批量保存表字段配置", notes = "批量保存表字段配置", response = ResultPageDTO.class, tags = {"Column"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultDTO.class)})
  @RequestMapping(value = "/columns/save", produces = {"application/json"},
      method = RequestMethod.POST)
  ResponseEntity<ResultDTO> columnsSavePost(
      @ApiParam(value = "表对象", required = false) @RequestBody List<ColumnPO> columns);
  
  /**
   * 保存表字段配置
   * @param column
   * @return
   */
  @ApiOperation(value = "保存表字段配置", notes = "保存表字段配置", response = ResultPageDTO.class, tags = {"Column"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultDTO.class)})
  @RequestMapping(value = "/column/save", produces = {"application/json"},
      method = RequestMethod.POST)
  public ResponseEntity<ResultDTO> columnSavePost(@RequestBody ColumnPO column);  
  
  /**
   * 保存表字段配置和字典表
   * @param column
   * @return
   */
  @ApiOperation(value = "保存表字段配置和字典表", notes = "保存表字段配置和字典表", response = ResultPageDTO.class, tags = {"Column"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultDTO.class)})
  @RequestMapping(value = "table/{id}/column/dict/save", produces = {"application/json"},
      method = RequestMethod.POST)
  public ResponseEntity<ResultDTO> columnAndDictSavePost(
      @RequestBody ResultOfColumnDTO columnDTO,
      @PathVariable String id);  
}
