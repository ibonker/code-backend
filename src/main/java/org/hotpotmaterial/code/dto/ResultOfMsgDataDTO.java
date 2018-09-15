/**
 * 
 */
package org.hotpotmaterial.code.dto;

import java.util.List;
import java.util.Map;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.entity.TablePO;

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
public class ResultOfMsgDataDTO extends ResultDTO {
  
  // 返回信息数据
  @JsonProperty("msgData")
  @JsonPropertyDescription("信息数据")
  @ApiModelProperty(value = "信息数据")
  private String msgData;
  
  // 返回连接数据库的表明以及描述
  @JsonProperty("tables")
  @JsonPropertyDescription("表名以及描述")
  @ApiModelProperty(value = "表名以及描述")
  private Map<String,String> tables;

  public ResultOfMsgDataDTO msgData(String msgData) {
    this.msgData = msgData;
    return this;
  }
  public ResultOfMsgDataDTO tablesMap(Map<String,String> tables) {
	  this.tables = tables;
	  return this;
  }

}
