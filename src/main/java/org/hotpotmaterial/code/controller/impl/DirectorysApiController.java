package org.hotpotmaterial.code.controller.impl;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.common.RestStatus;
import org.hotpotmaterial.code.controller.DirectorysApi;
import org.hotpotmaterial.code.dto.ResultOfDirectoryDTO;
import org.hotpotmaterial.code.entity.DirectoryPO;
import org.hotpotmaterial.code.service.IDirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/** 
 * 控制器实现类
 * @author Hotpotmaterial-Code2
 */
@Controller
public class DirectorysApiController extends BaseController implements DirectorysApi {

  // 注入业务bean - IDirectoryService
  @Autowired
  private IDirectoryService directoryService;
 
  /**
   * 实体new.titancode.test.DirectoryPO新增
   */
  @Override
  public ResponseEntity<ResultDTO> directorysPost(@RequestBody(required = true) DirectoryPO directory) {
    directoryService.insertDirectory(directory);
    return new ResponseEntity<ResultDTO>(new ResultOfDirectoryDTO().message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }
  
  /**
   * 实体new.titancode.test.DirectoryPO更新
   */
  @Override
  public ResponseEntity<ResultDTO> directorysPut(@PathVariable(required = true) String directoryId, @RequestBody(required = true) DirectoryPO directory) {
    directoryService.updateDirectory(directoryId, directory);
    return new ResponseEntity<ResultDTO>(new ResultOfDirectoryDTO().message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }
  /**
   * 实体new.titancode.test.DirectoryPO删除
   */
  @Override
  public ResponseEntity<ResultDTO> directorysDelete(@PathVariable(required = true) String directoryId) {
    directoryService.deleteById(directoryId);
    return new ResponseEntity<ResultDTO>(new ResultDTO().message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }
  /**
   * 实体new.titancode.test.DirectoryPO详情
   */
  @Override
  public ResponseEntity<ResultDTO> directorysGET(@PathVariable(required = true) String directoryId) {
    return new ResponseEntity<ResultDTO>(new ResultOfDirectoryDTO()
        .directory(directoryService.findById(directoryId))
        .message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }
  
  @Override
  public ResponseEntity<ResultDTO> directorysGetAll() {
  	return new ResponseEntity<ResultDTO>(new ResultOfDirectoryDTO()
  	        .directoryList(directoryService.findAll())
  	        .message(RestStatus.RESULT_SUCCESS.message())
  	        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }
  @Override
  public ResponseEntity<ResultDTO> directorysById(@PathVariable String directoryId) {
  	return new ResponseEntity<ResultDTO>(new ResultOfDirectoryDTO()
  	        .directoryList(directoryService.findDirectorysById(directoryId))
  	        .message(RestStatus.RESULT_SUCCESS.message())
  	        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }
  
}