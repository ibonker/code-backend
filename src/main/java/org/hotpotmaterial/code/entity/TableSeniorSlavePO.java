/**
 * 
 */
package org.hotpotmaterial.code.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.google.common.base.CaseFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wenxing
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "table_senior_slave")
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
public class TableSeniorSlavePO extends BaseEntity {
  
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -843290842832408915L;

  @Column(name = "senior_id")
  @JsonProperty("seniorId")
  @JsonPropertyDescription("关联关系Id")
  private String seniorId; // 关联关系Id
  
  @Column(name = "slave_table_id")
  @JsonProperty("slaveTableId")
  @JsonPropertyDescription("从表Id")
  private String slaveTableId; // 从表Id
  
  @Column(name = "slave_table_name")
  @JsonProperty("slaveTableName")
  @JsonPropertyDescription("从表名")
  private String slaveTableName; // 从表名
  
  @Column(name = "relation")
  @JsonProperty("relation")
  @JsonPropertyDescription("关系")
  private String relation; // 关系
  
  @Transient
  private List<TableSeniorColumnPO> relationColumns;
  
  @JsonIgnore
  public String getSlaveTableAttrName() {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, slaveTableName.toLowerCase());
  }
  
}
