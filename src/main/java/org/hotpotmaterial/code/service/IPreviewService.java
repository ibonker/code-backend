/**
 * 
 */
package org.hotpotmaterial.code.service;

import org.hotpotmaterial.code.dto.ResultOfPreviewDTO;

/**
 * @author wenxing
 *
 */
public interface IPreviewService {
  
  /**
   * 预览代码
   * @param tableId
   * @return
   */
  public ResultOfPreviewDTO preview(String tableId);
}
