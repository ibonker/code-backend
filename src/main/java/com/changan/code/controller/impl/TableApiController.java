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

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.common.Constants;
import com.changan.code.controller.TableApi;
import com.changan.code.dto.RequestOfTableIdsDTO;
import com.changan.code.dto.ResultOfColumnsDTO;
import com.changan.code.entity.ColumnPO;
import com.changan.code.entity.TablePO;
import com.changan.code.service.ITableService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

/**
 * @author wenxing
 *
 */
@Controller
public class TableApiController extends BaseController implements TableApi {

  @Autowired
  private ITableService tableService;

  /**
   * 获取table schema
   */
  @Override
  public ResponseEntity<JsonSchema> tablesSchemaGet() {
    return new ResponseEntity<JsonSchema>(
        this.getJsonSchemaByJavaType(new TypeReference<TablePO>() {}), HttpStatus.OK);
  }

  /**
   * 获取表字段列表
   */
  @Override
  public ResponseEntity<ResultDTO> tablesColumnsGet(@PathVariable String id) {
    List<ColumnPO> columns = tableService.findMergedColumns(id);
    return new ResponseEntity<ResultDTO>(new ResultOfColumnsDTO().columns(columns).message("成功")
        .statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }

  /**
   * 更改自动生成状态
   */
  @Override
  public ResponseEntity<ResultDTO> tablesAutocrudChangeGet(@PathVariable String status,
      @RequestBody RequestOfTableIdsDTO tableIds) {
    if ("active".equals(status)) {
      
    } else {
      
    }
    return new ResponseEntity<ResultDTO>(
        new ResultDTO().message("成功").statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }

}
