/**
 * 
 */
package com.changan.code.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.common.Constants;
import com.changan.code.controller.ColumnApi;
import com.changan.code.dto.ResultOfColumnDTO;
import com.changan.code.dto.ResultOfColumnsDTO;
import com.changan.code.entity.ColumnPO;
import com.changan.code.service.IColumnService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

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
   * 表字段列表schema
   * 
   * @return
   */
  @Override
  public ResponseEntity<JsonSchema> columnsSchemaGet() {
    return new ResponseEntity<JsonSchema>(
        this.getJsonSchemaByJavaType(new TypeReference<ResultOfColumnsDTO>() {}), HttpStatus.OK);
  }

  /**
   * 批量保存表字段配置
   */
  @Override
  public ResponseEntity<ResultDTO> columnsSavePost(@RequestBody List<ColumnPO> columns) {
    columns = columnService.saveConfigColumns(columns);
    return new ResponseEntity<ResultDTO>(new ResultOfColumnsDTO().columns(columns).message("成功")
        .statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }
  
  /**
   * 保存表字段配置
   */
  @Override
  public ResponseEntity<ResultDTO> columnSavePost(@RequestBody ColumnPO column) {
    column = columnService.saveConfigColumn(column);
    return new ResponseEntity<ResultDTO>(new ResultOfColumnDTO().column(column).message("成功")
        .statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }

}
