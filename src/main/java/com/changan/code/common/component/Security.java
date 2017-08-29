/**
 * 
 */
package com.changan.code.common.component;

import com.changan.code.common.Constants;

/**
 * @author wenxing
 *
 */
public enum Security {
  
  nosecurity("无安全配置"), rescentersecurity("资源中心安全配置"), cacsecurity("长安认证中心安全配置");
  
  private final String cname;

  private Security(String cname) {
    this.cname = cname;
  }

  public String getCname() {
    return cname;
  }
  
  public static String getTypeCname() {
    return "安全组件";
  }
  
  public static String isMultiSelect() {
    return Constants.IS_INACTIVE;
  }

}
