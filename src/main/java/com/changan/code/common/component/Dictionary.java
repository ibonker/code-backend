package com.changan.code.common.component;

import com.changan.code.common.Constants;

public enum Dictionary {
  
  dictionary("是"), undictionary("否");
  
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
