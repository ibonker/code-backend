package org.hotpotmaterial.code.dto;

import java.util.List;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.entity.ApiObjPO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * ApiObj DTO
 * @author xuyufeng
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
public class ResultOfApiObjDTO extends ResultDTO {

  @JsonProperty("apiObjs")
  @JsonPropertyDescription("全部方法")
  @ApiModelProperty(value = "全部方法")
  private List<ApiObjPO> apiObjs;

  @JsonProperty("apiObj")
  @JsonPropertyDescription("方法")
  @ApiModelProperty(value = "方法")
  private ApiObjPO apiObj;

  /**
   * 返回apiObj列表对象
   * @param apiObjs
   * @return
   */
  public ResultOfApiObjDTO apiObjs(List<ApiObjPO> apiObjs) {
    this.apiObjs = apiObjs;
    return this;
  }

  /**
   * 返回apiobj对象
   * @param apiObj
   * @return
   */
  public ResultOfApiObjDTO apiObj(ApiObjPO apiObj) {
    this.apiObj = apiObj;
    return this;
  }
}
