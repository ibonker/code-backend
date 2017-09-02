/**
 * 
 */
package com.changan.code.common;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author wenxing
 *
 */
public enum BaseDTO {
  
  ResultDTO("com.changan.anywhere.common.mvc.page.rest.response.ResultDTO"), 
  ResultPageDTO("com.changan.anywhere.common.mvc.page.rest.response.ResultPageDTO"), 
  PageDTO("com.changan.anywhere.common.mvc.page.rest.request.PageDTO"), 
  ResultJsonSchemaDTO("com.changan.anywhere.common.mvc.page.rest.response.ResultJsonSchemaDTO");
  
  // 包名
  private final String packageName;

  private BaseDTO(String packageName) {
    this.packageName = packageName;
  }

  public String getPackageName() {
    return packageName;
  }
  
  /**
   * 获取所有枚举名称
   * @return
   */
  public static List<String> getNameList() {
    List<String> result = Lists.newArrayList();
    for (BaseDTO basedto : BaseDTO.values()) {
      result.add(basedto.name());
    }
    return result;
  }
  
}
