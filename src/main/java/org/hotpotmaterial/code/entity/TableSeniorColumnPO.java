/**
 * 
 */
package org.hotpotmaterial.code.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wenxing
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "table_senior_column")
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
public class TableSeniorColumnPO extends BaseEntity {
  
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -843290842832408915L;

  @Column(name = "senior_slave_id")
  @JsonProperty("seniorSlaveId")
  @JsonPropertyDescription("关联从表关系Id")
  private String seniorSlaveId; // 关联从表关系Id
  
  @Column(name = "master_column_name")
  @JsonProperty("masterColumnName")
  @JsonPropertyDescription("主表关联字段")
  private String masterColumnName; // 主表关联字段
  
  @Column(name = "slave_column_name")
  @JsonProperty("slaveColumnName")
  @JsonPropertyDescription("从表关联字段")
  private String slaveColumnName; // 从表关联字段
  
  @Column(name = "master_column_type")
  @JsonProperty("masterColumnType")
  @JsonPropertyDescription("主表字段类型")
  private String masterColumnType; // 主表字段类型
  
  @Column(name = "slave_column_type")
  @JsonProperty("slaveColumnType")
  @JsonPropertyDescription("从表字段类型")
  private String slaveColumnType; // 从表字段类型
  
  @Column(name = "operate")
  @JsonProperty("operate")
  @JsonPropertyDescription("运算符")
  private String operate; // 运算符
  
}
