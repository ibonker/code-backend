/**
 * 
 */
package org.hotpotmaterial.code.dto;

import java.util.List;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
