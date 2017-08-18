/**
 * 
 */
package com.changan.code.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.changan.code.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wenxing
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "table")
@MappedSuperclass
public class TablePO extends BaseEntity {
  /**
   * 
   */
  private static final long serialVersionUID = 761964282772844740L;
  
  @Column(name = "name")
  @JsonProperty("name")
  private String name; // 名称
  
  @Column(name = "comments")
  @JsonProperty("comments")
  private String comments; // 描述
  
  @Column(name = "class_name")
  @JsonProperty("className")
  private String className; // 实体类名称
  
  @Column(name = "datasource_id")
  @JsonProperty("datasourceId")
  private String datasourceId; // 数据源
  
  @Column(name = "is_auto_crud")
  @JsonProperty("isAutoCrud")
  private String isAutoCrud; // 是否自动生成CRUD的SQL、接口和API（1：是；0：否）

  @Transient
  private List<ColumnPO> columnList = Lists.newArrayList(); // 表列

}
