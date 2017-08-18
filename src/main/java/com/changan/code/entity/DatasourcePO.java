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
@Table(name = "datasource")
@MappedSuperclass
public class DatasourcePO extends BaseEntity {

  /**
   * 
   */
  private static final long serialVersionUID = -6390026672973467014L;

  /**
   * 数据库密码
   */
  @Column(name = "dbpassword")
  @JsonProperty("dbpassword")
  private String dbpassword;

  /**
   * 数据库链接
   */
  @Column(name = "dburl")
  @JsonProperty("dburl")
  private String dburl;

  /**
   * 数据库地址
   */
  @Column(name = "dbuser")
  @JsonProperty("dbuser")
  private String dbuser;

  /**
   * jdbc driver
   */
  @Column(name = "dbdriver")
  @JsonProperty("dbdriver")
  private String dbdriver;

  /**
   * 数据库类型
   */
  @Column(name = "dbtype")
  @JsonProperty("dbtype")
  private String dbtype;

  /**
   * 测试SQL
   */
  @Column(name = "validatio_query")
  @JsonProperty("validationQuery")
  private String validationQuery;

  /**
   * 项目编号
   */
  @Column(name = "project_id")
  @JsonProperty("projectId")
  private String projectId;

}
