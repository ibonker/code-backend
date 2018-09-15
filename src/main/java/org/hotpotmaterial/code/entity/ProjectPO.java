/**
 * 
 */
package org.hotpotmaterial.code.entity;

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

import org.hotpotmaterial.code.annotation.JsonSchemaLink;
import org.hotpotmaterial.code.common.Constants;
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
  
  @Column(name = "isdictionary")
  @JsonProperty("isdictionary")
  @JsonPropertyDescription("是否启用字典表")
  private String isdictionary = Constants.DATA_IS_NORMAL; //是否启用字典表
  
  @Column(name = "app_id")
  @JsonProperty("appId")
  @JsonPropertyDescription("应用id")
  private String appId;//应用id
  
  @Column(name = "app_pub_key")
  @JsonProperty("appPubKey")
  @JsonPropertyDescription("应用公钥")
  private String appPubKey;//应用公钥
  
  @Column(name = "user_id")
  @JsonProperty("userId")
  @JsonPropertyDescription("用户id")
  private String userId;//用户id
  
  @Column(name = "user_name")
  @JsonProperty("userName")
  @JsonPropertyDescription("用户名称")
  private String userName;//用户名称
  
  @Column(name = "department_name")
  @JsonProperty("departmentName")
  @JsonPropertyDescription("部门名称")
  private String departmentName; //部门名称
  
  @Column(name = "modify_id")
  @JsonProperty("modifyId")
  @JsonPropertyDescription("修改人Id")
  private String modifyId; //修改人Id
  
  @Column(name = "modify_name")
  @JsonProperty("modifyName")
  @JsonPropertyDescription("修改人名称")
  private String modifyName; //修改人名称
  
  @Column(name = "modify_ip")
  @JsonProperty("modifyIp")
  @JsonPropertyDescription("修改人ip")
  private String modifyIp; //修改人ip
  
  @Column(name = "created_by")
  @JsonProperty("createdBy")
  @JsonPropertyDescription("创建人")
  private String createdBy; //创建人
  
  @Column(name = "flag")
  @JsonProperty("flag")
  @JsonPropertyDescription("星星标识 0:未标识 1:标识")
  private String flag = "0"; 
  
  @Transient
  @JsonProperty("tableNames")
  @JsonPropertyDescription("数据源表名")
  private List<String> tableNames; //该项目对应数据库下的所有表名
  
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
    this.isdictionary = newProject.getIsdictionary();
    this.userId = newProject.getUserId();
    this.userName = newProject.getUserName();
    this.departmentName = newProject.getDepartmentName();
    
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
