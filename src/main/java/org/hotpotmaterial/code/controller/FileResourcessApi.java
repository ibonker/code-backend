package org.hotpotmaterial.code.controller;

import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.dto.ResultOfFileResourcesDTO;
import org.hotpotmaterial.code.entity.FileResourcesPO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

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
@Api(value = "FileResourcess")
@RequestMapping(value = "/manage/api/v1")
public interface FileResourcessApi {

	/**
	 * 实体new.titancode.test.FileResourcesPO详情
	 */
	@ApiOperation(value = "实体new.titancode.test.FileResourcesPO详情", notes = "实体new.titancode.test.FileResourcesPO详情", response = ResultOfFileResourcesDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "返回结果信息", response = ResultOfFileResourcesDTO.class),
			@ApiResponse(code = 401, message = "返回认证错误信息") })
	@RequestMapping(value = "/file_resourcess/{fileResourcesId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
	ResponseEntity<ResultDTO> fileResourcessGET(
			@ApiParam(value = "fileResourcesId", required = true) @PathVariable(required = true) String fileResourcesId);

	/**
	 * 实体new.titancode.test.FileResourcesPO更新
	 */
	@ApiOperation(value = "实体new.titancode.test.FileResourcesPO更新", notes = "实体new.titancode.test.FileResourcesPO更新", response = ResultOfFileResourcesDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "返回结果信息", response = ResultOfFileResourcesDTO.class),
			@ApiResponse(code = 401, message = "返回认证错误信息") })
	@RequestMapping(value = "/file_resourcess/{fileResourcesId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.PUT)
	ResponseEntity<ResultDTO> fileResourcessPut(
			@ApiParam(value = "fileResourcesId", required = true) @PathVariable(required = true) String fileResourcesId,
			@ApiParam(value = "fileResources", required = true) @RequestBody(required = true) FileResourcesPO fileResources);

	/**
	 * 实体new.titancode.test.FileResourcesPO删除
	 */
	@ApiOperation(value = "实体new.titancode.test.FileResourcesPO删除", notes = "实体new.titancode.test.FileResourcesPO删除", response = ResultOfFileResourcesDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "返回结果信息", response = ResultOfFileResourcesDTO.class),
			@ApiResponse(code = 401, message = "返回认证错误信息") })
	@RequestMapping(value = "/file_resourcess/{fileResourcesId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.DELETE)
	ResponseEntity<ResultDTO> fileResourcessDelete(
			@ApiParam(value = "fileResourcesId", required = true) @PathVariable(required = true) String fileResourcesId);

	/**
	 * 实体new.titancode.test.FileResourcesPO新增
	 */
	@ApiOperation(value = "实体new.titancode.test.FileResourcesPO新增", notes = "实体new.titancode.test.FileResourcesPO新增", response = ResultOfFileResourcesDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "返回结果信息", response = ResultOfFileResourcesDTO.class),
			@ApiResponse(code = 401, message = "返回认证错误信息") })
	@RequestMapping(value = "/file_resourcess", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
	ResponseEntity<ResultDTO> fileResourcessPost(
			@ApiParam(value = "fileResources", required = true) @RequestBody(required = true) FileResourcesPO fileResources);

	/**
	 * 实体titan.portal.FileResourcesPO上传
	 */
	@ApiOperation(value = "实体manage.titancode.test.FileResourcesPO上传", notes = "实体manage.titancode.test.FileResourcesPO上传", response = ResultOfFileResourcesDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "返回结果信息", response = ResultOfFileResourcesDTO.class),
			@ApiResponse(code = 401, message = "返回认证错误信息") })
	@RequestMapping(value = "/file_resourcess/upload", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
	ResponseEntity<ResultDTO> fileResourcessUpload(MultipartFile file);

	/**
	 * 根据地址访问资源
	 */
	@ApiOperation(value = "根据地址访问资源", notes = "根据地址访问资源", response = ServletOutputStream.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "返回结果信息", response = ServletOutputStream.class),
			@ApiResponse(code = 401, message = "返回认证错误信息") })
	@RequestMapping(value = "/file_resourcess_url", method = RequestMethod.GET)
	OutputStream fileResourcessRead(HttpServletRequest request, HttpServletResponse response);
}