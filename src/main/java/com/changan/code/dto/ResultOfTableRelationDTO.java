package com.changan.code.dto;

import java.util.List;

import com.changan.anywhere.common.mvc.rest.basic.ResultDTO;
import com.changan.code.entity.TableRelationPO;
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
public class ResultOfTableRelationDTO extends ResultDTO {

  // 保存返回关联表关系
  @JsonProperty("tableRelation")
  @JsonPropertyDescription("关联表关系")
  @ApiModelProperty(value = "关联表关系")
  private TableRelationPO tableRelation;
  
  // 返回所有关联表关系
  @JsonProperty("tableRelations")
  @JsonPropertyDescription("所有关联表关系")
  @ApiModelProperty(value = "所有关联表关系")
  private List<TableRelationPO> tableRelations;
  

  public ResultOfTableRelationDTO tableRelation(TableRelationPO tableRelation) {
    this.tableRelation = tableRelation;
    return this;
  }
  
  public ResultOfTableRelationDTO tableRelations(List<TableRelationPO> tableRelations) {
    this.tableRelations = tableRelations;
    return this;
  }
}
