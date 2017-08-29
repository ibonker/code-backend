/**
 * 
 */
package com.changan.code.dto;

import java.util.List;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.entity.ColumnPO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

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
public class ResultOfColumnsDTO extends ResultDTO {

  // 返回编码
  @JsonProperty("columns")
  @JsonPropertyDescription("表字段")
  @ApiModelProperty(value = "表字段")
  private List<ColumnPO> columns;

  public ResultOfColumnsDTO columns(List<ColumnPO> columns) {
    this.columns = columns;
    return this;
  }

}