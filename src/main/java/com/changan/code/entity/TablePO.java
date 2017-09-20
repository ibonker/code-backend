/**
 * 
 */
package com.changan.code.entity;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.changan.anywhere.common.utils.StringUtils;
import com.changan.code.common.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wenxing
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "tabled")
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
public class TablePO extends BaseEntity {
  /**
   * 
   */
  private static final long serialVersionUID = 761964282772844740L;
  
  @NotNull
  @Column(name = "name")
  @JsonPropertyDescription("名称")
  @JsonProperty(value = "name", access = Access.READ_ONLY)
  private String name; // 名称
  
  @Column(name = "comments")
  @JsonProperty("comments")
  @JsonPropertyDescription("描述")
  private String comments; // 描述
  
  @Column(name = "class_name")
  @JsonProperty(value = "className", access = Access.READ_ONLY)
  @JsonPropertyDescription("实体类名称")
  private String className; // 实体类名称
  
  @Column(name = "datasource_id")
  @JsonProperty(value = "datasourceId", access = Access.READ_ONLY)
  @JsonPropertyDescription("数据源")
  private String datasourceId; // 数据源
  
  @Column(name = "is_auto_crud")
  @JsonProperty("isAutoCrud")
  @JsonPropertyDescription("是否自动生成CRUD的SQL、接口和API（1：是；0：否）")
  private String isAutoCrud; // 是否自动生成CRUD的SQL、接口和API（1：是；0：否）

  @Transient
  @JsonPropertyDescription("表字段列表")
  private List<ColumnPO> columnList = Lists.newArrayList(); // 表列
  
  @Transient
  @JsonPropertyDescription("api列表")
  private List<ApiObjPO> pathList = Lists.newArrayList(); // api列表
  
  @Transient
  @JsonIgnore
  private String packageName; // 包名
  
  /**
   * column map
   */
  @Transient
  @JsonIgnore
  private Map<String, ColumnPO> columnMaps;
  
  /**
   * 与主表的table relation list
   */
  @Transient
  @JsonIgnore
  private List<TableRelationPO> masterTableRelations;
  
  /**
   * 初始化值
   * @param datasourceId
   * @return
   */
  public TablePO setDefaultValue(String datasourceId) {
    // 类名
    this.setClassName(
        CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.getName()).concat("PO"));
    // 数据源名称
    this.setDatasourceId(datasourceId);
    // 是否自动生成crud，默认否
    this.setIsAutoCrud(Constants.IS_INACTIVE);
    
    return this;
  }
  
  /**
   * 更新表
   * @param newTable
   * @return
   */
  public TablePO updateAttrs(TablePO newTable) {
    if (StringUtils.isNotBlank(newTable.getComments())) {
      this.comments = newTable.getComments();
    }
    return this;
  }

}
