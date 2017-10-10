/**
 * 
 */
package com.changan.code.dto;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.entity.ProjectPO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
@JsonInclude(value = Include.NON_NULL)
public class ResultOfProjectDTO extends ResultDTO {
  
  // 返回编码
  @JsonProperty("project")
  @JsonPropertyDescription("项目")
  @ApiModelProperty(value = "项目")
  private ProjectPO project;
  
  @JsonProperty("isDictionary")
  @JsonPropertyDescription("字典表信息")
  @ApiModelProperty(value = "字典表信息")
  private String isDictionary;
  
  public ResultOfProjectDTO project(ProjectPO project) {
    this.project = project;
    return this;
  }
  
  public ResultOfProjectDTO isDictionary(String isDictionary){
    this.isDictionary = isDictionary;
    return this;
  }
}
