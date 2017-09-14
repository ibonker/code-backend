/**
 * 
 */
package com.changan.code.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
@JsonInclude(value = Include.NON_NULL)
public class ApiServiceName {

  private String serviceName;
  
  private String serviceNameAttr;
  
}
