package org.hotpotmaterial.code.security.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hotpotmaterial.code.entity.BaseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@Table(name = "hotpot_function")
public class HotpotFunctionPO extends BaseEntity {
  private static final long serialVersionUID = 1L;

  
  @Size(max = 255)
  @Column(name = "function_name")
  @JsonProperty(value = "functionName")
  @JsonPropertyDescription("")
  private String functionName;
  
  @Size(max = 255)
  @Column(name = "code")
  @JsonProperty(value = "code")
  @JsonPropertyDescription("")
  private String code;
  
  @Size(max = 255)
  @Column(name = "url")
  @JsonProperty(value = "url")
  @JsonPropertyDescription("")
  private String url;
  
  @Size(max = 3)
  @Column(name = "is_menu")
  @JsonProperty(value = "isMenu")
  @JsonPropertyDescription("")
  private String isMenu;
  
  @Size(max = 255)
  @Column(name = "description")
  @JsonProperty(value = "description")
  @JsonPropertyDescription("")
  private String description;
  
  @Size(max = 3)
  @Column(name = "enabled")
  @JsonProperty(value = "enabled")
  @JsonPropertyDescription("")
  private String enabled;
  
  @Size(max = 36)
  @Column(name = "parent_id")
  @JsonProperty(value = "parentId")
  @JsonPropertyDescription("")
  private String parentId;
  
  @Size(max = 255)
  @Column(name = "image1_id")
  @JsonProperty(value = "image1Id")
  @JsonPropertyDescription("")
  @JsonInclude
  private String image1Id;
  
  @Size(max = 255)
  @Column(name = "image2_id")
  @JsonProperty(value = "image2Id")
  @JsonPropertyDescription("")
  @JsonInclude
  private String image2Id;
  
  
  
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
  
}