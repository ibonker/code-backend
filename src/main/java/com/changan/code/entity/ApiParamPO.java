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
@Table(name = "api_param")
@MappedSuperclass
public class ApiParamPO extends BaseEntity {
  /**
   * 
   */
  private static final long serialVersionUID = 3696572365031036657L;
  
  @Column(name = "api_obj_id")
  @JsonProperty("apiObjId")
  private String apiObjId; // api对象id
  
  @Column(name = "name")
  @JsonProperty("name")
  private String name; // 参数名
  
  @Column(name = "description")
  @JsonProperty("description")
  private String description; // 参数描述
  
  @Column(name = "form")
  @JsonProperty("form")
  private String form; // 归属表
  
  @Column(name = "is_required")
  @JsonProperty("isRequired")
  private String isRequired; // 归属表
  
  @Column(name = "type")
  @JsonProperty("type")
  private String type; // 参数类型
  
  @Column(name = "format")
  @JsonProperty("format")
  private String format; // 参数格式

}
