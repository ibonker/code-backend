package org.hotpotmaterial.code.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "party_project")
@Entity
@EntityListeners(value = { AuditingEntityListener.class })
@JsonInclude(Include.NON_NULL)
public class PartyProjectPO extends BaseEntity {

	@Column(name = "project_id")
	@JsonProperty("projectId")
	@JsonPropertyDescription("项目ID")
	private String projectId;// 项目ID

	@Column(name = "party_id")
	@JsonProperty("partyId")
	@JsonPropertyDescription("组织ID或用户ID")
	private String partyId;// 组织ID或用户ID
	
	@Column(name = "style")
	@JsonProperty("style")
	@JsonPropertyDescription("用户标识  0:用户 1:组织")
	private String style;// "用户标识  0:用户 1:组织"
	
	@Column(name = "flag")
	@JsonProperty("flag")
	@JsonPropertyDescription("权限标识 0:该项目是用户自己创建的  1:该项目权限是用户加入的")
	private String flag;// "权限标识 0:该项目是用户自己创建的  1:该项目权限是用户加入的"

	public PartyProjectPO() {

	}

	
	public PartyProjectPO(String projectId) {
		this.projectId = projectId;
	}

	/**
	 * 更新属性
	 * 
	 * @param versionPO
	 * @return
	 */
	public PartyProjectPO updateAttrs(PartyProjectPO partyProjectPO) {
		this.projectId = partyProjectPO.getProjectId();
		this.partyId = partyProjectPO.getPartyId();
		return this;
	}
}
