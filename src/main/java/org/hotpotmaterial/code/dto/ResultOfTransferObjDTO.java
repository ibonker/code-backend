package org.hotpotmaterial.code.dto;

import java.util.List;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.common.Constants;
import org.hotpotmaterial.code.entity.TransferObjPO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * TransferObj DTO 
 * @author xuyufeng
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
public class ResultOfTransferObjDTO extends ResultDTO {
  
  @JsonProperty("isDto")
  @JsonPropertyDescription("是否为DTO实体")
  @ApiModelProperty(value = "是否为DTO实体")
  private String isDto = Constants.IS_ACTIVE;

  @JsonProperty("transferObj")
  @JsonPropertyDescription("DTO实体")
  @ApiModelProperty(value = "DTO实体")
  private TransferObjPO transferObj;

  @JsonProperty("transferObjs")
  @JsonPropertyDescription("所有DTO实体")
  @ApiModelProperty(value = "所有DTO实体")
  private List<TransferObjPO> transferObjs;
  
  /**
   * 返回是否为dto
   * @param isDto
   * @return
   */
  public ResultOfTransferObjDTO isDto(String isDto) {
    this.isDto = isDto;
    return this;
  }

  /**
   * 返回transferObj对象
   * @param transferObj
   * @return
   */
  public ResultOfTransferObjDTO transferObj(TransferObjPO transferObj) {
    this.transferObj = transferObj;
    return this;
  }

  /**
   * 返回transferObj列表对象
   * @param transferObjs
   * @return
   */
  public ResultOfTransferObjDTO transferObjs(List<TransferObjPO> transferObjs) {
    this.transferObjs = transferObjs;
    return this;
  }
}
