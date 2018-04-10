package org.hotpotmaterial.code.entity;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import org.hotpotmaterial.code.common.BaseType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author xuyufeng
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transfer_obj_field")
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
public class TransferObjFieldPO extends BaseEntity {

  /**
   * 
   */
  private static final long serialVersionUID = -8102260394240350397L;

  @Column(name = "transfer_obj_id")
  @JsonProperty("transferObjId")
  private String transferObjId; // 实体id

  @Column(name = "name")
  @JsonProperty("name")
  private String name; // 属性名

  @Column(name = "type")
  @JsonProperty("type")
  private String type; // 属性类型

  @Column(name = "array_type")
  @JsonProperty("arrayType")
  private String arrayType; // 参数array类型

  @Column(name = "format")
  @JsonProperty("format")
  private String format; // 属性格式

  @Column(name = "description")
  @JsonProperty("description")
  private String description; // 属性描述

  @Column(name = "is_nullable")
  @JsonProperty(value = "isNullable")
  @JsonPropertyDescription("是否可为空（1：可为空；0：不能空）")
  private String isNullable; // 是否可为空（1：可为空；0：不能空）

  @Column(name = "read_only")
  @JsonProperty(value = "readOnly")
  @JsonPropertyDescription("是否只读（1：只读；0：可修改）")
  private String readOnly; // 是否只读（1：只读；0：可修改）

  @Column(name = "pattern")
  @JsonProperty(value = "pattern")
  @JsonPropertyDescription("正则表达式")
  private String pattern; // 正则表达式

  @Column(name = "min")
  @JsonProperty(value = "min")
  @JsonPropertyDescription("最小值")
  private Integer min; // 最小值、字符串最小长度

  @Column(name = "max")
  @JsonProperty(value = "max")
  @JsonPropertyDescription("最大值")
  private Integer max; // 最大值、 字符串最大长度
  
  @Column(name = "sort")
  @JsonProperty(value = "sort")
  @JsonPropertyDescription("排序")
  private Integer sort; // 排序

  public TransferObjFieldPO(String transferObjId, String name, String type, String format,
      String description) {
    this.transferObjId = transferObjId;
    this.name = name;
    this.type = type;
    this.format = format;
    this.description = description;
  }

  public TransferObjFieldPO(String name, String type, String format, String description) {
    this.name = name;
    this.type = type;
    this.format = format;
    this.description = description;
  }

  /**
   * 可以更新的属性
   * 
   * @param newTransferObjFieldPO
   * @return
   */
  public TransferObjFieldPO updateAttrs(TransferObjFieldPO newTransferObjFieldPO) {
    this.name = newTransferObjFieldPO.getName();
    this.type = newTransferObjFieldPO.getType();
    this.format = newTransferObjFieldPO.getFormat();
    this.description = newTransferObjFieldPO.getDescription();
    this.isNullable = newTransferObjFieldPO.getIsNullable();
    this.readOnly = newTransferObjFieldPO.getReadOnly();
    this.pattern = newTransferObjFieldPO.getPattern();
    this.min = newTransferObjFieldPO.getMin();
    this.max = newTransferObjFieldPO.getMax();
    return this;
  }

  /**
   * 设置array type
   * 
   * @return
   */
  public TransferObjFieldPO setArrayType() {
    if (StringUtils.isNotBlank(this.type)
        && BaseType.ARRAY.equals(BaseType.valueOf(this.type.toUpperCase()))) {
      String[] array = this.format.split("\\.");
      this.arrayType = array[0];
      List<String> formats = Lists.newArrayList(Arrays.asList(array));
      formats.remove(0);
      this.format = Joiner.on(".").join(formats);
    }

    return this;
  }
}
