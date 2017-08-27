package com.changan.code.dto;

import java.util.List;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.entity.ApiParamPO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResultOfApiParamDTO extends ResultDTO{

	//返回编码
	 @JsonProperty("apiParams")
	 @JsonPropertyDescription("全部参数")
	 @ApiModelProperty(value = "全部参数")
	 private List<ApiParamPO> apiParams;
	 
	 public ResultOfApiParamDTO apiParams(List<ApiParamPO> apiParams) {
	   this.apiParams = apiParams;
	   return this;
	 }
}
