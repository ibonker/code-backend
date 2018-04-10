package org.hotpotmaterial.code.dto;

import java.util.List;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.entity.TransferObjFieldPO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * transferField DTO
 * @author xuyufeng
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
public class ResultOfTransferObjFieldDTO extends ResultDTO {

  @JsonProperty("transferObjField")
  @JsonPropertyDescription("DTO实体属性")
  @ApiModelProperty(value = "DTO实体")
  private TransferObjFieldPO transferObjField;

  @JsonProperty("transferObjFields")
  @JsonPropertyDescription("所有DTO实体属性")
  @ApiModelProperty(value = "所有DTO实体属性")
  private List<TransferObjFieldPO> transferObjFields;

  /**
   * 返回transferObjField对象
   * @param transferObjField
   * @return
   */
  public ResultOfTransferObjFieldDTO transferObjField(TransferObjFieldPO transferObjField) {
    this.transferObjField = transferObjField;
    return this;
  }

  /**
   * 返回transferObjField列表对象
   * @param transferObjFields
   * @return
   */
  public ResultOfTransferObjFieldDTO transferObjFields(List<TransferObjFieldPO> transferObjFields) {
    this.transferObjFields = transferObjFields;
    return this;
  }
}
