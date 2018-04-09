/**
 * 
 */
package com.changan.code.dto;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.entity.ColumnPO;
import com.changan.code.entity.DictTypePO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
@JsonInclude(value = Include.NON_NULL)
public class ResultOfColumnDTO extends ResultDTO {

  // 返回编码
  @JsonProperty("column")
  @JsonPropertyDescription("表字段")
  @ApiModelProperty(value = "表字段")
  private ColumnPO column;
  
  @JsonProperty("dictType")
  @JsonPropertyDescription("字典表类型")
  @ApiModelProperty(value = "字典表类型")
  private DictTypePO dictType;

  public ResultOfColumnDTO column(ColumnPO column) {
    this.column = column;
    return this;
  }

}
