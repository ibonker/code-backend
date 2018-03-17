/**
 * 
 */
package com.changan.code.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.changan.anywhere.common.mvc.rest.basic.ResultDTO;
import com.changan.code.common.Constants;
import com.changan.code.common.RestStatus;
import com.changan.code.controller.ColumnApi;
import com.changan.code.dto.ResultOfColumnDTO;
import com.changan.code.dto.ResultOfColumnsDTO;
import com.changan.code.entity.ColumnPO;
import com.changan.code.service.IColumnService;

/**
 * @author wenxing
 *
 */
@Controller
public class ColumnApiController extends BaseController implements ColumnApi {

  // 注入表字段service
  @Autowired
  private IColumnService columnService;

  /**
   * 批量保存表字段配置
   */
  @Override
  public ResponseEntity<ResultDTO> columnsSavePost(@RequestBody List<ColumnPO> columns) {
    columns = columnService.saveConfigColumns(columns);
    return new ResponseEntity<>(new ResultOfColumnsDTO().columns(columns).message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }
  
  /**
   * 保存表字段配置
   */
  @Override
  public ResponseEntity<ResultDTO> columnSavePost(@RequestBody ColumnPO column) {
    column = columnService.saveConfigColumn(column);
    return new ResponseEntity<>(new ResultOfColumnDTO().column(column).message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }
  
  /**
   * 保存表字段和字典表配置
   */
  @Override
  public ResponseEntity<ResultDTO> columnAndDictSavePost(@RequestBody ResultOfColumnDTO column,@PathVariable String id) {
    //保存表字段和字典表配置
    columnService.saveColumnAndDict(id,column);
    return new ResponseEntity<>(new ResultDTO().message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }
}
