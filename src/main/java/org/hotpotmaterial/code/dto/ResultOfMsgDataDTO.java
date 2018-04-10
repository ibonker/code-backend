/**
 * 
 */
package org.hotpotmaterial.code.dto;

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
public class ResultOfMsgDataDTO extends ResultDTO {
  
  // 返回信息数据
  @JsonProperty("msgData")
  @JsonPropertyDescription("信息数据")
  @ApiModelProperty(value = "信息数据")
  private String msgData;

  public ResultOfMsgDataDTO msgData(String msgData) {
    this.msgData = msgData;
    return this;
  }

}
