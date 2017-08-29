/**
 * 
 */
package com.changan.code.dto;

import java.util.List;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
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
public class ResultOfComponentsDTO extends ResultDTO {

  // 返回组件
  @JsonProperty("categories")
  @JsonPropertyDescription("组件")
  @ApiModelProperty(value = "组件")
  private List<ComponentCategory> categories;
  
  public ResultOfComponentsDTO categories(List<ComponentCategory> categories) {
    this.categories = categories;
    return this;
  }

}
