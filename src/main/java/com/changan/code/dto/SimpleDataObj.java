/**
 * 
 */
package com.changan.code.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wenxing
 *
 */
@Data
@AllArgsConstructor
public class SimpleDataObj {

  private String id; // id
  
  private String className; // 名称
  
  private String isGeneric; // 是否泛型
  
}
