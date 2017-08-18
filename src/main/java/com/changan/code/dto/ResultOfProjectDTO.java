/**
 * 
 */
package com.changan.code.dto;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.entity.ProjectPO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author wenxing
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResultOfProjectDTO extends ResultDTO {
  
  // 返回编码
  @JsonProperty("project")
  @JsonPropertyDescription("项目")
  @ApiModelProperty(value = "项目")
  private ProjectPO project;
  
  public ResultOfProjectDTO project(ProjectPO project) {
    this.project = project;
    return this;
  }
  
}
