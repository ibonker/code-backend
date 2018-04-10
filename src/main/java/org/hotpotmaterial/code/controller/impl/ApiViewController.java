/**
 * 
 */
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
import org.hotpotmaterial.code.controller.ApiView;
import org.hotpotmaterial.code.dto.RequestApiViewConfigDTO;
import org.hotpotmaterial.code.dto.ResultOfApiViewDTO;
import org.hotpotmaterial.code.entity.ApiViewFormConfigPO;
import org.hotpotmaterial.code.entity.ApiViewPO;
import org.hotpotmaterial.code.entity.ApiViewTableConfigPO;
import org.hotpotmaterial.code.entity.TablePO;
import org.hotpotmaterial.code.service.IApiViewService;

/**
 * @author wenxing
 *
 */
@Controller
public class ApiViewController extends BaseController implements ApiView {
  
  @Autowired
  private IApiViewService apiViewService;
  
  /**
   * 页面配置目录列表
   */
  @Override
  public ResponseEntity<ResultDTO> viewConfigList(@PathVariable(name="id") String projectId) {
    List<ApiViewPO> apiViews = apiViewService.findApiViewPO(projectId);
    return new ResponseEntity<>(new ResultOfApiViewDTO().apiViews(apiViews).message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }
  
  /**
   * 各个表对应的Api
   */
  @Override
  public ResponseEntity<ResultDTO> dtoApiList(@PathVariable(name="id") String projectId) {
    List<TablePO> tableApis = apiViewService.findApiTableList(projectId);
    return new ResponseEntity<>(new ResultOfApiViewDTO().tableApis(tableApis).message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }

  /**
   * Api页面表格配置列表
   */
  @Override
  public ResponseEntity<ResultDTO> tableConfigs(@PathVariable(name="id") String tableId) {
    List<ApiViewTableConfigPO> tableConfigs = apiViewService.findTableConfigList(tableId);
    return new ResponseEntity<>(new ResultOfApiViewDTO().tableConfigs(tableConfigs).message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }
  
  /**
   * Api页面表单配置列表
   */
  @Override
  public ResponseEntity<ResultDTO> formConfigs(@PathVariable(name="id") String tableId) {
    List<ApiViewFormConfigPO> formConfigs = apiViewService.findFormConfigList(tableId);
    return new ResponseEntity<>(new ResultOfApiViewDTO().formConfigs(formConfigs).message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }
  
  /**
   * 保存Api页面配置
   */
  @Override
  public ResponseEntity<ResultDTO> apiViewSavePost(@RequestBody ApiViewPO apiViewPO) {
    // 保存apiViewPO
    apiViewService.saveApiView(apiViewPO);
    return new ResponseEntity<>(
        new ResultDTO().message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }
  
  /**
   * 保存Api表单、表格配置
   */
  @Override
  public ResponseEntity<ResultDTO> apiViewConfigSavePost(@RequestBody
      RequestApiViewConfigDTO requestApiViewConfigDTO) {
    // 保存apiViewPO
    apiViewService.saveApiViewConfig(
        requestApiViewConfigDTO.getApiViewPO(), requestApiViewConfigDTO.getTableConfigs(), requestApiViewConfigDTO.getFormConfigs());
    return new ResponseEntity<>(
        new ResultDTO().message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }


}
