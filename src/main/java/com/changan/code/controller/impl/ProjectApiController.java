/**
 * 
 */
package com.changan.code.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.changan.anywhere.common.mvc.page.rest.request.PageDTO;
import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.anywhere.common.mvc.page.rest.response.ResultPageDTO;
import com.changan.code.common.Constants;
import com.changan.code.common.DtoType;
import com.changan.code.config.property.ApiProperties;
import com.changan.code.controller.ProjectApi;
import com.changan.code.dto.JavaTypeDTO;
import com.changan.code.dto.ResultOfComponentsDTO;
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
  public ResponseEntity<JsonSchema> projectsSchemaGet() {
    JsonSchema jsonSchema = this.getJsonSchemaByJavaType(new TypeReference<ProjectPO>() {});
    return new ResponseEntity<JsonSchema>(jsonSchema, HttpStatus.OK);
  }

  /**
   * 项目分页查询列表
   */
  @Override
  public ResponseEntity<ResultDTO> projectsGet(@RequestBody PageDTO searchParams) {
    Page<ProjectPO> result = projectService.findProjecsPage(searchParams);
    return new ResponseEntity<ResultDTO>(
        new ResultPageDTO<ProjectPO>().totalElements(result.getTotalElements())
            .pageNumber(result.getNumber()).pageSize(result.getSize()).data(result.getContent())
            .message("成功").statusCode(Constants.SUCCESS_API_CODE),
        HttpStatus.OK);
  }

  /**
   * 获取project
   */
  @Override
  public ResponseEntity<ResultDTO> projectsShowGet(@PathVariable String id) {
    ProjectPO project = projectService.getProjectById(id);
    return new ResponseEntity<ResultDTO>(new ResultOfProjectDTO().project(project).message("成功")
        .statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
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
    return new ResponseEntity<ResultDTO>(new ResultOfProjectDTO().project(project).message("成功")
        .statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }

  /**
   * 项目持久化实体
   */
  @Override
  public ResponseEntity<ResultDTO> projectDtoPoGet(@PathVariable String id) {
    return new ResponseEntity<ResultDTO>(
        new ResultOfTypeDTO().refobj(projectService.getProjectDTOandPO(id)).message("成功")
            .statusCode(Constants.SUCCESS_API_CODE),
        HttpStatus.OK);
  }

  @Override
  public ResponseEntity<ResultDTO> projectDataTypeGet(@PathVariable String id) {
    return new ResponseEntity<ResultDTO>(new ResultOfTypeDTO()
        .base(new JavaTypeDTO(DtoType.BASE.toString().toLowerCase(), DtoType.BASE.getCname(),
            apis.getBaseType()))
        .array(new JavaTypeDTO(DtoType.ARRAY.toString().toLowerCase(), DtoType.ARRAY.getCname(),
            apis.getArrayType()))
        .refobj(projectService.getProjectDTOandPO(id)).message("成功")
        .statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<ResultDTO> projectsComponentsDefaultGet() {
    return new ResponseEntity<ResultDTO>(
        new ResultOfComponentsDTO().categories(projectService.getComponents()).message("成功")
            .statusCode(Constants.SUCCESS_API_CODE),
        HttpStatus.OK);
  }

}
