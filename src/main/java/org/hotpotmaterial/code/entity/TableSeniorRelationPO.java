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
@Table(name = "table_senior_relation")
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
public class TableSeniorRelationPO extends BaseEntity {
  
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -6674717285087148697L;

  @Column(name = "master_table_id")
  @JsonProperty("masterTableId")
  @JsonPropertyDescription("主表Id")
  private String masterTableId; // 主表Id
  
  @Column(name = "master_table_name")
  @JsonProperty("masterTableName")
  @JsonPropertyDescription("主表名")
  private String masterTableName; // 主表名
  
  @Transient
  private List<TableSeniorSlavePO> relationTables; // 关联字表
  
  @Transient
  private String sql; //高级关联sql
  
}
