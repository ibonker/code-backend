/**
 * 
 */
package com.changan.code.common;

/**
 * @author wenxing
 *
 */
public enum BaseParamIn {
  
  PATH("@PathVariable", "org.springframework.web.bind.annotation.PathVariable"), 
  BODY("@RequestBody", "org.springframework.web.bind.annotation.RequestBody"), 
  QUERY("@RequestParam", "org.springframework.web.bind.annotation.RequestParam"),
  FORMDATA("", ""),
  HEADER("@RequestHeader", "org.springframework.web.bind.annotation.RequestHeader"), 
  COOKIE("@CookieValue", "org.springframework.web.bind.annotation.CookieValue");
  
  // 注解名
  private String annotation;
  
  // 注解包名
  private String annotationPack;
  
  private BaseParamIn(String annotation, String annotationPack) {
    this.annotation = annotation;
    this.annotationPack = annotationPack;
  }
  
  public String getAnnotation() {
    return this.annotation;
  }
  
  public String getAnnotationPack() {
    return this.annotationPack;
  }

}
