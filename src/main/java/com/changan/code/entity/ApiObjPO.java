/**
 * 
 */
package com.changan.code.entity;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.bind.annotation.RequestMethod;

import com.changan.code.common.BaseDTO;
import com.changan.code.common.Constants;
import com.changan.code.common.BaseType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wenxing
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "api_obj")
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
public class ApiObjPO extends BaseEntity {
  /**
   * 
   */
  private static final long serialVersionUID = -8633054006197974379L;

  @Column(name = "api_base_id")
  @JsonProperty("apiBaseId")
  private String apiBaseId; // api基类id

  @Column(name = "uri")
  @JsonProperty("uri")
  private String uri; // uri

  @Column(name = "name")
  @JsonProperty("name")
  private String name; // 方法名

  @Column(name = "prefix_name")
  @JsonProperty(value = "prefixName", required = false)
  @JsonPropertyDescription("类名前缀")
  private String prefixName = Constants.API_DEFAULT_PREFIX; // 类名前缀

  @Column(name = "request_method")
  @JsonProperty("requestMethod")
  private String requestMethod; // 请求方法

  @Column(name = "response_obj_name")
  @JsonProperty("responseObjName")
  private String responseObjName; // 返回值类

  @Column(name = "response_obj_generic_type")
  @JsonProperty("responseObjGenericType")
  private String responseObjGenericType; // 泛型类型

  @Column(name = "response_obj_generic_array_type")
  @JsonProperty("responseObjGenericArrayType")
  private String responseObjGenericArrayType; // 泛型array的格式

  @Column(name = "response_obj_generic_format")
  @JsonProperty("responseObjGenericFormat")
  private String responseObjGenericFormat; // 泛型格式

  @Column(name = "consumes")
  @JsonProperty("consumes")
  private String consumes; // 消费类型

  @Column(name = "produces")
  @JsonProperty("produces")
  private String produces = Constants.API_PRODUCES; // 生成类型

  @Column(name = "summary")
  @JsonProperty("summary")
  private String summary; // 概述

  @Column(name = "description")
  @JsonProperty("description")
  private String description; // 描述

  @Column(name = "tag")
  @JsonProperty("tag")
  private String tag; // 标签

  @Column(name = "gen_based_table_id")
  @JsonProperty("genBasedTableId")
  private String genBasedTableId; // 自动生成表id

  @Column(name = "gen_related_table_id")
  @JsonProperty("genRelatedTableId")
  private String genRelatedTableId; // 自动生成关联表id

  @Transient
  private List<ApiParamPO> apiParams; // api方法参数

  @Transient
  private String serviceName; // 包含的service名称

  @Transient
  private String relateTableName; // 对应的表名

  @Transient
  private boolean isSlaveUri; // 从表uri

  @Transient
  private String firstPathVar; // uri中最后一个参数名（自动生成的api中为从表外键关联的主表字段）

  @Transient
  private String lastPathVar; // uri中最后一个参数名（自动生成的api中为从表主键）

  @Transient
  private String seniorRelationVar; // 高级查询时关联查询名称

  public boolean getIsSlaveUri() {
    return isSlaveUri;
  }

  /**
   * 更新属性
   * 
   * @param apiObj
   * @return
   */
  public ApiObjPO updateAttrs(ApiObjPO apiObj) {
    this.uri = apiObj.getUri();
    this.name = apiObj.getName();
    this.prefixName = apiObj.getPrefixName();
    this.requestMethod = apiObj.getRequestMethod();
    this.responseObjName = apiObj.getResponseObjName();
    this.responseObjGenericType = apiObj.getResponseObjGenericType();
    this.responseObjGenericFormat = apiObj.getResponseObjGenericFormat();
    this.consumes = apiObj.getConsumes();
    this.produces = apiObj.getProduces();
    this.summary = apiObj.getSummary();
    this.description = apiObj.getDescription();
    this.tag = apiObj.getTag();
    this.apiParams = apiObj.getApiParams();

    return this;
  }

  /**
   * 设置array type
   * 
   * @return
   */
  public ApiObjPO setArrayType() {
    if (StringUtils.isNotBlank(this.responseObjGenericType)
        && BaseType.ARRAY.equals(BaseType.valueOf(this.responseObjGenericType.toUpperCase()))) {
      String[] array = this.responseObjGenericFormat.split("\\.");
      this.responseObjGenericArrayType = array[0];
      List<String> formats = Lists.newArrayList(Arrays.asList(array));
      formats.remove(0);
      this.responseObjGenericFormat = Joiner.on(".").join(formats);
    }

    return this;
  }

  /**
   * 获取response实体名
   * 
   * @return
   */
  @JsonIgnore
  public String getResponseObjNameNoPack() {
    String[] responseObjNames = this.responseObjName.split("\\.");
    return responseObjNames[responseObjNames.length - 1];
  }

  /**
   * 获取generic实体名
   * 
   * @return
   */
  @JsonIgnore
  public String getResponseObjGenericNoPack() {
    if (StringUtils.isNotBlank(this.responseObjGenericFormat)) {
      String[] names = this.responseObjGenericFormat.split("\\.");
      return "<" + names[names.length - 1] + ">";
    }
    return "";
  }

  /**
   * 获取service的名称
   * 
   * @return
   */
  @JsonIgnore
  public String getServiceNameNoPack() {
    if (StringUtils.isNotBlank(this.serviceName)) {
      String[] names = this.serviceName.split("\\.");
      return names[names.length - 1];
    }
    return "";
  }

  /**
   * 获取service的属性名称
   * 
   * @return
   */
  @JsonIgnore
  public String getAttrServiceNameNoPack() {
    if (StringUtils.isNotBlank(this.serviceName)) {
      String[] names = this.serviceName.split("\\.");
      String name = names[names.length - 1];
      return StringUtils.uncapitalize(name.substring(1, name.length()));
    }
    return "";
  }

  /**
   * 是否自动生成
   * 
   * @return
   */
  @JsonProperty("isAutoGen")
  public String getIsAutoGen() {
    if (StringUtils.isBlank(this.genBasedTableId)) {
      return Constants.IS_INACTIVE;
    }
    return Constants.IS_ACTIVE;
  }

  /**
   * api的类型
   * 
   * @return
   */
  @JsonIgnore
  public String getApiType() {
    if (Constants.IS_ACTIVE.equals(this.getIsAutoGen())) {
      if (BaseDTO.ResultPageDTO.name().equals(this.responseObjName)) {
        return "page";
      } else if (!BaseDTO.ResultPageDTO.name().equals(this.responseObjName)
          && RequestMethod.POST.name().equals(this.requestMethod)) {
        return "insert";
      } else if (RequestMethod.PUT.name().equals(this.requestMethod)) {
        return "update";
      } else if (RequestMethod.DELETE.name().equals(this.requestMethod)) {
        return "delete";
      } else if (BaseDTO.ResultJsonSchemaDTO.name().equals(this.responseObjName)) {
        return "jsonschema";
      } else {
        return "show";
      }
    }
    return "general";
  }

  /**
   * table对应的单体属性名
   * 
   * @return
   */
  @JsonIgnore
  public String getTableAttrName() {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,
        this.relateTableName.toLowerCase());
  }

  /**
   * table对应的单体属性名(首字母大写)
   * 
   * @return
   */
  @JsonIgnore
  public String getTableAttrNameCap() {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,
        this.relateTableName.toLowerCase());
  }

  /**
   * 通过uri的第一级获取controller名称
   * 
   * @return
   */
  @JsonIgnore
  public String getControllerName() {
    return this.prefixName.concat(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,
        this.uri.split("/")[1].toLowerCase()));
  }

  /**
   * 首字母大写
   * 
   * @return
   */
  @JsonIgnore
  public String getFirstPathVarCap() {
    return StringUtils.capitalize(this.firstPathVar);
  }

  /**
   * 是否高级查询
   * 
   * @return
   */
  @JsonIgnore
  public boolean getIsSenior() {
    return Constants.API_SENIOR_TAG.equals(this.genRelatedTableId);
  }

  /**
   * 大写驼峰
   * 
   * @return
   */
  public String getSeniorRelationVarCap() {
    if (StringUtils.isNotBlank(this.seniorRelationVar)) {
      return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.seniorRelationVar);
    }
    return null;
  }

}
