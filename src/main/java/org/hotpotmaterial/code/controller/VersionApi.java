package org.hotpotmaterial.code.controller;

import javax.servlet.http.HttpServletRequest;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.dto.ResultOfVersionDTO;
import org.hotpotmaterial.code.entity.ProjectPO;
import org.hotpotmaterial.code.entity.VersionPO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "version", description = "the project API")
@RequestMapping(value = "/codegen/api/v1")
public interface VersionApi {
  
  /**
   * 代码生成器版本履历
   */
  @ApiOperation(value = "代码生成器版本履历", notes = "代码生成器版本履历", response = ResultOfVersionDTO.class, tags = {"Version"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfVersionDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfVersionDTO.class)})
  @RequestMapping(value = "/version/code", produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> codeVer();
  
  /**
   * 前台版本履历
   */
  @ApiOperation(value = "前台版本履历", notes = "前台版本履历", response = ResultOfVersionDTO.class, tags = {"Version"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfVersionDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfVersionDTO.class)})
  @RequestMapping(value = "/version/ui", produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> uiVer();
  
  /**
   * 版本信息的保存或修改
   * @param versionPO
   * @return
   */
  @ApiOperation(value = "保存或修改版本信息", notes = "保存或修改版本信息", response = ResultDTO.class, tags = {"Version"})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultDTO.class),
      @ApiResponse(code = 200, message = "返回错误信息", response = ResultDTO.class)})
  @RequestMapping(value = "/version/save", produces = {"application/json"},
      method = RequestMethod.POST)
  ResponseEntity<ResultDTO> versionSavePost(
      @RequestBody VersionPO versionPO);
  
  @ApiOperation(value = "根据ID删除版本信息", notes = "根据ID删除版本信息", response = ResultDTO.class, tags = {"Version"})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultDTO.class),
		  @ApiResponse(code = 200, message = "返回错误信息", response = ResultDTO.class)})
  @RequestMapping(value = "/version/delete/{versionId}", produces = {"application/json"},
  method = RequestMethod.POST)
  ResponseEntity<ResultDTO> deleteSavePost(
		  @ApiParam(value = "dictTypeId", required = true) @PathVariable String versionId);
  
}
