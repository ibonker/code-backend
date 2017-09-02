/**
 * 
 */
package com.changan.code.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.changan.code.common.Constants;
import com.changan.code.common.DtoType;
import com.changan.code.common.ParamIn;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wenxing
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "api_param")
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
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
  private String isRequired = Constants.IS_ACTIVE; // 是否必需

  @Column(name = "type")
  @JsonProperty("type")
  private String type; // 参数类型

  @Column(name = "format")
  @JsonProperty("format")
  private String format; // 参数格式

  /**
   * 可以更新的属性
   * 
   * @param newTransferObjFieldPO
   * @return
   */
  public ApiParamPO updateAttrs(ApiParamPO newApiParamPO) {
    this.name = newApiParamPO.getName();
    this.description = newApiParamPO.getDescription();
    this.form = newApiParamPO.getForm();
    this.isRequired = newApiParamPO.getIsRequired();
    this.type = newApiParamPO.getType();
    this.format = newApiParamPO.getFormat();
    return this;
  }
  
  /**
   * 获取实体名
   * 
   * @return
   */
  @JsonIgnore
  public String getParamObj() {
    String[] responseObjNames = this.format.split("\\.");
    String prefix = "", postfix = "";
    if (DtoType.ARRAY.toString().equals(this.type.toUpperCase())) {
      prefix = "List<";
      postfix = ">";
    }
    return prefix.concat(responseObjNames[responseObjNames.length - 1]).concat(postfix);
  }
  
  /**
   * 根据参数位置类型获取相应注解
   * @return
   */
  @JsonIgnore
  public String getFormAnnotation() {
    return ParamIn.valueOf(this.form.toUpperCase()).getAnnotation();
  }
}
