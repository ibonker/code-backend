/**
 * 
 */
package org.hotpotmaterial.code.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import org.hotpotmaterial.anywhere.common.utils.StringUtils;
import org.hotpotmaterial.code.common.Constants;
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
@EqualsAndHashCode(callSuper = true)
@Table(name = "columnd")
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
public class ColumnPO extends BaseEntity {
  /**
   * 
   */
  private static final long serialVersionUID = 3980144310483390653L;

  @Column(name = "table_id")
  @JsonProperty(value = "tableId")
  @JsonPropertyDescription("归属表")
  private String tableId; // 归属表

  @Column(name = "name")
  @JsonProperty(value = "name")
  @JsonPropertyDescription("列名")
  private String name; // 列名

  @Column(name = "comments")
  @JsonProperty(value = "comments")
  @JsonPropertyDescription("描述")
  private String comments; // 描述

  @Column(name = "jdbc_type")
  @JsonProperty(value = "jdbcType")
  @JsonPropertyDescription("JDBC类型")
  private String jdbcType; // JDBC类型

  @Column(name = "java_type")
  @JsonProperty(value = "javaType")
  @JsonPropertyDescription("JAVA类型")
  private String javaType; // JAVA类型

  @Column(name = "java_field")
  @JsonProperty(value = "javaField")
  @JsonPropertyDescription("JAVA字段名")
  private String javaField; // JAVA字段名

  @Column(name = "is_pk")
  @JsonProperty(value = "isPk")
  @JsonPropertyDescription("是否主键（1：主键；0：一般字段）")
  private String isPk = Constants.IS_INACTIVE; // 是否主键（1：主键）

  @Column(name = "is_nullable")
  @JsonProperty(value = "isNullable")
  @JsonPropertyDescription("是否可为空（1：可为空；0：不能空）")
  private String isNullable = Constants.IS_ACTIVE; // 是否可为空（1：可为空；0：不能空）

  @Column(name = "read_only")
  @JsonProperty(value = "readOnly")
  @JsonPropertyDescription("是否只读（1：只读；0：可修改）")
  private String readOnly = Constants.IS_INACTIVE; // 是否只读（1：只读；0：可修改）

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
  
  @Column(name = "dict_type_code")
  @JsonProperty(value = "dictTypeCode")
  @JsonPropertyDescription("字典表字段")
  private String dictTypeCode; //字典表字段
  
  @Transient
  private int sortWeight = 0; // 排序权重

  /**
   * 设置java类型
   * 
   */
  public ColumnPO setColumnJavaType() {
    // 设置java类型
    if (StringUtils.startsWithIgnoreCase(this.getJdbcType(), "CHAR") // 当数据库中该字段类型为CHAR型时
        || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "VARCHAR") // 当数据库中该字段类型为VARCHAR型时
        || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "TINYBLOB") // 当数据库中该字段类型为TINYBLOB型时
        || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "TINYTEXT") // 当数据库中该字段类型为TINYTEXT型时
        || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "BLOB") // 当数据库中该字段类型为BLOB型时
        || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "CLOB") // 当数据库中该字段类型为CLOB型时
        || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "TEXT") // 当数据库中该字段类型为TEXT型时
        || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "MEDIUMBLOB") // 当数据库中该字段类型为MEDIUMBLOB型时
        || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "MEDIUMTEXT") // 当数据库中该字段类型为MEDIUMTEXT型时
        || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "LONGBLOB") // 当数据库中该字段类型为LONGBLOB型时
        || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "LONGTEXT") // 当数据库中该字段类型为LONGTEXT型时
        || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "NVARCHAR")) { // 当数据库中该字段类型为LONGTEXT型时
      // 设置该字段的Java类型为String型
      this.javaType = "String";
    } else if (StringUtils.startsWithIgnoreCase(this.getJdbcType(), "DATE") // 当数据库中该字段类型为DATE型时
        || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "TIME") // 当数据库中该字段类型为TIME型时
        || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "YEAR") // 当数据库中该字段类型为YEAR型时
        || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "DATETIME")) { // 当数据库中该字段类型为DATETIME型时
      this.javaType = "Date";
    } else if (StringUtils.startsWithIgnoreCase(this.getJdbcType(), "TIMESTAMP")) { // 当数据库中该字段类型为TIMESTAMP型时
      this.javaType = "Timestamp";
    } else if (StringUtils.startsWithIgnoreCase(this.getJdbcType(), "DECIMAL")) { // 当数据库中该字段类型为DECIMAL型时
      // 设置该字段的java类型为BigDecimal类型
      this.javaType = "BigDecimal";
    } else if (StringUtils.startsWithIgnoreCase(this.getJdbcType(), "TINYINT") // 当数据库中该字段类型为TINYINT型时
        || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "SMALLINT") // 当数据库中该字段类型为SMALLINT型时
        || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "MEDIUMINT") // 当数据库中该字段类型为MEDIUMINT型时
        || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "INT") // 当数据库中该字段类型为INT型时
        || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "INTEGER") // 当数据库中该字段类型为INTEGER型时
        || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "BIGINT") // 当数据库中该字段类型为BIGINT型时
        || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "FLOAT") // 当数据库中该字段类型为FLOAT型时
        || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "DOUBLE")
        || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "NUMBER")
        || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "BINARY_FLOAT")
        || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "BINARY_DOUBLE")) { // 当数据库中该字段类型为DOUBLE型时
      // 如果是浮点型
      String[] ss =
          StringUtils.split(StringUtils.substringBetween(this.getJdbcType(), "(", ")"), ",");
      // 当该数据库字段为浮点型数据时
      if (ss != null && ss.length == 2 && Integer.parseInt(ss[1]) > 0
          || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "BINARY_FLOAT")
          || StringUtils.startsWithIgnoreCase(this.getJdbcType(), "BINARY_DOUBLE")) {
        // 设置该字段的java类型为Double数据类型
        this.javaType = "Double";
      }
      // 如果是整形
      else if (ss != null && ss.length == 1 && Integer.parseInt(ss[0]) <= 11) {
        // 设置该字段的java类型为Integer数据类型
        this.javaType = "Integer";
      }
      // 长整形
      else {
        // 设置该字段的java类型为Long数据类型
        this.javaType = "Long";
      }
    } else if (StringUtils.startsWithIgnoreCase(this.getJdbcType(), "RAW")) {
      this.javaType = "byte[]";
    } else {
      this.javaType = "String";
    }

    return this;
  }

  /**
   * 设置配置后的属性
   * 
   * @param column
   * @return
   */
  public ColumnPO setConfigedProperties(ColumnPO column) {
    if (null != column) {
      this.id = column.getId();
      // 如果数据库可以为空则由用户自定义
      if (Constants.IS_ACTIVE.equals(this.isNullable)) {
        this.isNullable = column.getIsNullable();
      }
      this.readOnly = column.getReadOnly();
      this.pattern = column.getPattern();
      this.min = column.getMin();
      this.max = column.getMax();
      this.dictTypeCode = column.getDictTypeCode();
      if (StringUtils.isNotBlank(column.getComments())) {
        this.comments = column.getComments();
      }
    }

    return this;
  }

  /**
   * 字段名转为java属性名
   * 
   * @return
   */
  public ColumnPO javaFieldName() {
    this.setJavaField(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.getName()));

    return this;
  }

}
