/**
 * 
 */
package com.changan.code.dto;

import java.util.List;

import com.changan.code.entity.ColumnPO;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wenxing
 *
 */
@Data
@AllArgsConstructor
public class SeniorDtoAttribute {

  private String name; // 属性名
  
  private String tableName; // 属性对应的表名

  private String type; // 属性类型

  private List<ColumnPO> columns; // 属性包含的字段

}
