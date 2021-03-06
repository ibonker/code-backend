package org.hotpotmaterial.code.security.dto;

import org.hotpotmaterial.annotation.RelationDesc;
import org.hotpotmaterial.annotation.SeniorDto;
import org.hotpotmaterial.code.security.entity.HotpotOrganizationPO;
import org.hotpotmaterial.code.security.entity.HotpotUserPO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

 /**
  * @author Hotpotmaterial-Code2
  *  ddemo.HotpotUserPO高级关联查询
  */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@JsonInclude(Include.NON_NULL)
@SeniorDto(name = "hotpot_user_senior", relation = {
    @RelationDesc(name = "relation_1", description = "hotpot_user与hotpot_organization高级关联查询")
})
public class HotpotUserSeniorDTO {

	@JsonProperty(value = "hotpotUser")
	@JsonPropertyDescription("hotpot_user对象")				
	@ApiModelProperty(value = "hotpot_user对象")
	private HotpotUserPO hotpotUser;
	
	@JsonProperty(value = "hotpotOrganization")
	@JsonPropertyDescription("hotpot_organization对象")				
	@ApiModelProperty(value = "hotpot_organization对象")
	private HotpotOrganizationPO hotpotOrganization;
	
	@JsonProperty(value = "newPassword")
    @JsonPropertyDescription("newPassword对象")               
    @ApiModelProperty(value = "newPassword对象")
	private String newPassword;
	
	public HotpotUserSeniorDTO hotpotUser (HotpotUserPO hotpotUser){
	  this.hotpotUser = hotpotUser;
	  return this;
	}
	public HotpotUserSeniorDTO hotpotOrganization (HotpotOrganizationPO hotpotOrganization){
	  this.hotpotOrganization = hotpotOrganization;
	  return this;
	}
}