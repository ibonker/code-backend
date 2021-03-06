/**
 * 
 */
package org.hotpotmaterial.code.entity;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import org.hotpotmaterial.code.common.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wenxing
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "datasource")
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
public class DatasourcePO extends BaseEntity {

  /**
   * 
   */
  private static final long serialVersionUID = -6390026672973467014L;

  /**
   * 数据库密码
   */
  @NotNull
  @Column(name = "dbpassword")
  @JsonProperty(value = "dbpassword")
  @JsonPropertyDescription("数据库密码")
  private String dbpassword;

  /**
   * 数据库链接
   */
  @NotNull
  @Column(name = "dburl")
  @JsonProperty(value = "dburl")
  @JsonPropertyDescription("数据库链接")
  private String dburl;

  /**
   * 数据库地址
   */
  @NotNull
  @Column(name = "dbuser")
  @JsonProperty(value = "dbuser")
  @JsonPropertyDescription("数据库地址")
  private String dbuser;

  /**
   * jdbc driver
   */
  @NotNull
  @Column(name = "dbdriver")
  @JsonProperty(value = "dbdriver")
  @JsonPropertyDescription("jdbc driver")
  private String dbdriver;

  /**
   * 数据库类型
   */
  @NotNull
  @Column(name = "dbtype")
  @JsonProperty(value = "dbtype")
  @JsonPropertyDescription("数据库类型")
  private String dbtype;

  /**
   * 测试SQL
   */
  @Column(name = "validation_query")
  @JsonProperty(value = "validationQuery", access = Access.READ_ONLY)
  @JsonPropertyDescription("测试SQL")
  private String validationQuery = Constants.DATASOURCE_CHECK_QUERY;

  /**
   * 项目编号
   */
  @Column(name = "project_id")
  @JsonProperty("projectId")
  @JsonPropertyDescription("项目编号")
  private String projectId;
  
  /**
   * 数据库名称
   */
  @NotNull
  @Column(name = "name")
  @JsonProperty(value = "name", required = true)
  @JsonPropertyDescription("数据库名称")
  private String name;
  
  /**
   * 数据库名称对应包名
   */
  @Column(name = "package_name")
  @JsonProperty(value = "packageName", required = false)
  @JsonPropertyDescription("数据库名称对应包名")
  private String packageName;
  
  /**
   * table map
   */
  @Transient
  @JsonIgnore
  private Map<String, TablePO> tableMaps = Maps.newHashMap();
  
  /**
   * 用于class名前缀
   * @return
   */
  public String getUpperCamelPackageName() {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.name);
  }
  
  /**
   * 用于bean名前缀
   * @return
   */
  public String getLowerCamelPackageName() {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.name);
  }

}
