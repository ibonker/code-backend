package org.hotpotmaterial.code.dto;

import java.util.List;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.entity.ApiBasePO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * ApiBase DTO
 * @author xuyufeng
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
public class ResultOfApiBaseDTO extends ResultDTO {

  @JsonProperty("apiBases")
  @JsonPropertyDescription("所有API")
  @ApiModelProperty(value = "所有API")
  private List<ApiBasePO> apiBases;

  @JsonProperty("apiBase")
  @JsonPropertyDescription("API")
  @ApiModelProperty(value = "API")
  private ApiBasePO apiBase;

  /**
   * 返回ApiBase列表对象
   * @param apiBases
   * @return
   */
  public ResultOfApiBaseDTO apiBases(List<ApiBasePO> apiBases) {
    this.apiBases = apiBases;
    return this;
  }

  /**
   * 返回ApiBase对象
   * @param apiBase
   * @return
   */
  public ResultOfApiBaseDTO apiBase(ApiBasePO apiBase) {
    this.apiBase = apiBase;
    return this;
  }
}
