/**
 * 
 */
package org.hotpotmaterial.code.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wenxing
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "api_base")
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
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
  @JsonProperty("basePath")
  private String basePath; // 根路徑

  @Column(name = "description")
  @JsonProperty("description")
  private String description; // 描述

  @Transient
  private List<ApiObjPO> apiObjs; // api方法

  /**
   * 更新属性
   * 
   * @param apiBasePO
   * @return
   */
  public ApiBasePO updateAttrs(ApiBasePO apiBase) {
    this.versionName = apiBase.getVersionName();
    this.basePath = apiBase.getBasePath();
    this.description = apiBase.getDescription();

    return this;
  }
}
