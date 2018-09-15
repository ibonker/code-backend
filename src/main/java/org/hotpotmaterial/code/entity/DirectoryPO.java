package org.hotpotmaterial.code.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Hotpotmaterial-Code2
 * 菜单目录实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "directory")
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
public class DirectoryPO extends BaseEntity {
  private static final long serialVersionUID = 1L;

  
  @Column(name = "pid")
  @JsonProperty(value = "pid")
  @JsonPropertyDescription("该目录父ID")
  private String pid;
  
  @Column(name = "name")
  @JsonProperty(value = "name")
  @JsonPropertyDescription("目录名称")
  private String name;
  
  @Column(name = "sort")
  @JsonProperty(value = "sort")
  @JsonPropertyDescription("排序")
  private String sort;
  
  @Column(name = "content_flag")
  @JsonProperty(value = "contentFlag")
  @JsonPropertyDescription("文档标识  0:该目录无文档 1:该目录有文档")
  private String contentFlag = "0";
  
  @Transient
  private List<DirectoryPO> child = new ArrayList<>();
  
}