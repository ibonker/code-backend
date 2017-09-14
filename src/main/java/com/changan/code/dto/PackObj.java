/**
 * 
 */
package com.changan.code.dto;

import java.util.List;

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
public class PackObj {
  
  private String packName;
  
  private List<SimpleDataObj> data;

}
