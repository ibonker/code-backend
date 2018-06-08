package org.hotpotmaterial.code.dto;

import java.util.Map;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

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
public class ResultOfPreviewDTO extends ResultDTO {


  @JsonProperty("fileList")
  @JsonPropertyDescription("fileList")
  @ApiModelProperty(value = "fileList")
  private Map<String, String> fileList;
  
  @JsonProperty("url")
  @JsonPropertyDescription("url")
  @ApiModelProperty(value = "url")
  private String url;

  /**
   * 返回ApiBase列表对象
   * @param apiBases
   * @return
   */
  public ResultOfPreviewDTO fileList(Map<String, String> fileList, String url) {
    this.fileList = fileList;
    this.url = url;
    return this;
  }

}
