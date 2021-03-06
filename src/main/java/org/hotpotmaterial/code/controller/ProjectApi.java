/**
 * 
 */
package org.hotpotmaterial.code.controller;

import java.io.FileNotFoundException;

import javax.servlet.http.HttpServletRequest;

import org.hotpotmaterial.anywhere.common.mvc.page.rest.request.PageDTO;
import org.hotpotmaterial.anywhere.common.mvc.page.rest.response.ResultPageDTO;
import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.dto.ResultOfComponentsDTO;
import org.hotpotmaterial.code.dto.ResultOfMsgDataDTO;
import org.hotpotmaterial.code.dto.ResultOfProjectDTO;
import org.hotpotmaterial.code.dto.ResultOfTypeDTO;
import org.hotpotmaterial.code.entity.DatasourcePO;
import org.hotpotmaterial.code.entity.ProjectPO;
import org.springframework.core.io.InputStreamResource;
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

/**
 * @author wenxing
 *
 */
@Api(value = "project", description = "the project API")
@RequestMapping(value = "/codegen/api/v1")
public interface ProjectApi {

  @ApiOperation(value = "分页获取项目列表", notes = "分页获取项目列表", response = ResultPageDTO.class,
      tags = {"Project"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultPageDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultPageDTO.class)})
  @RequestMapping(value = "/projects", produces = {"application/json"}, method = RequestMethod.POST)
  ResponseEntity<ResultDTO> projectsGet(
      @ApiParam(value = "查询参数", required = false) @RequestBody PageDTO searchParams);

  @ApiOperation(value = "获取项目", notes = "获取项目", response = ResultOfProjectDTO.class,
      tags = {"Project"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfProjectDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfProjectDTO.class)})
  @RequestMapping(value = "/projects/{id}/show", produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> projectsShowGet(
      @ApiParam(value = "id", required = true) @PathVariable String id);

  @ApiOperation(value = "保存项目", notes = "保存项目", response = ResultDTO.class, tags = {"Project"})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultDTO.class),
      @ApiResponse(code = 200, message = "返回错误信息", response = ResultDTO.class)})
  @RequestMapping(value = "/project/save", produces = {"application/json"},
      method = RequestMethod.POST)
  ResponseEntity<ResultDTO> projectSavePost(
      @RequestBody ProjectPO project, @RequestParam(value = "token", required = false) String token, HttpServletRequest request);

  @ApiOperation(value = "项目实体", notes = "项目实体", response = ResultOfTypeDTO.class,
      tags = {"Project"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfTypeDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfTypeDTO.class)})
  @RequestMapping(value = "/project/{id}/dto_po", produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> projectDtoPoGet(
      @ApiParam(value = "id", required = true) @PathVariable String id);

  @ApiOperation(value = "项目实体以及基本类型", notes = "项目实体以及基本类型", response = ResultOfTypeDTO.class,
      tags = {"Project"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfTypeDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfTypeDTO.class)})
  @RequestMapping(value = "/project/{id}/data_type", produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> projectDataTypeGet(
      @ApiParam(value = "id", required = true) @PathVariable String id);

  @ApiOperation(value = "项目组件列表", notes = "项目组件列表", response = ResultOfComponentsDTO.class,
      tags = {"Project"})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfComponentsDTO.class),
      @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfComponentsDTO.class)})
  @RequestMapping(value = "/projects/components/default", produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> projectsComponentsDefaultGet();

  @ApiOperation(value = "生成项目代码", notes = "生成项目代码", response = ResultOfMsgDataDTO.class,
      tags = {"Project"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfMsgDataDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfMsgDataDTO.class)})
  @RequestMapping(value = "/projects/{id}/generate/code", produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> projectsGenerateCodeGet(
      @ApiParam(value = "id", required = true) @PathVariable String id);

  @ApiOperation(value = "下载项目代码", notes = "下载项目代码", response = InputStreamResource.class,
      tags = {"Project"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = InputStreamResource.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = InputStreamResource.class)})
  @RequestMapping(value = "/projects/{projectName}/download",
      produces = {"application/octet-stream"}, method = RequestMethod.GET)
  ResponseEntity<InputStreamResource> projectsDownloadGet(
      @ApiParam(value = "projectName", required = true) @PathVariable String projectName)
      throws FileNotFoundException;
  
  @ApiOperation(value = "下载前台项目代码", notes = "下载前台项目代码", response = InputStreamResource.class,
      tags = {"Project"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = InputStreamResource.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = InputStreamResource.class)})
  @RequestMapping(value = "/projects/{projectId}/downloadUI",
      produces = {"application/octet-stream"}, method = RequestMethod.GET)
  ResponseEntity<InputStreamResource> projectsDownloadUIGet(
      @ApiParam(value = "projectId", required = true) @PathVariable String projectId)
      throws FileNotFoundException;

  @ApiOperation(value = "启用或关闭字典表", notes = "启用或关闭字典表", response = ResultOfProjectDTO.class,
      tags = {"Project"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfProjectDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfProjectDTO.class)})
  @RequestMapping(value = "/project/{id}", produces = {"application/json"},
      method = RequestMethod.POST)
  ResponseEntity<ResultDTO> isDictionaryPost(
      @ApiParam(value = "id", required = true) @PathVariable String id);
  
  @ApiOperation(value = "根据id删除项目", notes = "根据id删除项目", response = ResultOfProjectDTO.class,
      tags = {"Project"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfProjectDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfProjectDTO.class)})
  @RequestMapping(value = "/project/{id}/delete", produces = {"application/json"},
      method = RequestMethod.POST)
  ResponseEntity<ResultDTO> projectPost(
      @ApiParam(value = "id", required = true) @PathVariable String id);

  @ApiOperation(value = "修改项目星标", notes = "修改项目星标", response = ResultOfProjectDTO.class,
	      tags = {"Project"})
	  @ApiResponses(
	      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfProjectDTO.class),
	          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfProjectDTO.class)})
	  @RequestMapping(value = "/project/star/update", produces = {"application/json"},
	      method = RequestMethod.POST)
ResponseEntity<ResultDTO> projectStar(@RequestBody ProjectPO projectPO);
  
  /**
   * 保存项目以及数据库信息,同步数据库以及激活勾选的表
   * @param projectPO
   * @return
   */
  @ApiOperation(value = "保存项目以及数据库信息,同步数据库以及激活勾选的表", notes = "保存项目以及数据库信息,同步数据库以及激活勾选的表", response = ResultOfProjectDTO.class,
		  tags = {"Project"})
  @ApiResponses(
		  value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfProjectDTO.class),
				  @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfProjectDTO.class)})
  @RequestMapping(value = "/project/save/sync", produces = {"application/json"},
  method = RequestMethod.POST)
  ResponseEntity<ResultDTO> projectSaveAndSync(@RequestBody ProjectPO projectPO);
  
  
}
