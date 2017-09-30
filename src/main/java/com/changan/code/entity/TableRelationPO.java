/**
 * 
 */
package com.changan.code.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.CaseFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wenxing
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "table_relation")
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
public class TableRelationPO extends BaseEntity {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 1164655325493943659L;
  
  @Column(name="master_table_id")
  @JsonProperty("masterTableId")
  private String masterTableId; //主表Id
  
  @Column(name="slave_table_id")
  @JsonProperty("slaveTableId")
  private String slaveTableId; //从表Id
  
  @Column(name="relation")
  @JsonProperty("relation")
  private String relation; //关系
  
  @Column(name="slave_column_name")
  @JsonProperty("slaveColumnName")
  private String slaveColumnName; //主表字段
  
  @Column(name="master_column_name")
  @JsonProperty("masterColumnName")
  private String masterColumnName; //从表字段
  
  @Column(name="master_column_type")
  @JsonProperty("masterColumnType")
  private String masterColumnType; //主表字段类型
  
  @Column(name="slave_column_type")
  @JsonProperty("slaveColumnType")
  private String slaveColumnType; //从表字段类型
  
  @Transient
  private String slaveTableName; // 从表名
  
  @Transient
  private String masterTableName; // 主表名
  
  @Transient
  private String slaveClassName; // 从表实体名
  
  @Transient
  private String masterClassName; // 主表实体名
  
  
  
  /**
   * 主表名小写驼峰
   * @return
   */
  @JsonIgnore
  public String getMasterTableNameLower() {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,
        this.masterTableName.toLowerCase());
  }
  
  /**
   * 主表名大写驼峰
   * @return
   */
  @JsonIgnore
  public String getMasterTableNameCap() {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,
        this.masterTableName.toLowerCase());
  }
  
  /**
   * 主表字段大写驼峰
   * @return
   */
  @JsonIgnore
  public String getMasterColumnNameCap() {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,
        this.masterColumnName.toLowerCase());
  }
  
  /**
   * 从表名大写驼峰
   * @return
   */
  @JsonIgnore
  public String getSlaveTableNameCap() {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,
        this.slaveTableName.toLowerCase());
  }
  
  /**
   * 从表名大写驼峰
   * @return
   */
  @JsonIgnore
  public String getSlaveTableNameLower() {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,
        this.slaveTableName.toLowerCase());
  }
  
  /**
   * 从表表字段大写驼峰
   * @return
   */
  @JsonIgnore
  public String getSlaveColumnNameCap() {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,
        this.slaveColumnName.toLowerCase());
  }
  
  /**
   * 从表表字段小写驼峰
   * @return
   */
  @JsonIgnore
  public String getSlaveColumnNameLower() {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,
        this.slaveColumnName.toLowerCase());
  }
}
