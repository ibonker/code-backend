package org.hotpotmaterial.code.dto;

import java.util.List;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.entity.ApiParamPO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * ApiParam DTO
 * @author xuyufeng
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
public class ResultOfApiParamDTO extends ResultDTO {

  @JsonProperty("apiParams")
  @JsonPropertyDescription("全部参数")
  @ApiModelProperty(value = "全部参数")
  private List<ApiParamPO> apiParams;
  
  /**
   * 返回参数列表对象
   * @param apiParams
   * @return
   */
  public ResultOfApiParamDTO apiParams(List<ApiParamPO> apiParams) {
    this.apiParams = apiParams;
    return this;
  }
}
