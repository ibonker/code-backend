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
@Entity(name = "Column")
@Table(name = "column")
public class ColumnPO extends BaseEntity {
  /**
   * 
   */
  private static final long serialVersionUID = 3980144310483390653L;
  
  @Column(name = "table_id")
  @JsonProperty("tableId")
  private String tableId; // 归属表
  
  @Column(name = "name")
  @JsonProperty("name")
  private String name; // 列名
  
  @Column(name = "comments")
  @JsonProperty("comments")
  private String comments; // 描述
  
  @Column(name = "jdbc_type")
  @JsonProperty("jdbcType")
  private String jdbcType; // JDBC类型
  
  @Column(name = "java_type")
  @JsonProperty("javaType")
  private String javaType; // JAVA类型
  
  @Column(name = "java_field")
  @JsonProperty("javaField")
  private String javaField; // JAVA字段名
  
  @Column(name = "is_pk")
  @JsonProperty("isPk")
  private String isPk; // 是否主键（1：主键）
  
  @Column(name = "not_null")
  @JsonProperty("notNull")
  private String notNull; // 是否可为空（1：不为空；0：可为空）
  
  @Column(name = "read_only")
  @JsonProperty("readOnly")
  private String readOnly; // 是否只读（1：只读；0：可修改）
  
  @Column(name = "pattern")
  @JsonProperty("pattern")
  private String pattern; // 正则表达式
  
  @Column(name = "length_min")
  @JsonProperty("lengthMin")
  private Integer lengthMin; // 字符串最小长度
  
  @Column(name = "length_max")
  @JsonProperty("lengthMax")
  private Integer lengthMax; // 字符串最大长度
  
  @Column(name = "min")
  @JsonProperty("min")
  private Integer min; // 最小值
  
  @Column(name = "max")
  @JsonProperty("max")
  private Integer max; // 最大值

}
