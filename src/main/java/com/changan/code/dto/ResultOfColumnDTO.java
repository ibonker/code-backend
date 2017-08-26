/**
 * 
 */
package com.changan.code.dto;

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
public class ResultOfColumnDTO extends ResultDTO {

  // 返回编码
  @JsonProperty("column")
  @JsonPropertyDescription("表字段")
  @ApiModelProperty(value = "表字段")
  private ColumnPO column;

  public ResultOfColumnDTO column(ColumnPO column) {
    this.column = column;
    return this;
  }

}
