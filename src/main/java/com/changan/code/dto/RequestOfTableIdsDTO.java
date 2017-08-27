/**
 * 
 */
package com.changan.code.dto;

import java.util.List;

import lombok.Data;

/**
 * @author wenxing
 *
 */
@Data
public class RequestOfTableIdsDTO {
  
  // 数据源名称
  private String datasourceId;
  // table的id列表
  private List<String> ids;

}
