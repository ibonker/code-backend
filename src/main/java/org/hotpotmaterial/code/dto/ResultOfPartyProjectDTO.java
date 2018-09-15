package org.hotpotmaterial.code.dto;

import java.util.List;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.entity.PartyProjectPO;
import org.hotpotmaterial.code.security.dto.HotpotUserSeniorDTO;
import org.hotpotmaterial.code.security.entity.HotpotOrganizationPO;

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
public class ResultOfPartyProjectDTO extends ResultDTO {

	@JsonProperty("partyprojectList")
	@JsonPropertyDescription("partyprojectList")
	@ApiModelProperty(value = "partyprojectList")
	private List<PartyProjectPO> partyprojectList;

	@JsonProperty("infoList")
	@JsonPropertyDescription("infoList")
	@ApiModelProperty(value = "infoList")
	private List<HotpotUserSeniorDTO> infoList;

	@JsonProperty("orgsList")
	@JsonPropertyDescription("orgsList")
	@ApiModelProperty(value = "orgsList")
	private List<HotpotOrganizationPO> orgsList;

	/**
	 * 版本明细
	 * 
	 * @param partyprojectList
	 * @return
	 */
	public ResultOfPartyProjectDTO partyprojectList(List<PartyProjectPO> partyprojectList) {
		this.partyprojectList = partyprojectList;
		return this;
	}

	/**
	 * 对应的用户信息
	 * 
	 * @param info
	 * @return
	 */
	public ResultOfPartyProjectDTO infoList(List<HotpotUserSeniorDTO> infoList) {
		this.infoList = infoList;
		return this;
	}

	public ResultOfPartyProjectDTO departmentList(List<HotpotOrganizationPO> orgsList) {
		this.orgsList = orgsList;
		return this;
	}
}
