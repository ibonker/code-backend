package org.hotpotmaterial.code.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author cyj
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "api_view_form_config")
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
public class ApiViewFormConfigPO extends BaseEntity {
  
  /**
   * 
   */
  private static final long serialVersionUID = 7986967006157841017L;

  @Column(name = "columnd_id")
  @JsonProperty("columndId")
  private String columndId; // 字段id

  @Column(name = "table_id")
  @JsonProperty("tableId")
  private String tableId; // 表Id

  @Column(name = "show_flag")
  @JsonProperty("showFlag")
  private String showFlag; // 是否显示
  
  @Column(name = "order")
  @JsonProperty("order")
  private Integer order; // 显示顺序
  
  @Column(name = "is_validate")
  @JsonProperty("isValidate")
  private String isValidate; // 是否需要验证
  
  @Column(name = "is_nullable")
  @JsonProperty("isNullable")
  private String isNullable; // 是否为空
  
  @Column(name = "read_only")
  @JsonProperty("readOnly")
  private String readOnly; // 是否只读
  
  @Column(name = "pattern")
  @JsonProperty("pattern")
  private String pattern; // 正则表达式
  
  @Column(name = "max")
  @JsonProperty("max")
  private Integer max; // 显示顺序
  
  @Column(name = "min")
  @JsonProperty("min")
  private Integer min; // 显示顺序
  
  @Transient
  private String javaType; // JAVA类型
  
  @Transient
  private String javaField; // JAVA字段名
  
  @Transient
  private String comments; // 描述

  /**
   * 可以更新的属性
   * 
   * @param apiViewFormConfigPO
   * @return
   */
  public ApiViewFormConfigPO updateAttrs(ApiViewFormConfigPO apiViewFormConfigPO) {
    this.showFlag = apiViewFormConfigPO.getShowFlag();
    this.order = apiViewFormConfigPO.getOrder();
    this.isValidate = apiViewFormConfigPO.getIsValidate();
    this.isNullable = apiViewFormConfigPO.getIsNullable();
    this.readOnly = apiViewFormConfigPO.getReadOnly();
    this.pattern = apiViewFormConfigPO.getPattern();
    this.max = apiViewFormConfigPO.getMax();
    this.min = apiViewFormConfigPO.getMin();
    return this;
  }

}
