package com.changan.code.dto;

import java.util.List;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.entity.ApiObjPO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResultOfApiObjDTO extends ResultDTO {

  // 返回编码
  @JsonProperty("apiObjs")
  @JsonPropertyDescription("全部方法")
  @ApiModelProperty(value = "全部方法")
  private List<ApiObjPO> apiObjs;

  // 返回编码
  @JsonProperty("apiObj")
  @JsonPropertyDescription("方法")
  @ApiModelProperty(value = "方法")
  private ApiObjPO apiObj;

  public ResultOfApiObjDTO apiObjs(List<ApiObjPO> apiObjs) {
    this.apiObjs = apiObjs;
    return this;
  }

  public ResultOfApiObjDTO apiObj(ApiObjPO apiObj) {
    this.apiObj = apiObj;
    return this;
  }
}
