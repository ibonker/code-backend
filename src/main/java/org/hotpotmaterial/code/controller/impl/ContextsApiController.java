package org.hotpotmaterial.code.controller.impl;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.common.RestStatus;
import org.hotpotmaterial.code.controller.ContextsApi;
import org.hotpotmaterial.code.dto.ResultOfContextDTO;
import org.hotpotmaterial.code.entity.ContextPO;
import org.hotpotmaterial.code.service.IContextService;
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
public class ContextsApiController extends BaseController implements ContextsApi {

  // 注入业务bean - IContextService
  @Autowired
  private IContextService contextService;
  
  /**
   * 实体new.titancode.test.ContextPO新增
   */
  @Override
  public ResponseEntity<ResultDTO> contextsPost(@RequestBody(required = true) ContextPO context) {
    contextService.insertContext(context);
    return new ResponseEntity<ResultDTO>(new ResultOfContextDTO().message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }
 
 
  /**
   * 实体new.titancode.test.ContextPO删除
   */
  @Override
  public ResponseEntity<ResultDTO> contextsDelete(@PathVariable(required = true) String contextId) {
    contextService.deleteById(contextId);
    return new ResponseEntity<ResultDTO>(new ResultDTO().message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }
  /**
   * 实体new.titancode.test.ContextPO更新
   */
  @Override
  public ResponseEntity<ResultDTO> contextsPut(@PathVariable(required = true) String contextId, @RequestBody(required = true) ContextPO context) {
    contextService.updateContext(contextId, context);
    return new ResponseEntity<ResultDTO>(new ResultOfContextDTO().message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }
  /**
   * 实体new.titancode.test.ContextPO详情
   */
  @Override
  public ResponseEntity<ResultDTO> contextsGET(@PathVariable(required = true) String contextId) {
    return new ResponseEntity<ResultDTO>(new ResultOfContextDTO()
        .context(contextService.findById(contextId))
        .message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }
@Override
public ResponseEntity<ResultDTO> contextsByDirectoryId(@PathVariable String directoryId) {
	return new ResponseEntity<ResultDTO>(new ResultOfContextDTO()
	        .context(contextService.findBydirectoryId(directoryId))
	        .message(RestStatus.RESULT_SUCCESS.message())
	        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
}
  
}