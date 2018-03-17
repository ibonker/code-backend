package com.changan.code.dto;

import java.util.List;

import com.changan.anywhere.common.mvc.rest.basic.ResultDTO;
import com.changan.code.entity.TableSeniorRelationPO;
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
public class ResultOfTableSeniorRelationDTO extends ResultDTO {
  
  @JsonProperty("seniorRelationList")
  @JsonPropertyDescription("高级关联sql")
  @ApiModelProperty(value = "高级关联sql")
  private List<TableSeniorRelationPO> seniorRelationList;
  
  @JsonProperty("seniorRelation")
  @JsonPropertyDescription("高级关联关系")
  @ApiModelProperty(value = "高级关联关系")
  private TableSeniorRelationPO seniorRelation;

  public ResultOfTableSeniorRelationDTO seniorRelationList(List<TableSeniorRelationPO> seniorRelationList) {
    this.seniorRelationList = seniorRelationList;
    return this;
  }
  
  public ResultOfTableSeniorRelationDTO seniorRelation(TableSeniorRelationPO seniorRelation) {
    this.seniorRelation = seniorRelation;
    return this;            
  }
}
