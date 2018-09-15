package org.hotpotmaterial.code.dto;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.entity.PlugsInfoPO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Hotpotmaterial-Code2 new.titancode.test.PlugsInfoPO详情实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(Include.NON_NULL)
public class ResultOfPlugsInfoDTO extends ResultDTO {

	@JsonProperty(value = "plugsInfo")
	@JsonPropertyDescription("plugs_info对象")
	@ApiModelProperty(value = "plugs_info对象")
	private PlugsInfoPO plugsInfo;

	public ResultOfPlugsInfoDTO plugsInfo(PlugsInfoPO plugsInfo) {
		this.plugsInfo = plugsInfo;
		return this;
	}
}