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
@Table(name = "api_view_table_config")
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
public class ApiViewTableConfigPO extends BaseEntity {
  
  /**
   * 
   */
  private static final long serialVersionUID = -5874734393149974918L;

  @Column(name = "columnd_id")
  @JsonProperty("columndId")
  private String columndId; // 字段id

  @Column(name = "table_id")
  @JsonProperty("tableId")
  private String tableId; // 表Id

  @Column(name = "show_flag")
  @JsonProperty("showFlag")
  private String showFlag; // 是否显示
  
  @Column(name = "search_flag")
  @JsonProperty("searchFlag")
  private String searchFlag; // 是否查询

  @Column(name = "order")
  @JsonProperty("order")
  private Integer order; // 显示顺序
  
  @Transient
  private String javaType; // JAVA类型
  
  @Transient
  private String javaField; // JAVA字段名
  
  @Transient
  private String comments; // 描述

  /**
   * 可以更新的属性
   * 
   * @param newTransferObjPO
   * @return
   */
  public ApiViewTableConfigPO updateAttrs(ApiViewTableConfigPO apiViewTableConfigPO) {
    this.showFlag = apiViewTableConfigPO.getShowFlag();
    this.searchFlag = apiViewTableConfigPO.getSearchFlag();
    this.order = apiViewTableConfigPO.getOrder();
    return this;
  }

}
