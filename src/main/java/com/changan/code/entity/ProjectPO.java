/**
 * 
 */
package com.changan.code.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.changan.code.annotation.JsonSchemaLink;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wenxing
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "project")
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
public class ProjectPO extends BaseEntity {
  /**
   * 
   */
  private static final long serialVersionUID = -3154012195551439895L;
  
  @NotNull
  @Column(name = "name")
  @JsonProperty("name")
  @JsonPropertyDescription("项目名称")
  private String name; // 项目名称
  
  @NotNull
  @Column(name = "packages")
  @JsonProperty("packages")
  @JsonPropertyDescription("项目包名")
  private String packages; // 项目包名
  
  @Column(name = "components")
  @JsonProperty("components")
  @JsonPropertyDescription("项目组件")
  private String components; // 项目组件
  
  @Column(name = "description")
  @JsonProperty("description")
  @JsonPropertyDescription("项目描述")
  private String description; // 项目描述
  
  @Transient
  @JsonProperty("datasources")
  @JsonPropertyDescription("数据源列表")
  @JsonSchemaLink(href = "/codegen/api/v1/datasources")
  private List<DatasourcePO> datasources; // 数据源
  
  @Transient
  @JsonProperty("componentsMap")
  @JsonPropertyDescription("项目组件选中")
  private Map<String, Object> componentsMap; // 项目组件选中
  
  /**
   * 可以更新的属性
   * @param newProject
   * @return
   */
  public ProjectPO updateAttrs(ProjectPO newProject) {
    this.components = newProject.getComponents();
    this.description = newProject.getDescription();
    this.packages = newProject.getPackages();
    this.name = newProject.getName();
    this.datasources = newProject.getDatasources();
    
    return this;
  }
  
  /**
   * 组件列表
   * @return
   */
  public List<String> getComponentList() {
    if (StringUtils.isBlank(components)) {
      return null;
    }
    String[] componentarr = this.components.split(",");
    return Arrays.asList(componentarr);
  }

}
