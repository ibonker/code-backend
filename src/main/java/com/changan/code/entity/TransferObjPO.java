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
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author xuyufeng
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "transfer_obj")
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
public class TransferObjPO extends BaseEntity {

  /**
   * 
   */
  private static final long serialVersionUID = 4806150382416503270L;

  @Column(name = "project_id")
  @JsonProperty("projectId")
  private String projectId; // 项目id

  @Column(name = "name")
  @JsonProperty("name")
  private String name; // 实体名称

  @Column(name = "comments")
  @JsonProperty("comments")
  private String comments; // 注释

  @Column(name = "is_generic")
  @JsonProperty("isGeneric")
  private String isGeneric; // 是否泛型

  @Column(name = "inherit_obj_name")
  @JsonProperty("inheritObjName")
  private String inheritObjName; // 父类实体名

  /**
   * 数据库名称对应包名
   */
  @Column(name = "package_name")
  @JsonProperty(value = "packageName", required = false)
  @JsonPropertyDescription("包名")
  private String packageName = Constants.TRANS_OBJ_DEFAULT_PACKAGE;

  @Transient
  private List<TransferObjFieldPO> transferObjField; // 数据源

  /**
   * 可以更新的属性
   * 
   * @param newTransferObjPO
   * @return
   */
  public TransferObjPO updateAttrs(TransferObjPO newTransferObjPO) {
    this.projectId = newTransferObjPO.getProjectId();
    this.name = newTransferObjPO.getName();
    this.comments = newTransferObjPO.getComments();
    this.isGeneric = newTransferObjPO.getIsGeneric();
    this.inheritObjName = newTransferObjPO.getInheritObjName();
    this.transferObjField = newTransferObjPO.getTransferObjField();
    return this;
  }
}
