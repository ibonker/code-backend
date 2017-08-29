package com.changan.code.dto;

import java.util.List;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.entity.TransferObjFieldPO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResultOfTransferObjFieldDTO extends ResultDTO {

  @JsonProperty("transferObjField")
  @JsonPropertyDescription("DTO实体属性")
  @ApiModelProperty(value = "DTO实体")
  private TransferObjFieldPO transferObjField;

  @JsonProperty("transferObjFields")
  @JsonPropertyDescription("所有DTO实体属性")
  @ApiModelProperty(value = "所有DTO实体属性")
  private List<TransferObjFieldPO> transferObjFields;

  public ResultOfTransferObjFieldDTO transferObjField(TransferObjFieldPO transferObjField) {
    this.transferObjField = transferObjField;
    return this;
  }

  public ResultOfTransferObjFieldDTO transferObjFields(List<TransferObjFieldPO> transferObjFields) {
    this.transferObjFields = transferObjFields;
    return this;
  }
}
