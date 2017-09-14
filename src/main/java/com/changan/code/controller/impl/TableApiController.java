/**
 * 
 */
package com.changan.code.controller.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.common.Constants;
import com.changan.code.common.RestStatus;
import com.changan.code.controller.TableApi;
import com.changan.code.dto.RequestOfTableIdsDTO;
import com.changan.code.dto.ResultOfColumnsDTO;
import com.changan.code.dto.ResultOfMsgDataDTO;
import com.changan.code.dto.ResultOfTransferObjDTO;
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
    return new ResponseEntity<>(new ResultOfColumnsDTO().columns(columns).message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }

  /**
   * 更改自动生成状态
   */
  @Override
  public ResponseEntity<ResultDTO> tablesAutocrudChangeGet(@PathVariable String status,
      @RequestBody RequestOfTableIdsDTO tableIds) {
    if ("active".equals(status)) {
      tableService.activeIsAutoCrud(tableIds);
    } else {
      tableService.inactiveIsAutoCrud(tableIds);
    }
    return new ResponseEntity<>(
        new ResultDTO().message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }

  /**
   * 获取table的实体详情
   */
  @Override
  public ResponseEntity<ResultDTO> tablesDtoGet(@PathVariable String id) {
    return new ResponseEntity<>(
        new ResultOfTransferObjDTO().transferObj(tableService.transColumnPOToTransPO(id))
            .isDto(Constants.IS_INACTIVE).message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE),
        HttpStatus.OK);
  }

  /**
   * 更新表
   */
  @Override
  public ResponseEntity<ResultDTO> tablesSavePut(@PathVariable String id,
      @RequestBody TablePO table) {
    tableService.updateTable(id, table);
    return new ResponseEntity<>(
        new ResultDTO().message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }
  /**
   * 生成实体文件
   */
  @Override
  public ResponseEntity<ResultDTO> columnsGenerateCodeGet(@PathVariable String tableId) {
    return new ResponseEntity<>(
        new ResultOfMsgDataDTO().msgData(tableService.generateEntityCodeFiles(tableId)).message(RestStatus.RESULT_SUCCESS.message())
            .statusCode(Constants.SUCCESS_API_CODE),
        HttpStatus.OK);
  }

  /**
   * 下载实体文件
   */
  @Override
  public ResponseEntity<InputStreamResource> projectsDownloadGet(@RequestParam String tableId)
      throws FileNotFoundException {
    File file = tableService.downLoadFile(tableId);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.add("Content-Disposition",
        String.format("attachment; filename=\"%s\"", file.getName()));
    headers.add("Pragma", "no-cache");
    headers.add("Expires", "0");
    return ResponseEntity.ok().headers(headers).contentLength(file.length())
        .body(new InputStreamResource(new FileInputStream(file)));
  }
}
