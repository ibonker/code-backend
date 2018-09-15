/**
 * 
 */
package org.hotpotmaterial.code.dto;

import lombok.Data;

/**
 * @author Administrator
 *
 */
@Data
public class RequestAppinfoDTO {
  
  // 申请应用名称
  private String appName;
  // 申请应用描述
  private String appDesc;
  // 应用id
  private String appId;

}
