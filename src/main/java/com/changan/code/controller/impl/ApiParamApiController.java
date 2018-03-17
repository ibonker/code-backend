package com.changan.code.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.changan.anywhere.common.mvc.rest.basic.ResultDTO;
import com.changan.code.common.Constants;
import com.changan.code.common.RestStatus;
import com.changan.code.controller.ApiParamApi;
import com.changan.code.dto.ResultOfApiParamDTO;
import com.changan.code.entity.ApiParamPO;
import com.changan.code.service.IApiParamService;
/**
 * ApiParam Contoller
 * @author xuyufeng
 *
 */
@Controller
public class ApiParamApiController implements ApiParamApi {

  /**
   * 注入ApiParam接口
   */
  @Autowired
  private IApiParamService apiParamService;

  /**
   * 保存ApiParam
   */
  @Override
  public ResponseEntity<ResultDTO> apiObjSavePost(@RequestBody List<ApiParamPO> apiParams, @RequestParam String apiObjId) {
    // 执行保存
    apiParamService.saveApiParam(apiParams, apiObjId);
    // 返回成功信息
    return new ResponseEntity<>(
        new ResultDTO().message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }

  /**
   * 查询指定ApiObj的ApiParam(有排序)
   * 
   */
  @Override
  public ResponseEntity<ResultDTO> apiObjAllShowGet(@PathVariable String apiObjId) {
    // 查询指定ApiObj的ApiParam
    List<ApiParamPO> apiParams = apiParamService.findAllApiParamOrderBySort(apiObjId);
    // 返回成功信息
    return new ResponseEntity<>(new ResultOfApiParamDTO().apiParams(apiParams)
        .message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }
}
