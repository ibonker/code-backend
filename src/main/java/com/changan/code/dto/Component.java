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
@JsonInclude(value = Include.NON_NULL)
public class Component {
  
  private String code;
  
  private String cname;
  
}
