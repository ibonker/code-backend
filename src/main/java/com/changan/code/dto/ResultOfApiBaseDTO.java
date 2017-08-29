package com.changan.code.dto;

import java.util.List;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.entity.ApiBasePO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResultOfApiBaseDTO extends ResultDTO {

  // 返回编码
  @JsonProperty("apiBases")
  @JsonPropertyDescription("所有API")
  @ApiModelProperty(value = "所有API")
  private List<ApiBasePO> apiBases;

  @JsonProperty("apiBase")
  @JsonPropertyDescription("API")
  @ApiModelProperty(value = "API")
  private ApiBasePO apiBase;

  public ResultOfApiBaseDTO apiBases(List<ApiBasePO> apiBases) {
    this.apiBases = apiBases;
    return this;
  }

  public ResultOfApiBaseDTO apiBase(ApiBasePO apiBase) {
    this.apiBase = apiBase;
    return this;
  }
}
