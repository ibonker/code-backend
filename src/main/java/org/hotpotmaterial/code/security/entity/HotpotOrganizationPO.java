package org.hotpotmaterial.code.security.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hotpotmaterial.code.entity.BaseEntity;

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
@Table(name = "hotpot_organization")
public class HotpotOrganizationPO extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Size(max = 255)
	@Column(name = "org_full_id")
	@JsonProperty(value = "orgFullId")
	@JsonPropertyDescription("")
	private String orgFullId;

	@Size(max = 255)
	@Column(name = "org_name")
	@JsonProperty(value = "orgName")
	@JsonPropertyDescription("")
	private String orgName;

	@Size(max = 255)
	@Column(name = "org_full_name")
	@JsonProperty(value = "orgFullName")
	@JsonPropertyDescription("")
	private String orgFullName;

	@Size(max = 36)
	@Column(name = "parent_id")
	@JsonProperty(value = "parentId")
	@JsonPropertyDescription("")
	private String parentId;

	@Size(max = 255)
	@Column(name = "description")
	@JsonProperty(value = "description")
	@JsonPropertyDescription("")
	private String description;

	@Size(max = 255)
	@Column(name = "created_by")
	@JsonProperty(value = "createdBy")
	@JsonPropertyDescription("")
	private String createdBy;

	@Size(max = 255)
	@Column(name = "updated_by")
	@JsonProperty(value = "updatedBy")
	@JsonPropertyDescription("")
	private String updatedBy;

	@Size(max = 3)
	@Column(name = "del_flag")
	@JsonProperty(value = "delFlag")
	@JsonPropertyDescription("")
	private String delFlag;

	@Column(name = "sort")
	@JsonProperty(value = "sort")
	@JsonPropertyDescription("")
	private Integer sort;

	@JsonProperty(value = "flag")
	@JsonPropertyDescription("标识 0:创建的  1:加入的")
	@ApiModelProperty(value = "flag对象")
	private String flag;

}