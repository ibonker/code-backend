/**
 * 
 */
package org.hotpotmaterial.code.common;

/**
 * @author wenxing
 *
 */
public enum BaseType {

  BASE("基本类型"), ARRAY("集合类型"), PO("PO实体"), DTO("DTO实体");
  
  // 中文名
  private final String cname;

  private BaseType(String cname) {
    this.cname = cname;
  }

  public String getCname() {
    return cname;
  }

}
