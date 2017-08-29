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
public class RefObjDTO {

  private String code; // 英文名code
  
  private String cname; // 中文名
  
  private List<PackObj> dto; // dto数据，key为包名
  
  private List<PackObj> po; // po数据，key为数据库的包名
  
}
