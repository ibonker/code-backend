package org.hotpotmaterial.code.controller;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.dto.ResultOfDirectoryDTO;
import org.hotpotmaterial.code.entity.DirectoryPO;
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
@Api(value = "Directorys")
@RequestMapping(value = "/manage/api/v1")
public interface DirectorysApi {
  
  /**
   * 实体new.titancode.test.DirectoryPO新增
   */
  @ApiOperation(value = "实体new.titancode.test.DirectoryPO新增", notes = "实体new.titancode.test.DirectoryPO新增", response = ResultOfDirectoryDTO.class)
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultOfDirectoryDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/directorys", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      method = RequestMethod.POST)
  ResponseEntity<ResultDTO> directorysPost(@ApiParam(value = "directory", required = true) @RequestBody(required = true) DirectoryPO directory);
  /**
   * 实体new.titancode.test.DirectoryPO更新
   */
  @ApiOperation(value = "实体new.titancode.test.DirectoryPO更新", notes = "实体new.titancode.test.DirectoryPO更新", response = ResultOfDirectoryDTO.class)
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultOfDirectoryDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/directorys/{directoryId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      method = RequestMethod.PUT)
  ResponseEntity<ResultDTO> directorysPut(@ApiParam(value = "directoryId", required = true) @PathVariable(required = true) String directoryId, @ApiParam(value = "directory", required = true) @RequestBody(required = true) DirectoryPO directory);
  /**
   * 实体new.titancode.test.DirectoryPO删除
   */
  @ApiOperation(value = "实体new.titancode.test.DirectoryPO删除", notes = "实体new.titancode.test.DirectoryPO删除", response = ResultOfDirectoryDTO.class)
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultOfDirectoryDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/directorys/{directoryId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      method = RequestMethod.DELETE)
  ResponseEntity<ResultDTO> directorysDelete(@ApiParam(value = "directoryId", required = true) @PathVariable(required = true) String directoryId);
  /**
   * 实体new.titancode.test.DirectoryPO详情
   */
  @ApiOperation(value = "实体new.titancode.test.DirectoryPO详情", notes = "实体new.titancode.test.DirectoryPO详情", response = ResultOfDirectoryDTO.class)
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultOfDirectoryDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/directorys/{directoryId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> directorysGET(@ApiParam(value = "directoryId", required = true) @PathVariable(required = true) String directoryId);
  
  /**
   * 查询是所有根目录
   * @param directoryId
   * @return
   */
  @ApiOperation(value = "查询是所有根目录", notes = "查询是所有根目录", response = ResultOfDirectoryDTO.class)
  @ApiResponses(
		  value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultOfDirectoryDTO.class),
				  @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/directorys/getAll", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
  method = RequestMethod.GET)
  ResponseEntity<ResultDTO> directorysGetAll();
  
  /**
   * 根据目录ID查询它的子目录
   * @param directoryId
   * @return
   */
  @ApiOperation(value = "根据目录ID查询它的子目录", notes = "根据目录ID查询它的子目录", response = ResultOfDirectoryDTO.class)
  @ApiResponses(
		  value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultOfDirectoryDTO.class),
				  @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/directorysById/{directoryId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
  method = RequestMethod.GET)
  ResponseEntity<ResultDTO> directorysById(@ApiParam(value = "directoryId", required = true) @PathVariable(required = true) String directoryId);
  
}