/**
 * 
 */
package org.hotpotmaterial.code.common;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author wenxing
 *
 */
public enum BaseDTO {

  ResultDTO("org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO", "返回值基类"), ResultPageDTO(
      "org.hotpotmaterial.anywhere.common.mvc.page.rest.response.ResultPageDTO",
      "分页返回值基类(泛型)"), PageDTO("org.hotpotmaterial.anywhere.common.mvc.page.rest.request.PageDTO",
          "分页查询参数类"), ResultJsonSchemaDTO(
              "org.hotpotmaterial.anywhere.common.mvc.rest.json.ResultJsonSchemaDTO",
              "json schema返回值基类");

  // 包名
  private final String packageName;

  // 描述
  private final String comments;

  private BaseDTO(String packageName, String comments) {
    this.packageName = packageName;
    this.comments = comments;
  }

  public String getPackageName() {
    return packageName;
  }

  public String getComments() {
    return comments;
  }

  /**
   * 获取所有枚举名称
   * 
   * @return
   */
  public static List<String> getNameList() {
    List<String> result = Lists.newArrayList();
    for (BaseDTO basedto : BaseDTO.values()) {
      result.add(basedto.name());
    }
    return result;
  }

}
