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
  private String moduleName; // 模块名
  private String functionName; // 功能名, 用于页面展示
  private String isLogicDel; // 是否是逻辑删除（1：物理删除；0：逻辑删除）
  private String cnName; // 中文名

  private List<ColumnPO> columnList = Lists.newArrayList(); // 表列

  private String nameLike; // 按名称模糊查询

  private List<String> pkList; // 当前表主键列表

}
