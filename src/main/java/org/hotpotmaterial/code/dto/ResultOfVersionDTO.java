package org.hotpotmaterial.code.dto;

import java.util.List;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;

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
public class ResultOfVersionDTO extends ResultDTO{
  
  @JsonProperty("versionList")
  @JsonPropertyDescription("versionList")
  @ApiModelProperty(value = "versionList")
  private List<Object> versionList;
  
  /**
   * 版本明细
   * @param versionList
   * @return
   */
  public ResultOfVersionDTO versionList(List<Object> versionList) {
    this.versionList = versionList;
    return this;
  }
}
