package org.hotpotmaterial.code.controller;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.dto.ResultOfContextDTO;
import org.hotpotmaterial.code.entity.ContextPO;
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
@Api(value = "Contexts")
@RequestMapping(value = "/manage/api/v1")
public interface ContextsApi {
  
  /**
   * 实体new.titancode.test.ContextPO新增
   */
  @ApiOperation(value = "实体new.titancode.test.ContextPO新增", notes = "实体new.titancode.test.ContextPO新增", response = ResultOfContextDTO.class)
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultOfContextDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/contexts", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      method = RequestMethod.POST)
  ResponseEntity<ResultDTO> contextsPost(@ApiParam(value = "context", required = true) @RequestBody(required = true) ContextPO context);
  /**
   * 实体new.titancode.test.ContextPO删除
   */
  @ApiOperation(value = "实体new.titancode.test.ContextPO删除", notes = "实体new.titancode.test.ContextPO删除", response = ResultOfContextDTO.class)
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultOfContextDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/contexts/{contextId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      method = RequestMethod.DELETE)
  ResponseEntity<ResultDTO> contextsDelete(@ApiParam(value = "contextId", required = true) @PathVariable(required = true) String contextId);
  /**
   * 实体new.titancode.test.ContextPO更新
   */
  @ApiOperation(value = "实体new.titancode.test.ContextPO更新", notes = "实体new.titancode.test.ContextPO更新", response = ResultOfContextDTO.class)
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultOfContextDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/contexts/{contextId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      method = RequestMethod.PUT)
  ResponseEntity<ResultDTO> contextsPut(@ApiParam(value = "contextId", required = true) @PathVariable(required = true) String contextId, @ApiParam(value = "context", required = true) @RequestBody(required = true) ContextPO context);
  /**
   * 实体new.titancode.test.ContextPO详情
   */
  @ApiOperation(value = "实体new.titancode.test.ContextPO详情", notes = "实体new.titancode.test.ContextPO详情", response = ResultOfContextDTO.class)
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultOfContextDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/contexts/{contextId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> contextsGET(@ApiParam(value = "contextId", required = true) @PathVariable(required = true) String contextId);
  
  /**
   * 根据目录ID查询实体new.titancode.test.ContextPO详情
   */
  @ApiOperation(value = "根据目录ID查询实体new.titancode.test.ContextPO详情", notes = "根据目录ID查询实体new.titancode.test.ContextPO详情", response = ResultOfContextDTO.class)
  @ApiResponses(
		  value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultOfContextDTO.class),
				  @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/contexts/directory/{directoryId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
  method = RequestMethod.GET)
  ResponseEntity<ResultDTO> contextsByDirectoryId(@ApiParam(value = "ddirectoryId", required = true) @PathVariable(required = true) String directoryId);
  
}