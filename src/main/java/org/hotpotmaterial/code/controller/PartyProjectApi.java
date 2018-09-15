package org.hotpotmaterial.code.controller;

import java.util.List;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.dto.ResultOfProjectDTO;
import org.hotpotmaterial.code.entity.PartyProjectPO;
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

@Api(value = "partyproject", description = "the project API")
@RequestMapping(value = "/codegen/api/v1")
public interface PartyProjectApi {
  
  
  /**
   * 项目查看权限的保存或修改
   * @param versionPO
   * @return
   */
  @ApiOperation(value = "项目查看权限的保存或修改", notes = "项目查看权限的保存或修改", response = ResultDTO.class, tags = {"PartyProject"})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultDTO.class),
      @ApiResponse(code = 200, message = "返回错误信息", response = ResultDTO.class)})
  @RequestMapping(value = "/partyproject/save", produces = {"application/json"},
      method = RequestMethod.POST)
  ResponseEntity<ResultDTO> partyprojectSavePost(
      @RequestBody List<PartyProjectPO> partyprojectPO);
  
  /**
   * 根据id删除项目查看权限
   * @param partyprojectId
   * @return
   */
  @ApiOperation(value = "根据ID删除项目查看权限", notes = "根据ID删除项目查看权限", response = ResultDTO.class, tags = {"PartyProject"})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultDTO.class),
		  @ApiResponse(code = 200, message = "返回错误信息", response = ResultDTO.class)})
  @RequestMapping(value = "/partyproject/delete/{partyprojectId}", produces = {"application/json"},
  method = RequestMethod.DELETE)
  ResponseEntity<ResultDTO> deleteSavePost(
		  @ApiParam(value = "partyprojectId", required = true) @PathVariable String partyprojectId);
  
  /**
   * 根据项目ID获取对应的用户信息
   * @param projectPO
   * @return
   */
  @ApiOperation(value = "根据项目ID获取对应的用户信息", notes = "根据项目ID获取对应的用户信息", response = ResultOfProjectDTO.class,
		  tags = {"PartyProject"})
  @ApiResponses(
		  value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfProjectDTO.class),
				  @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfProjectDTO.class)})
  @RequestMapping(value = "/partyproject/userInfo/{projectId}/{style}", produces = {"application/json"},
  method = RequestMethod.GET)
  ResponseEntity<ResultDTO> projectUserInfo(@ApiParam(value = "projectId", required = true) @PathVariable String projectId,@ApiParam(value = "style", required = true) @PathVariable String style);
  
}
