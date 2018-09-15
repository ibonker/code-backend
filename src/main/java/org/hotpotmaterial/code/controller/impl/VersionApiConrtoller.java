package org.hotpotmaterial.code.controller.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.common.Constants;
import org.hotpotmaterial.code.common.RestStatus;
import org.hotpotmaterial.code.config.property.GenerateProperties;
import org.hotpotmaterial.code.controller.VersionApi;
import org.hotpotmaterial.code.dto.ResultOfVersionDTO;
import org.hotpotmaterial.code.entity.VersionPO;
import org.hotpotmaterial.code.service.IVersionService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.common.io.ByteStreams;

import lombok.extern.slf4j.Slf4j;


/**
 * 版本查询
 * @author Administrator
 *
 */
@Controller
@Slf4j
public class VersionApiConrtoller implements VersionApi {
  
  @Autowired
  private GenerateProperties genProperties;
  
  @Autowired
  private IVersionService versionService;
  /**
   * 后台版本
   */
  @Override
  public ResponseEntity<ResultDTO> codeVer() {
    //return getResult(genProperties.getVersionPath() + "code.json");
	  List<VersionPO> codeVersions = versionService.findByFlag(Constants.CODE_VERSION);
	  return new ResponseEntity<>(new ResultOfVersionDTO()
	            .versionList(codeVersions)
	            .message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
	            HttpStatus.OK);
  }

  /**
   * 前台版本
   */
  @Override
  public ResponseEntity<ResultDTO> uiVer() {
    //return getResult(genProperties.getVersionPath() + "ui.json");
	  List<VersionPO> uiVersions = versionService.findByFlag(Constants.UI_VERSION);
	  return new ResponseEntity<>(new ResultOfVersionDTO()
	            .versionList(uiVersions)
	            .message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
	            HttpStatus.OK);
  }
  
  /**
   * 读取resource中版本json文件
   * @param pathName
   * @return
   */
/*  private ResponseEntity<ResultDTO> getResult(String pathName) {
    File f = new File(pathName);
    if (f.exists()) {
      try {
        String json = new String(ByteStreams.toByteArray(new FileInputStream(f)), "UTF-8");
        JSONArray arr = new JSONArray(json);
        return new ResponseEntity<>(new ResultOfVersionDTO()
            .versionList(arr.toList())
            .message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
            HttpStatus.OK);
      } catch (IOException e) {
        log.error(e.getMessage());
      }
    }
    return null;
  }*/

@Override
public ResponseEntity<ResultDTO> versionSavePost(@RequestBody VersionPO versionPO) {
	versionService.saveVersion(versionPO);
	return new ResponseEntity<>(new ResultOfVersionDTO()
            .message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
            HttpStatus.OK);
}

@Override
public ResponseEntity<ResultDTO> deleteSavePost(@PathVariable String versionId) {
	versionService.deleteVersion(versionId);
	return new ResponseEntity<>(new ResultOfVersionDTO()
            .message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
            HttpStatus.OK);
}

}
