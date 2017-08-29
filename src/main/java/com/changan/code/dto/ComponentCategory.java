/**
 * 
 */
package com.changan.code.dto;

import java.util.List;

import lombok.Data;

/**
 * @author wenxing
 *
 */
@Data
public class ComponentCategory {
  
  // 中文分类
  private String category;
  
  // 组件列表
  private List<Component> components;
  
  // 是否多选
  private String isMultiSelect;

}
