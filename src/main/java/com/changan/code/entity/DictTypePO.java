package com.changan.code.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.changan.anywhere.common.utils.IdGen;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典表类型
 * 
 * @author xuyufeng
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "dict_type")
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
public class DictTypePO extends BaseEntity {

  private static final long serialVersionUID = -8625001044493801034L;

  @Size(max = 64)
  @Column(name = "code")
  @JsonProperty(value = "code")
  @JsonPropertyDescription("")
  private String code;

  @Size(max = 255)
  @Column(name = "name")
  @JsonProperty(value = "name")
  @JsonPropertyDescription("")
  private String name;

  @Size(max = 64)
  @Column(name = "created_by")
  @JsonProperty(value = "createdBy")
  @JsonPropertyDescription("")
  private String createdBy;

  @Size(max = 64)
  @Column(name = "updated_by")
  @JsonProperty(value = "updatedBy")
  @JsonPropertyDescription("")
  private String updatedBy;

  /**
   * 更新属性
   * @param dictType
   * @return
   */
  public DictTypePO updateAttrs(DictTypePO dictType) {
    this.code = dictType.getCode();
    this.name = dictType.getName();
    this.createdBy = dictType.getCreatedBy();
    this.updatedBy = dictType.getUpdatedBy();
    return this;
  }
  
  /**
   * 插入之前执行方法，需要手动调用
   */
  public void preInsert() {
    // 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
    if (this.isNew()) {
      setId(IdGen.uuid());
    }
    this.updatedAt = LocalDateTime.now();
    this.createdAt = this.updatedAt;
  }

  /**
   * 更新之前执行方法，需要手动调用
   */
  public void preUpdate() {
    this.updatedAt = LocalDateTime.now();
  }
}
