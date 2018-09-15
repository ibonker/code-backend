package org.hotpotmaterial.code.security.dto;

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
  * @author TitanCode2
  *  ddemo.AttachmentPO详情实体
  */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(Include.NON_NULL)
public class ResultOfUserSeniorDTO extends ResultDTO{

	@JsonProperty(value = "hotpotUserSeniorDTO")
    @JsonPropertyDescription("hotpotUserSeniorDTO对象")               
    @ApiModelProperty(value = "hotpotUserSeniorDTO对象")
	private HotpotUserSeniorDTO hotpotUserSeniorDTO;
	
	public ResultOfUserSeniorDTO hotpotUserSeniorDTO (HotpotUserSeniorDTO hotpotUserSeniorDTO){
      this.hotpotUserSeniorDTO = hotpotUserSeniorDTO;
      return this;
    }
}