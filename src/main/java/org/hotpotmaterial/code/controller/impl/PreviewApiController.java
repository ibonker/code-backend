/**
 * 
 */
package org.hotpotmaterial.code.controller.impl;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.common.RestStatus;
import org.hotpotmaterial.code.controller.PreviewApi;
import org.hotpotmaterial.code.service.IPreviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author wenxing
 *
 */
@Controller
public class PreviewApiController extends BaseController implements PreviewApi {

  @Autowired
  private IPreviewService previewService;
  
  /**
   * 预览代码
   */
  @Override
  public ResponseEntity<ResultDTO> previewDTO(@PathVariable String id) {
    return new ResponseEntity<>(previewService.preview(id)
        .message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
        HttpStatus.OK);
  }


}
