package org.hotpotmaterial.code.security.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hotpotmaterial.code.entity.BaseEntity;
import org.hotpotmaterial.code.security.dto.HotpotUserSeniorDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author TitanCode2 实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "hotpot_user")
public class HotpotUserPO extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Size(max = 36)
	@Column(name = "user_name")
	@JsonProperty(value = "userName")
	@JsonPropertyDescription("")
	private String userName;

	@Size(max = 255)
	@Column(name = "name")
	@JsonProperty(value = "name")
	@JsonPropertyDescription("")
	private String name;

	@Size(max = 255)
	@Column(name = "password")
	@JsonProperty(value = "password")
	@JsonPropertyDescription("")
	private String password;

	@Size(max = 36)
	@Column(name = "org_id")
	@JsonProperty(value = "orgId")
	@JsonPropertyDescription("")
	private String orgId;

	@Size(max = 3)
	@Column(name = "del_flag")
	@JsonProperty(value = "delFlag")
	@JsonPropertyDescription("")
	private String delFlag;

	@JsonProperty(value = "flag")
	@JsonPropertyDescription("标识 0:创建的  1:加入的")
	@ApiModelProperty(value = "flag对象")
	private String flag;

}