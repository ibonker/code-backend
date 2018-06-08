package org.hotpotmaterial.code.controller.impl;

import java.io.File;
import java.io.IOException;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.anywhere.common.utils.FileUtils;
import org.hotpotmaterial.code.common.RestStatus;
import org.hotpotmaterial.code.controller.VersionApi;
import org.hotpotmaterial.code.dto.ResultOfVersionDTO;
import org.json.JSONArray;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.google.common.io.ByteStreams;


@Controller
public class VersionApiConrtoller implements VersionApi {

  @Override
  public ResponseEntity<ResultDTO> codeVer() {
    return getResult("version/code.json");
  }

  @Override
  public ResponseEntity<ResultDTO> uiVer() {
    return getResult("version/ui.json");
  }
  
  private ResponseEntity<ResultDTO> getResult(String pathName) {
    Resource resource = new ClassPathResource(pathName);
    try {
      String json = new String(ByteStreams.toByteArray(resource.getInputStream()), "UTF-8");
      JSONArray arr = new JSONArray(json);
      return new ResponseEntity<>(new ResultOfVersionDTO()
          .versionList(arr.toList())
          .message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
          HttpStatus.OK);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}
