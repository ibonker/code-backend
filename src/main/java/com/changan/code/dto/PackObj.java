/**
 * 
 */
package com.changan.code.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wenxing
 *
 */
@Data
@AllArgsConstructor
public class PackObj {
  
  private String packName;
  
  private List<SimpleDataObj> data;

}
