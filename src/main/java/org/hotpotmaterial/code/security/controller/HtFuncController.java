package org.hotpotmaterial.code.security.controller;

import org.hotpotmaterial.anywhere.common.mvc.page.rest.request.PageDTO;
import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.common.RestStatus;
import org.hotpotmaterial.code.controller.impl.BaseController;
import org.hotpotmaterial.code.security.entity.HotpotFunctionPO;
import org.hotpotmaterial.code.security.service.IFuncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/funcMag")
public class HtFuncController extends BaseController{
  
  @Autowired
  private IFuncService funcService;
  
  /**
   * 获取所有菜单
   * @param searchParams
   * @return
   */
  @PostMapping("/funcs")
  @ResponseBody
  public ResponseEntity<ResultDTO> list(@RequestBody PageDTO searchParams){
    return new ResponseEntity<ResultDTO>(funcService.getHotpotFunctionList(searchParams)
        .message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }
  
  /**
   * 新增菜单
   * @param hotpotFunctionPO
   * @return
   */
  @PostMapping("/func")
  @ResponseBody
  public ResponseEntity<ResultDTO> saveFunc(@RequestBody HotpotFunctionPO hotpotFunctionPO){
    funcService.saveFunc(hotpotFunctionPO, getUser());
    return new ResponseEntity<ResultDTO>(new ResultDTO().message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }
  
  /**
   * 更新菜单
   * @param id
   * @param hotpotFunctionPO
   * @return
   */
  @PutMapping("/func/{id}")
  @ResponseBody
  public ResponseEntity<ResultDTO> updateFunc(@PathVariable String id ,@RequestBody HotpotFunctionPO hotpotFunctionPO){
    funcService.updateFunc(id, hotpotFunctionPO);
    return new ResponseEntity<ResultDTO>(new ResultDTO().message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }
  
  /**
   * 删除菜单
   * @param id
   * @return
   */
  @DeleteMapping("/func/{id}")
  @ResponseBody
  public ResponseEntity<ResultDTO> saveOrg(@PathVariable String id){
    if (funcService.delete(id) > 0) {
      return new ResponseEntity<ResultDTO>(new ResultDTO().message(RestStatus.RESULT_SUCCESS.message())
          .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
    } else {
      return new ResponseEntity<ResultDTO>(new ResultDTO().message("必须先删除子节点!")
          .statusCode(RestStatus.RESULT_SYSTEM_ERROR.code()), HttpStatus.BAD_REQUEST);
    }
  }
  
}