package org.hotpotmaterial.code.security.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hotpotmaterial.code.entity.BaseEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author TitanCode2
 * 实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "hotpot_role")
public class HotpotRolePO extends BaseEntity{

  private static final long serialVersionUID = 1L;
  
  @Size(max = 255)
  @Column(name = "role_name")
  @JsonProperty(value = "roleName")
  @JsonPropertyDescription("")
  private String roleName;
  
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
  
  
}