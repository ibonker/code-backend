/**
 * 
 */
package com.changan.code.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.changan.code.common.Constants;
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
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
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
  
  @Column(name = "name")
  @JsonProperty("name")
  private String name; // 方法名
  
  @Column(name = "request_method")
  @JsonProperty("requestMethod")
  private String requestMethod; // 请求方法
  
  @Column(name = "response_obj_name")
  @JsonProperty("responseObjName")
  private String responseObjName; // 返回值类
  
  @Column(name = "response_obj_generic")
  @JsonProperty("responseObjGeneric")
  private String responseObjGeneric; // 生成类型
  
  @Column(name = "consumes")
  @JsonProperty("consumes")
  private String consumes; // 消费类型
  
  @Column(name = "produces")
  @JsonProperty("produces")
  private String produces = Constants.API_PRODUCES; // 生成类型
  
  @Column(name = "summary")
  @JsonProperty("summary")
  private String summary; // 概述
  
  @Column(name = "description")
  @JsonProperty("description")
  private String description; // 描述
  
  @Column(name = "is_auto_gen")
  @JsonProperty("isAutoGen")
  private String isAutoGen = Constants.IS_INACTIVE; // 是否自动生成
  
  @Column(name = "tag")
  @JsonProperty("tag")
  private String tag; // 标签

  @Transient
  private List<ApiParamPO> apiParam; //api方法参数
  
  /**
   * 更新属性
   * @param apiObj
   * @return
   */
  public ApiObjPO updateAttrs(ApiObjPO apiObj){
	  this.apiBaseId = apiObj.getApiBaseId();
	  this.uri = apiObj.getUri();
	  this.requestMethod = apiObj.getRequestMethod();
	  this.responseObjName = apiObj.getResponseObjName();
	  this.responseObjGeneric = apiObj.getResponseObjGeneric();
	  this.summary = apiObj.getSummary();
	  this.description = apiObj.getDescription();
	  this.tag = apiObj.getTag();
	  this.apiParam = apiObj.getApiParam();
	  
	  return this;
  }
  
  public String getResponseObjNameNoPack() {
    String[] responseObjNames = this.responseObjName.split(".");
    return responseObjNames[responseObjNames.length - 1];
  }
  
}
