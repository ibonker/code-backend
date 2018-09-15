/** 
 * Project Name:api-gateway-server 
 * File Name:CurrentUserDTO.java 
 * Package Name:com.changan.api.dto.res 
 * Date:2017年8月31日下午1:56:11 
 * Copyright (c) 2017, zengjing All Rights Reserved. 
 * 
 */  
package org.hotpotmaterial.code.security.dto;

import java.io.Serializable;

import lombok.Data;

/** 
 * ClassName: CurrentUserDTO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2017年8月31日 下午1:56:11 <br/> 
 * 
 * @author zengjing
 * @version  
 * @since JDK 1.8
 */
@Data
public class CurrentUserDTO implements Serializable {
  /**
   * 序列号
   */
  private static final long serialVersionUID = 2300732633577403803L;

  /**
   * 主键
   */
  private String id;
  /**
   * 登陆账号
   */
  private String username;
  /**
   * 登陆人姓名
   */
  private String name;
  /**
   * 所属组织机构ID
   */
  private String orgId;
  
  /**
   * 所属组织机构
   */
  private String orgName;
  
  private String orgFullId;
  
  private String orgFullName;
  
  /**
   * 是否第一次登陆  0:登陆过 1:第一次登陆
   */
  private String flag;
}