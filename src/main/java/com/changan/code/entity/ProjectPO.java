/**
 * 
 */
package com.changan.code.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import com.changan.code.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wenxing
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "project")
@MappedSuperclass
public class ProjectPO extends BaseEntity {/**
   * 
   */
  private static final long serialVersionUID = -3154012195551439895L;
  
  @Column(name = "name")
  @JsonProperty("name")
  private String name; // 项目名称
  
  @Column(name = "packages")
  @JsonProperty("packages")
  private String packages; // 项目包名
  
  @Column(name = "components")
  @JsonProperty("components")
  private String components; // 项目组件
  
  @Column(name = "description")
  @JsonProperty("description")
  private String description; // 项目描述

}
