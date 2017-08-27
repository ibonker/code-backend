/**
 * 
 */
package com.changan.code.dto;

import java.util.List;
import java.util.Map;

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
  
  private List<SimpleDataObj> dto; // dto数据
  
  private Map<String, List<SimpleDataObj>> po; // po数据，key为数据库的包名
  
}
