/**
 * 
 */
package com.changan.code.controller.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import com.changan.code.controller.ProjectApi;
import com.changan.code.dto.ResultDTO;
import com.changan.code.exception.CodeCommonException;

/**
 * @author wenxing
 *
 */
@Controller
public class ProjectApiController implements ProjectApi {

  @Override
  public ResponseEntity<ResultDTO> projectEditGet(
      @PathVariable(value = "id", required = true) String id) {
    throw new CodeCommonException("test");
  }

}
