package org.hotpotmaterial.code.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.common.Constants;
import org.hotpotmaterial.code.common.RestStatus;
import org.hotpotmaterial.code.controller.ApiBaseApi;
import org.hotpotmaterial.code.dto.ResultOfApiBaseDTO;
import org.hotpotmaterial.code.entity.ApiBasePO;
import org.hotpotmaterial.code.service.IApiBaseService;

/**
 * API controller
 * 
 * @author xuyufeng
 *
 */
@Controller
public class ApiBaseApiController implements ApiBaseApi {

  /**
   * 注入api 接口
   */
  @Autowired
  IApiBaseService apiBaseService;

  /**
   * 查询所有api
   */
  @Override
  public ResponseEntity<ResultDTO> apiBaseAllShowGet(@PathVariable String projectId) {
    // 获得所有的api
    List<ApiBasePO> allApiBases = apiBaseService.findAllApiBase(projectId);
    // 返回成功信息
    return new ResponseEntity<>(new ResultOfApiBaseDTO().apiBases(allApiBases)
        .message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }

  /**
   * 保存api/修改api,当api版本号相同时操作失败
   */
  @Override
  public ResponseEntity<ResultDTO> apiBaseSavePost(@RequestBody ApiBasePO apiBase) {
    ApiBasePO newApiBase;
    if (apiBase.isNew()) {
      // 保存api
      newApiBase = apiBaseService.saveApiBase(apiBase);
    } else {
      // 更新api
      newApiBase = apiBaseService.updateApiBase(apiBase);
    }
    // 返回成功信息
    return new ResponseEntity<>(new ResultOfApiBaseDTO().apiBase(newApiBase).message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }

  /**
   * 根据id删除api
   */
  @Override
  public ResponseEntity<ResultDTO> apiBaseDelete(@PathVariable String id) {
    // 根据id删除api
    apiBaseService.deleteApiBaseById(id);
    // 返回成功信息
    return new ResponseEntity<>(
        new ResultDTO().message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }
}
