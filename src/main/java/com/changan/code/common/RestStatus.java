/**
 * 
 */
package com.changan.code.common;

import com.changan.anywhere.common.mvc.rest.basic.BaseRestStatus;

/**
 * @author wenxing
 *
 */
public class RestStatus extends BaseRestStatus {

  public RestStatus(String code, String description, String message) {
    super(code, description, message);
  }

}
