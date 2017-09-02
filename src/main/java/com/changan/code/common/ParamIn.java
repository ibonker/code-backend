/**
 * 
 */
package com.changan.code.common;

/**
 * @author wenxing
 *
 */
public enum ParamIn {
  
  PATH("@PathVariable"), 
  BODY("@RequestBody"), 
  QUERY("@RequestParam"), 
  HEADER("@RequestHeader"), 
  COOKIE("@CookieValue");
  
  // 注解名
  private String annotation;
  
  private ParamIn(String annotation) {
    this.annotation = annotation;
  }
  
  public String getAnnotation() {
    return this.annotation;
  }

}
