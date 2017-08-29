/**
 * 
 */
package com.changan.code.common;

/**
 * @author wenxing
 *
 */
public enum DtoType {

  BASE("基本类型"), ARRAY("集合类型"), REFOBJ("实体");
  
  private final String cname;

  private DtoType(String cname) {
    this.cname = cname;
  }

  public String getCname() {
    return cname;
  }

}
