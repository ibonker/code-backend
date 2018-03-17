/**
 * 
 */
package com.changan.code.common.component;

import com.changan.code.common.Constants;

/**
 * @author wenxing
 *
 */
public enum UiConfig {
  
  uiConfigEnabled("是(将创建ui_config数据库表)"), uiConfigDisabled("否");
  
  private final String cname;

  private UiConfig(String cname) {
    this.cname = cname;
  }

  public String getCname() {
    return cname;
  }
  
  public static String getTypeCname() {
    return "使用数据库保存前端配置";
  }
  
  public static String isMultiSelect() {
    return Constants.IS_INACTIVE;
  }

}
