/**
 * 
 */
package org.hotpotmaterial.code.dto;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(Include.NON_NULL)
public class ResultOfAppDTO extends ResultDTO {

  // appid
  private String appId;
  // app公钥
  private String appPublicKey;
  // 环境
  private String env;
  
}
