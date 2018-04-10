package org.hotpotmaterial.code.common.component;

import org.hotpotmaterial.code.common.Constants;

public enum Dictionary {
  
  dictionary("是(将创建dict_value和dict_type数据库表)"), undictionary("否");
  
  private final String cname;

  private Dictionary(String cname) {
    this.cname = cname;
  }

  public String getCname() {
    return cname;
  }
  
  public static String getTypeCname() {
    return "字典表配置组件";
  }
  
  public static String isMultiSelect() {
    return Constants.IS_INACTIVE;
  }

}
