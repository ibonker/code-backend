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
  
  nosecurity("无安全配置"), localsecurity("本地安全配置（该配置将创建完整认证和组织体系）"), rescentersecurity("资源中心安全配置");
  
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
