package com.changan.code.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author cyj
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "api_view")
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
public class ApiViewPO extends BaseEntity {
  
  /**
   * 
   */
  private static final long serialVersionUID = 7121043973988156836L;
  
  @Column(name = "project_id")
  @JsonProperty("projectId")
  private String projectId; // 项目id

  @Column(name = "table_config")
  @JsonProperty("tableConfig")
  private String tableConfig; // 表格配置

  @Column(name = "form_config")
  @JsonProperty("formConfig")
  private String formConfig; // 表单配置

  @Column(name = "save_path")
  @JsonProperty("savePath")
  private String savePath; // 新增请求地址

  @Column(name = "search_path")
  @JsonProperty("searchPath")
  private String searchPath; // 查询请求地址

  @Column(name = "delete_path")
  @JsonProperty("deletePath")
  private String deletePath; // 删除请求地址
  
  @Column(name = "update_path")
  @JsonProperty("updatePath")
  private String updatePath; // 修改请求地址
  
  @Column(name = "is_add")
  @JsonProperty("isAdd")
  private String isAdd; // 是否可新增
  
  @Column(name = "is_modify")
  @JsonProperty("isModify")
  private String isModify; // 是否可修改
  
  @Column(name = "is_delete")
  @JsonProperty("isDelete")
  private String isDelete; // 是否可删除
  
  @Column(name = "is_page")
  @JsonProperty("isPage")
  private String isPage; // 是否分页
  
  @Column(name = "table_id")
  @JsonProperty("tableId")
  private String tableId; // 表Id
  
  @Column(name = "table_name")
  @JsonProperty("tableName")
  private String tableName; // 表名称
  
  @Column(name = "config_name")
  @JsonProperty("configName")
  private String configName; // 配置名称


  /**
   * 可以更新的属性
   * 
   * @param newTransferObjPO
   * @return
   */
  public ApiViewPO updateAttrs(ApiViewPO apiViewPO) {
    this.tableConfig = apiViewPO.getTableConfig();
    this.formConfig = apiViewPO.getFormConfig();
    this.savePath = apiViewPO.getSavePath();
    this.searchPath = apiViewPO.getSearchPath();
    this.deletePath = apiViewPO.getDeletePath();
    this.updatePath = apiViewPO.getUpdatePath();
    this.isAdd = apiViewPO.getIsAdd();
    this.isModify = apiViewPO.getIsModify();
    this.isDelete = apiViewPO.getIsDelete();
    this.isPage = apiViewPO.getIsPage();
    this.configName = apiViewPO.getConfigName();
    return this;
  }

}
