/**
 * 
 */
package com.changan.code.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wenxing
 *
 */
@Data
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class SimpleDataObj {

  private String id; // id
  
  private String className; // 名称
  
  private String isGeneric; // 是否泛型
  
  private String packageName; // 包名
  
}
