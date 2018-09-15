package org.hotpotmaterial.code.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Hotpotmaterial-Code2
 * 实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "context")
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
public class ContextPO extends BaseEntity {
  private static final long serialVersionUID = 1L;

  
  @Column(name = "directory_id")
  @JsonProperty(value = "directoryId")
  @JsonPropertyDescription("目录菜单ID")
  private String directoryId;
  
  @Column(name = "content")
  @JsonProperty(value = "content")
  @JsonPropertyDescription("文档内容")
  private String content;
  
  
  @Column(name = "created_by")
  @JsonProperty(value = "createdBy")
  @JsonPropertyDescription("创建人")
  private String createdBy;
  
  
  @Column(name = "updated_by")
  @JsonProperty(value = "updatedBy")
  @JsonPropertyDescription("修改人")
  private String updatedBy;
  
}