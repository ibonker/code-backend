/**
 * 
 */
package com.changan.code.dto;

import java.util.List;

import com.changan.code.entity.TableSeniorSlavePO;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wenxing
 *
 */
@Data
@AllArgsConstructor
public class SeniorDtoRelation {
  
  private String name;
  
  private List<SeniorDtoAttribute> attrs;
  
  private List<TableSeniorSlavePO> slaves;
  
}
