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
import com.changan.anywhere.common.mvc.rest.basic.ResultDTO;
import com.changan.anywhere.common.mvc.page.rest.response.ResultPageDTO;
import com.changan.code.entity.DatasourcePO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author wenxing
 *
 */
@Api(value = "datasource", description = "the datasource API")
@RequestMapping(value = "/codegen/api/v1")
public interface DatasourceApi {

  /**
   * 检测数据源连接
   */
  @ApiOperation(value = "检测数据源连接", notes = "检测数据源连接", response = ResultDTO.class,
      tags = {"Datasource"})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultDTO.class),
      @ApiResponse(code = 200, message = "返回错误信息", response = ResultDTO.class)})
  @RequestMapping(value = "/datasource/check", produces = {"application/json"},
      method = RequestMethod.POST)
  ResponseEntity<ResultDTO> datasourceCheckPost(
      @ApiParam(value = "数据源") @RequestBody DatasourcePO datasource);

  /**
   * 同步数据库表
   */
  @ApiOperation(value = "同步数据库表连接", notes = "同步数据库表连接", response = ResultDTO.class,
      tags = {"Datasource"})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultDTO.class),
      @ApiResponse(code = 200, message = "返回错误信息", response = ResultDTO.class)})
  @RequestMapping(value = "/datasources/{id}/tables/sync", produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> datasourcesTablesSyncGet(
      @ApiParam(value = "数据源id") @PathVariable String id);

  /**
   * 数据源表分页
   */
  @ApiOperation(value = "分页获取表列表", notes = "分页获取表列表", response = ResultPageDTO.class, tags = {"Datasource"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultPageDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultPageDTO.class)})
  @RequestMapping(value = "/datasources/{id}/tables", produces = {"application/json"},
      method = RequestMethod.POST)
  ResponseEntity<ResultDTO> projectsGet(
      @ApiParam(value = "数据源id", required = false) @PathVariable String id,
      @ApiParam(value = "查询参数", required = false) @RequestBody PageDTO searchParams);

}
