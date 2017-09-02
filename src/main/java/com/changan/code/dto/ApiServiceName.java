/**
 * 
 */
package com.changan.code.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wenxing
 *
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
public class ApiServiceName {

  private String serviceName;
  
  private String serviceNameAttr;
  
}
