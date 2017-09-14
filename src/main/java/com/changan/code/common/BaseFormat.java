/**
 * 
 */
package com.changan.code.common;

/**
 * @author wenxing
 *
 */
public enum BaseFormat {
  
  String(""),
  Integer(""),
  Long(""),
  Float(""),
  Double(""),
  BigDecimal("java.math.BigDecimal"),
  Date("java.util.Date"),
  Timestamp("java.sql.Timestamp"),
  LocalDateTime("java.time.LocalDateTime");
  
  // 包名
  private final String packageName;

  private BaseFormat(String packageName) {
    this.packageName = packageName;
  }

  public String getPackageName() {
    return this.packageName;
  }

}
