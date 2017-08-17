/**
 * 
 */
package com.changan.code.entity;

import java.util.List;

import com.changan.code.common.BaseEntity;
import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wenxing
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class TablePO extends BaseEntity {
  /**
   * 
   */
  private static final long serialVersionUID = 761964282772844740L;
  
  private String name; // 名称
  private String comments; // 描述
  private String className; // 实体类名称
  private String datasourceId; // 数据源
  private String isAutoCrud; // 是否自动生成CRUD的SQL、接口和API（1：是；0：否）

  private List<ColumnPO> columnList = Lists.newArrayList(); // 表列
  private List<String> pkList; // 当前表主键列表

}
