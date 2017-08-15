/**
 * 
 */
package com.changan.code.entity;

import com.changan.code.common.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wenxing
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class DatasourcePO extends BaseEntity {

  /**
   * 
   */
  private static final long serialVersionUID = -6390026672973467014L;

  /**
   * 数据库密码
   */
  private String dbpassword;

  /**
   * 数据库链接
   */
  private String dburl;

  /**
   * 数据库地址
   */
  private String dbuser;

  /**
   * jdbc driver
   */
  private String dbdriver;

  /**
   * 数据库类型
   */
  private String dbtype;

  /**
   * 测试SQL
   */
  private String validationQuery;

  /**
   * 项目编号
   */
  private String projectId;

}
