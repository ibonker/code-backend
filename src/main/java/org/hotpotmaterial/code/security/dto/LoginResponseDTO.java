/** 
 * Project Name:api-gateway-server 
 * File Name:LoginResponseDTO.java 
 * Package Name:com.changan.api.dto.res 
 * Date:2017年8月31日下午2:20:34 
 * Copyright (c) 2017, zengjing All Rights Reserved. 
 * 
 */  
package org.hotpotmaterial.code.security.dto;

import java.io.Serializable;

import lombok.Data;

/** 
 * ClassName: LoginResponseDTO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2017年8月31日 下午2:20:34 <br/> 
 * 
 * @author zengjing
 * @version  
 * @since JDK 1.8
 */
@Data
public class LoginResponseDTO implements Serializable {
  /**
   * 序列号
   */
  private static final long serialVersionUID = 3705557259484325611L;
  /**
   * 是否成功
   */
  private boolean isSuccess;
  /**
   * HTTP STATUS CODE
   */
  private int status;
  /**
   * 错误消息
   */
  private String errorMessage;
  /**
   * 结果数据
   */
  private transient Object data;
}