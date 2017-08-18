/**
 * 
 */
package com.changan.code.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import com.changan.code.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wenxing
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "api_obj")
@MappedSuperclass
public class ApiObjPO extends BaseEntity {
  /**
   * 
   */
  private static final long serialVersionUID = -8633054006197974379L;
  
  @Column(name = "api_base_id")
  @JsonProperty("apiBaseId")
  private String apiBaseId; // api基类id
  
  @Column(name = "uri")
  @JsonProperty("uri")
  private String uri; // uri
  
  @Column(name = "request_method")
  @JsonProperty("requestMethod")
  private String requestMethod; // 请求方法
  
  @Column(name = "response_obj_name")
  @JsonProperty("response_obj_name")
  private String response_obj_name; // 返回值类
  
  @Column(name = "response_obj_generic")
  @JsonProperty("responseObjGeneric")
  private String responseObjGeneric; // 返回值类泛型（如果存在）
  
  @Column(name = "summary")
  @JsonProperty("summary")
  private String summary; // 概述
  
  @Column(name = "description")
  @JsonProperty("description")
  private String description; // 描述
  
  @Column(name = "tag")
  @JsonProperty("tag")
  private String tag; // 标签

}
