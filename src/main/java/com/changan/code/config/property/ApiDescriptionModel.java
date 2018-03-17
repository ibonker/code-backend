/**
 * 
 */
package com.changan.code.config.property;

import java.util.List;

import lombok.Data;

/**
 * @author wenxing
 *
 */
@Data
public class ApiDescriptionModel {
  
  // 模型名称
  private String apiModelName;
  
  // swagger标签
  private String tag;
  
  // 接口路径
  private String uri;
  
  // 接口方法名
  private String methodName;
  
  // 接口响应dto
  private String responseDTO;
  
  // 接口描述
  private String description;
  
  // 响应泛型类型
  private String responseGenericType;
  
  // 响应泛型名称
  private String responseGenericName;
  
  // http produce
  private String produces;
  
  // http consume
  private String consumes;
  
  // 请求方式
  private String requestMethod;
  
  // api接口参数
  private List<ApiParamDescriptionModel> apiParams;
  
}
