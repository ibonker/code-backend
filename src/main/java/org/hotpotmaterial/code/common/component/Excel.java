/**
 * 
 */
package org.hotpotmaterial.code.common.component;

import org.hotpotmaterial.code.common.Constants;

/**
 * @author Administrator
 *
 */
public enum Excel {
  
  excelEnabled("是"), excelDisabled("否");
  
  private final String cname;

  private Excel(String cname) {
    this.cname = cname;
  }

  public String getCname() {
    return cname;
  }
  
  public static String getTypeCname() {
    return "启用Excel导入导出组件";
  }
  
  public static String isMultiSelect() {
    return Constants.IS_INACTIVE;
  }

}
