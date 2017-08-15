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
public class ColumnPO extends BaseEntity {
  /**
   * 
   */
  private static final long serialVersionUID = 3980144310483390653L;
  
  private String tableId; // 归属表
  private String name; // 列名
  private String comments; // 描述
  private String jdbcType; // JDBC类型
  private String javaType; // JAVA类型
  private String javaField; // JAVA字段名
  private String isPk; // 是否主键（1：主键）
  private String isNull; // 是否可为空（1：可为空；0：不为空）
  private String readOnly; // 是否只读
  private String pattern; // 正则表达式
  private Integer lengthMin; // 字符串最小长度
  private Integer lengthMax; // 字符串最大长度
  private Integer min; // 最小值
  private Integer max; // 最大值

}
