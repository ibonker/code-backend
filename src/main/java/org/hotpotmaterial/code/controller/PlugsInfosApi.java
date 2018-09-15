package org.hotpotmaterial.code.controller;

import org.hotpotmaterial.anywhere.common.mvc.page.rest.request.PageDTO;
import org.hotpotmaterial.anywhere.common.mvc.page.rest.response.ResultPageDTO;
import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.dto.ResultOfPlugsInfoDTO;
import org.hotpotmaterial.code.entity.PlugsInfoPO;
import org.springframework.http.MediaType;
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
 * 控制器的声明接口，可以与FeignClient配合使用 
 * 
 * @author Hotpotmaterial-Code2
 */
@Api(value = "PlugsInfos")
@RequestMapping(value = "/manage/api/v1")
public interface PlugsInfosApi {
  
  /**
   * 实体new.titancode.test.PlugsInfoPO新增
   */
  @ApiOperation(value = "实体new.titancode.test.PlugsInfoPO新增", notes = "实体new.titancode.test.PlugsInfoPO新增", response = ResultOfPlugsInfoDTO.class)
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultOfPlugsInfoDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/plugs_infos", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      method = RequestMethod.POST)
  ResponseEntity<ResultDTO> plugsInfosPost(@ApiParam(value = "plugsInfo", required = true) @RequestBody(required = true) PlugsInfoPO plugsInfo);
  /**
   * 实体new.titancode.test.PlugsInfoPO删除
   */
  @ApiOperation(value = "实体new.titancode.test.PlugsInfoPO删除", notes = "实体new.titancode.test.PlugsInfoPO删除", response = ResultOfPlugsInfoDTO.class)
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultOfPlugsInfoDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/plugs_infos/{plugsInfoId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      method = RequestMethod.DELETE)
  ResponseEntity<ResultDTO> plugsInfosDelete(@ApiParam(value = "plugsInfoId", required = true) @PathVariable(required = true) String plugsInfoId);
  /**
   * 实体new.titancode.test.PlugsInfoPO更新
   */
  @ApiOperation(value = "实体new.titancode.test.PlugsInfoPO更新", notes = "实体new.titancode.test.PlugsInfoPO更新", response = ResultOfPlugsInfoDTO.class)
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultOfPlugsInfoDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/plugs_infos/{plugsInfoId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      method = RequestMethod.PUT)
  ResponseEntity<ResultDTO> plugsInfosPut(@ApiParam(value = "plugsInfoId", required = true) @PathVariable(required = true) String plugsInfoId, @ApiParam(value = "plugsInfo", required = true) @RequestBody(required = true) PlugsInfoPO plugsInfo);
  /**
   * 实体new.titancode.test.PlugsInfoPO详情
   */
  @ApiOperation(value = "实体new.titancode.test.PlugsInfoPO详情", notes = "实体new.titancode.test.PlugsInfoPO详情", response = ResultOfPlugsInfoDTO.class)
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultOfPlugsInfoDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/plugs_infos/{plugsInfoId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> plugsInfosGET(@ApiParam(value = "plugsInfoId", required = true) @PathVariable(required = true) String plugsInfoId);
  /**
   * 实体new.titancode.test.PlugsInfoPO分页列表
   */
  @ApiOperation(value = "实体new.titancode.test.PlugsInfoPO分页列表", notes = "实体new.titancode.test.PlugsInfoPO分页列表", response = ResultPageDTO.class)
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultPageDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/plugs_infos/pages", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      method = RequestMethod.POST)
  ResponseEntity<ResultDTO> plugsInfosPagesPost(@ApiParam(value = "searchParams", required = true) @RequestBody(required = true) PageDTO searchParams);
  
}