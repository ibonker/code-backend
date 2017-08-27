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
public class ResultOfApiBaseDTO extends ResultDTO{

	//返回编码
	 @JsonProperty("apiBases")
	 @JsonPropertyDescription("表字段")
	 @ApiModelProperty(value = "表字段")
	 private List<ApiBasePO> apiBases;
	 
	 public ResultOfApiBaseDTO apiBases(List<ApiBasePO> apiBases) {
	   this.apiBases = apiBases;
	   return this;
	 }
}
