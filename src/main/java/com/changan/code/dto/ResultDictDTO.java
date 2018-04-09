/**
 * 
 */
package com.changan.code.dto;

import java.util.List;
import java.util.Map;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.entity.DictTypePO;
import com.changan.code.entity.DictValuePO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author xuyufeng
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(Include.NON_NULL)
public class ResultDictDTO extends ResultDTO{

  @JsonProperty("dictTypeAndValue")
  @JsonPropertyDescription("返回dictTypeAndValue")
  @ApiModelProperty(value = "返回dictTypeAndValue")
  private List<Map<String, Object>> dictTypeAndValue; 
  
  @JsonProperty("dictType")
  @JsonPropertyDescription("返回dictType")
  @ApiModelProperty(value = "返回dictType")
  private DictTypePO dictType;
  
  @JsonProperty("dictTypes")
  @JsonPropertyDescription("返回dictTypes")
  @ApiModelProperty(value = "返回dictTypes")
  private List<DictTypePO> dictTypes;
  
  @JsonProperty("dictValue")
  @JsonPropertyDescription("返回dictValue")
  @ApiModelProperty(value = "返回dictValue")
  private DictValuePO dictValue;
  
  @JsonProperty("dictValues")
  @JsonPropertyDescription("返回dictValues")
  @ApiModelProperty(value = "返回dictValues")
  private List<DictValuePO> dictValues;
  
  @JsonProperty("isUpdate")
  @JsonPropertyDescription("返回isUpdate")
  @ApiModelProperty(value = "返回isUpdate")
  private String isUpdate;
  
  @JsonProperty("version")
  @JsonPropertyDescription("返回version")
  @ApiModelProperty(value = "返回version")
  private Integer version;
  
  public ResultDictDTO DictTypeAndValue(List<Map<String, Object>> dictTypeAndValue){
    this.dictTypeAndValue = dictTypeAndValue;
    return this;
  }
  
  public ResultDictDTO DictIsUpdate(String isUpdate){
    this.isUpdate = isUpdate;
    return this;
  }
  
  public ResultDictDTO DictVersion(Integer version){
    this.version = version;
    return this;
  }
  
  public ResultDictDTO DictType(DictTypePO dictType){
    this.dictType = dictType;
    return this;
  }
  
  public ResultDictDTO DictTypes(List<DictTypePO> dictTypes){
    this.dictTypes = dictTypes;
    return this;
  }
  
  public ResultDictDTO DictValues(List<DictValuePO> dictValues){
    this.dictValues = dictValues;
    return this;
  }
  
  public ResultDictDTO DictValue(DictValuePO dictValue){
    this.dictValue = dictValue;
    return this;
  }
}
