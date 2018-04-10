package org.hotpotmaterial.code.dto;

import java.util.List;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.entity.ApiViewFormConfigPO;
import org.hotpotmaterial.code.entity.ApiViewPO;
import org.hotpotmaterial.code.entity.ApiViewTableConfigPO;
import org.hotpotmaterial.code.entity.TablePO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
public class ResultOfApiViewDTO extends ResultDTO {

  // 返回所有页面配置
  @JsonProperty("apiViews")
  @JsonPropertyDescription("所有页面配置")
  @ApiModelProperty(value = "所有页面配置")
  private List<ApiViewPO> apiViews;

  // 返回各个表对应的Api
  @JsonProperty("tableApis")
  @JsonPropertyDescription("各个表对应的Api")
  @ApiModelProperty(value = "各个表对应的Api")
  private List<TablePO> tableApis;

  // Api页面表格配置列表
  @JsonProperty("tableConfigs")
  @JsonPropertyDescription("Api页面表格配置列表")
  @ApiModelProperty(value = "Api页面表格配置列表")
  private List<ApiViewTableConfigPO> tableConfigs;
  
  // Api页面表单配置列表
  @JsonProperty("formConfigs")
  @JsonPropertyDescription("Api页面表单配置列表")
  @ApiModelProperty(value = "Api页面表单配置列表")
  private List<ApiViewFormConfigPO> formConfigs;

  public ResultOfApiViewDTO apiViews(List<ApiViewPO> apiViews) {
    this.apiViews = apiViews;
    return this;
  }

  public ResultOfApiViewDTO tableApis(List<TablePO> tableApis) {
    this.tableApis = tableApis;
    return this;
  }

  public ResultOfApiViewDTO tableConfigs(List<ApiViewTableConfigPO> tableConfigs) {
    this.tableConfigs = tableConfigs;
    return this;
  }
  
  public ResultOfApiViewDTO formConfigs(List<ApiViewFormConfigPO> formConfigs) {
    this.formConfigs = formConfigs;
    return this;
  }
}
