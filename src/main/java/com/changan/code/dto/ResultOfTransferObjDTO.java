package com.changan.code.dto;

import java.util.List;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.common.Constants;
import com.changan.code.entity.TransferObjPO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * @author xuyufeng
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResultOfTransferObjDTO extends ResultDTO {
  
  @JsonProperty("isDto")
  @JsonPropertyDescription("是否为DTO实体")
  @ApiModelProperty(value = "是否为DTO实体")
  private String isDto = Constants.IS_ACTIVE;

  @JsonProperty("transferObj")
  @JsonPropertyDescription("DTO实体")
  @ApiModelProperty(value = "DTO实体")
  private TransferObjPO transferObj;

  @JsonProperty("transferObjs")
  @JsonPropertyDescription("所有DTO实体")
  @ApiModelProperty(value = "所有DTO实体")
  private List<TransferObjPO> transferObjs;
  
  public ResultOfTransferObjDTO isDto(String isDto) {
    this.isDto = isDto;
    return this;
  }

  public ResultOfTransferObjDTO transferObj(TransferObjPO transferObj) {
    this.transferObj = transferObj;
    return this;
  }

  public ResultOfTransferObjDTO transferObjs(List<TransferObjPO> transferObjs) {
    this.transferObjs = transferObjs;
    return this;
  }
}
