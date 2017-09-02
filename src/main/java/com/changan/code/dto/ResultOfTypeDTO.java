/**
 * 
 */
package com.changan.code.dto;

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
public class ResultOfTypeDTO extends ResultDTO {
  
  @JsonProperty("base")
  @JsonPropertyDescription("java基础类型")
  @ApiModelProperty(value = "java基础类型")
  private JavaTypeDTO base;
  
  @JsonProperty("array")
  @JsonPropertyDescription("java集合类型")
  @ApiModelProperty(value = "java集合类型")
  private JavaTypeDTO array;
  
  @JsonProperty("dto")
  @JsonPropertyDescription("自定义实体")
  @ApiModelProperty(value = "DTO实体")
  private RefObjDTO dto;
  
  @JsonProperty("po")
  @JsonPropertyDescription("  持久化实体")
  @ApiModelProperty(value = "PO实体")
  private RefObjDTO po;
  
  public ResultOfTypeDTO base(JavaTypeDTO base) {
    this.base = base;
    return this;
  }
  
  public ResultOfTypeDTO array(JavaTypeDTO array) {
    this.array = array;
    return this;
  }
  
  public ResultOfTypeDTO dto(RefObjDTO dto) {
    this.dto = dto;
    return this;
  }
  
  public ResultOfTypeDTO po(RefObjDTO po) {
    this.po = po;
    return this;
  }
  
}
