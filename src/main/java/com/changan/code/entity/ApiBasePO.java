/**
 * 
 */
package com.changan.code.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Entity(name = "ApiBase")
@Table(name = "api_base")
public class ApiBasePO extends BaseEntity {
  /**
   * 
   */
  private static final long serialVersionUID = 5086146945294192788L;
	
  @Column(name = "project_id")
  @JsonProperty("projectId")
  private String projectId; // 項目id
  
  @Column(name = "version_name")
  @JsonProperty("versionName")
  private String versionName; // 版本
  
  @Column(name = "base_path")
  @JsonProperty("base_path")
  private String basePath; // 根路徑
  
  @Column(name = "description")
  @JsonProperty("description")
  private String description; // 描述

}
