package org.hotpotmaterial.code.dto;

import java.util.List;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.entity.LoginInfoPO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
public class ResultOfLoginInfoDTO extends ResultDTO {

	@JsonProperty("loginInfoList")
	@JsonPropertyDescription("loginInfoList")
	@ApiModelProperty(value = "loginInfoList")
	private List<LoginInfoPO> loginInfoList;

	/**
	 * 版本明细
	 * 
	 * @param versionList
	 * @return
	 */
	public ResultOfLoginInfoDTO versionList(List<LoginInfoPO> loginInfoList) {
		this.loginInfoList = loginInfoList;
		return this;
	}
}
