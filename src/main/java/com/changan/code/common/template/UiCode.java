/**
 * 
 */
package com.changan.code.common.template;

/**
 * @author wenxing
 *
 */
public enum UiCode {
  
  uiUitl("uicode/util.xml");
  
  private String path;
  
  private UiCode(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
  
}