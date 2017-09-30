/**
 * 
 */
package com.changan.code.controller.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.changan.anywhere.common.mvc.page.rest.request.PageDTO;
import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.anywhere.common.mvc.page.rest.response.ResultJsonSchemaDTO;
import com.changan.anywhere.common.mvc.page.rest.response.ResultPageDTO;
import com.changan.code.common.BaseType;
import com.changan.code.common.RestStatus;
import com.changan.code.config.property.ApiProperties;
import com.changan.code.controller.ProjectApi;
import com.changan.code.dto.JavaTypeDTO;
import com.changan.code.dto.ResultOfComponentsDTO;
import com.changan.code.dto.ResultOfMsgDataDTO;
import com.changan.code.dto.ResultOfProjectDTO;
import com.changan.code.dto.ResultOfTypeDTO;
import com.changan.code.entity.ProjectPO;
import com.changan.code.service.IProjectService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

/**
 * @author wenxing
 *
 */
@Controller
public class ProjectApiController extends BaseController implements ProjectApi {

  @Autowired
  private IProjectService projectService;

  @Autowired
  private ApiProperties apis;

  /**
   * 项目schema
   */
  @Override
  public ResponseEntity<ResultDTO> projectsSchemaGet() {
    JsonSchema jsonSchema = this.getJsonSchemaByJavaType(new TypeReference<ProjectPO>() {});
    return new ResponseEntity<>(new ResultJsonSchemaDTO().jsonSchema(jsonSchema)
        .message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
        HttpStatus.OK);
  }

  /**
   * 项目分页查询列表
   */
  @Override
  public ResponseEntity<ResultDTO> projectsGet(@RequestBody PageDTO searchParams) {
    Page<ProjectPO> result = projectService.findProjecsPage(searchParams);
    return new ResponseEntity<>(new ResultPageDTO<ProjectPO>()
        .totalElements(result.getTotalElements()).pageNumber(result.getNumber())
        .pageSize(result.getSize()).data(result.getContent())
        .message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
        HttpStatus.OK);
  }

  /**
   * 获取project
   */
  @Override
  public ResponseEntity<ResultDTO> projectsShowGet(@PathVariable String id) {
    ProjectPO project = projectService.getProjectById(id);
    return new ResponseEntity<>(new ResultOfProjectDTO().project(project)
        .message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
        HttpStatus.OK);
  }

  /**
   * 保存project
   */
  @Override
  public ResponseEntity<ResultDTO> projectSavePost(@RequestBody ProjectPO project) {
    if (project.isNew()) {
      project = projectService.saveProject(project);
    } else {
      project = projectService.updateProject(project);
    }
    return new ResponseEntity<>(new ResultOfProjectDTO().project(project)
        .message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
        HttpStatus.OK);
  }

  /**
   * 项目持久化实体
   */
  @Override
  public ResponseEntity<ResultDTO> projectDtoPoGet(@PathVariable String id) {
    return new ResponseEntity<>(new ResultOfTypeDTO().dto(projectService.getProjectDTO(id))
        .po(projectService.getProjectPO(id)).message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }

  /**
   * 项目支持数据类型
   */
  @Override
  public ResponseEntity<ResultDTO> projectDataTypeGet(@PathVariable String id) {
    return new ResponseEntity<>(new ResultOfTypeDTO()
        .base(new JavaTypeDTO(BaseType.BASE.toString().toLowerCase(), BaseType.BASE.getCname(),
            apis.getBaseType()))
        .array(new JavaTypeDTO(BaseType.ARRAY.toString().toLowerCase(), BaseType.ARRAY.getCname(),
            apis.getArrayType()))
        .dto(projectService.getProjectDTO(id)).po(projectService.getProjectPO(id))
        .message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
        HttpStatus.OK);
  }

  /**
   * 项目支持组件
   */
  @Override
  public ResponseEntity<ResultDTO> projectsComponentsDefaultGet() {
    return new ResponseEntity<>(new ResultOfComponentsDTO()
        .categories(projectService.getComponents()).message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }

  /**
   * 生成项目代码
   */
  @Override
  public ResponseEntity<ResultDTO> projectsGenerateCodeGet(@PathVariable String id) {
    return new ResponseEntity<>(new ResultOfMsgDataDTO()
        .msgData(projectService.generateCodeFiles(id)).message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }

  /**
   * 下载项目代码
   */
  @Override
  public ResponseEntity<InputStreamResource> projectsDownloadGet(@PathVariable String projectName)
      throws FileNotFoundException {
    File file = projectService.downloadZipFiles(projectName);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.add("Content-Disposition",
        String.format("attachment; filename=\"%s\"", file.getName()));
    headers.add("Pragma", "no-cache");
    headers.add("Expires", "0");
    return ResponseEntity.ok().headers(headers).contentLength(file.length())
        .body(new InputStreamResource(new FileInputStream(file)));
  }

  @Override
  public ResponseEntity<InputStreamResource> projectsDownloadUIGet(@PathVariable String projectName)
      throws FileNotFoundException {
    File file = projectService.downloadZipUIFiles(projectName);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.add("Content-Disposition",
        String.format("attachment; filename=\"%s\"", file.getName()));
    headers.add("Pragma", "no-cache");
    headers.add("Expires", "0");
    return ResponseEntity.ok().headers(headers).contentLength(file.length())
        .body(new InputStreamResource(new FileInputStream(file)));
  }

}
