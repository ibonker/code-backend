/**
 * 
 */
package com.changan.code.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.changan.anywhere.common.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.collect.Lists;

import lombok.Data;

/**
 * @author wenxing
 *
 */
@Data
@XmlRootElement(name = "template")
@JsonInclude(value = Include.NON_NULL)
public class Template implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 名称
   */
  private String name;

  /**
   * 分类
   */
  private String category;

  /**
   * 生成文件路径
   */
  private String filePath;

  /**
   * 文件名
   */
  private String fileName;

  /**
   * 内容
   */
  private String content;
  
  /**
   * 获取分类列表
   * 
   * @return
   */
  @XmlTransient
  public List<String> getCategoryList() {
    if (category == null) {
      return Lists.newArrayList();
    } else {
      return Lists.newArrayList(StringUtils.split(category, ","));
    }
  }

  /**
   * 设置分类列表
   * 
   * @param categoryList
   */
  public void setCategoryList(List<String> categoryList) {
    if (categoryList == null) {
      this.category = "";
    } else {
      this.category = "," + StringUtils.join(categoryList, ",") + ",";
    }
  }

}
