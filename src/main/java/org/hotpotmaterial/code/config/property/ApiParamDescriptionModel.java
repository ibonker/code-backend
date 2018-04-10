/**
 * 
 */
package org.hotpotmaterial.code.config.property;

import lombok.Data;

/**
 * @author wenxing
 *
 */
@Data
public class ApiParamDescriptionModel {

  // 参数名称
  private String name;
  
  // 参数描述
  private String description;
  
  // 请求参数方式
  private String paramForm;
  
  // 请求参数类型
  private String paramType;
  
  // 请求参数格式
  private String paramFormat;
  
}
