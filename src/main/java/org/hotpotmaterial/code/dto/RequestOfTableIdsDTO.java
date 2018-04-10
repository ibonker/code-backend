/**
 * 
 */
package org.hotpotmaterial.code.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * @author wenxing
 *
 */
@Data
@JsonInclude(value = Include.NON_NULL)
public class RequestOfTableIdsDTO {
  
  // 数据源名称
  private String datasourceId;
  // table的id列表
  private List<String> ids;

}
