/**
 * 
 */
package com.changan.code.common.component;

import com.changan.code.common.Constants;

/**
 * @author wenxing
 *
 */
public enum Consul {
  
  consuldiscovery("使用consul服务发现"), consulconfig("使用consul配置中心");
  
  private final String cname;

  private Consul(String cname) {
    this.cname = cname;
  }

  public String getCname() {
    return cname;
  }
  
  public static String getTypeCname() {
    return "Consul组件";
  }
  
  public static String isMultiSelect() {
    return Constants.IS_ACTIVE;
  }

}
