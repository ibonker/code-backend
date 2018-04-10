/**
 * 
 */
package org.hotpotmaterial.code.controller.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.common.Constants;
import org.hotpotmaterial.code.common.RestStatus;
import org.hotpotmaterial.code.controller.TableApi;
import org.hotpotmaterial.code.dto.RequestOfTableIdsDTO;
import org.hotpotmaterial.code.dto.ResultOfColumnsDTO;
import org.hotpotmaterial.code.dto.ResultOfMsgDataDTO;
import org.hotpotmaterial.code.dto.ResultOfTableRelationDTO;
import org.hotpotmaterial.code.dto.ResultOfTableSeniorRelationDTO;
import org.hotpotmaterial.code.dto.ResultOfTransferObjDTO;
import org.hotpotmaterial.code.entity.ColumnPO;
import org.hotpotmaterial.code.entity.TablePO;
import org.hotpotmaterial.code.entity.TableRelationPO;
import org.hotpotmaterial.code.entity.TableSeniorRelationPO;
import org.hotpotmaterial.code.service.ITableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wenxing
 *
 */
@Controller
public class TableApiController extends BaseController implements TableApi {

  @Autowired
  private ITableService tableService;

  /**
   * 获取表字段列表
   */
  @Override
  public ResponseEntity<ResultDTO> tablesColumnsGet(@PathVariable String id) {
    List<ColumnPO> columns = tableService.findMergedColumns(id);
    return new ResponseEntity<>(
        new ResultOfColumnsDTO().columns(columns).dictFlag(tableService.isDictionary(id))
            .message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE),
        HttpStatus.OK);
  }

  /**
   * 更改自动生成状态
   */
  @Override
  public ResponseEntity<ResultDTO> tablesAutocrudChangeGet(@PathVariable String status,
      @RequestBody RequestOfTableIdsDTO tableIds) {
    if ("active".equals(status)) {
      tableService.activeIsAutoCrud(tableIds, "");
    } else {
      tableService.inactiveIsAutoCrud(tableIds);
    }
    return new ResponseEntity<>(new ResultDTO().message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }

  /**
   * 获取table的实体详情
   */
  @Override
  public ResponseEntity<ResultDTO> tablesDtoGet(@PathVariable String id) {
    return new ResponseEntity<>(new ResultOfTransferObjDTO()
        .transferObj(tableService.transColumnPOToTransPO(id)).isDto(Constants.IS_INACTIVE)
        .message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE),
        HttpStatus.OK);
  }

  /**
   * 更新表
   */
  @Override
  public ResponseEntity<ResultDTO> tablesSavePut(@PathVariable String id,
      @RequestBody TablePO table) {
    tableService.updateTable(id, table);
    return new ResponseEntity<>(new ResultDTO().message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }

  /**
   * 生成实体文件
   * 
   * @throws FileNotFoundException
   */
  @Override
  public ResponseEntity<ResultDTO> columnsGenerateCodeGet(@PathVariable String tableId)
      throws FileNotFoundException {
    return new ResponseEntity<>(new ResultOfMsgDataDTO()
        .msgData(tableService.generateTableCodes(tableId))
        .message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
        HttpStatus.OK);
  }

  /**
   * 下载单表相关文件
   */
  @Override
  public ResponseEntity<InputStreamResource> tableDownloadGet(@PathVariable String tableName)
      throws FileNotFoundException {
    File file = tableService.downloadZipFiles(tableName);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.add("Content-Disposition",
        String.format("attachment; filename=\"%s\"", file.getName()));
    headers.add("Pragma", "no-cache");
    headers.add("Expires", "0");
    return ResponseEntity.ok().headers(headers).contentLength(file.length())
        .body(new InputStreamResource(new FileInputStream(file)));
  }

  /**
   * 新增restFul关联表
   */
  @Override
  public ResponseEntity<ResultDTO> tablesRelationSavePut(
      @RequestBody TableRelationPO tableRelation) {
    ResultOfTableRelationDTO result =
        new ResultOfTableRelationDTO().tableRelation(tableService.saveTableRelation(tableRelation));
    return new ResponseEntity<>(
        result.message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE),
        HttpStatus.OK);
  }

  /**
   * 根据id删除tableRelation
   */
  @Override
  public ResponseEntity<ResultDTO> deletTableRelation(@PathVariable String id) {
    tableService.deletTableRelation(id);
    return new ResponseEntity<>(new ResultDTO().message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);

  }

  /**
   * 根据id获取所有关联关系表
   */
  @Override
  public ResponseEntity<ResultDTO> tableRelationList(@PathVariable String id) {
    ResultOfTableRelationDTO result =
        new ResultOfTableRelationDTO().tableRelations(tableService.findTableRelationList(id));
    return new ResponseEntity<>(
        result.message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE),
        HttpStatus.OK);
  }

  /**
   * 保存高级关联
   */
  @Override
  public ResponseEntity<ResultDTO> tablesSeniorRelationSavePut(
      @RequestBody TableSeniorRelationPO tableSeniorRelation) {
    tableService.saveTableSeniorRelation(tableSeniorRelation);
    return new ResponseEntity<>(new ResultDTO().message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }

  /**
   * 根据tableId获取高级关联sqlList
   */
  @Override
  public ResponseEntity<ResultDTO> tableSeniorRelationList(@PathVariable String id) {
    ResultOfTableSeniorRelationDTO result = new ResultOfTableSeniorRelationDTO()
        .seniorRelationList(tableService.findTableSeniorRelationSqlList(id));
    return new ResponseEntity<>(
        result.message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE),
        HttpStatus.OK);
  }

  /**
   * 删除高级关联关系
   */
  @Override
  public ResponseEntity<ResultDTO> deletTableSeniorRelation(@PathVariable String id) {
    tableService.deletTableSeniorRelation(id);
    return new ResponseEntity<>(new ResultDTO().message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }

  /**
   * 根据id获取指定高级关联关系表
   * 
   * @param id
   * @return
   */
  @Override
  public ResponseEntity<ResultDTO> tableSeniorRelation(@PathVariable String id) {
    ResultOfTableSeniorRelationDTO result = new ResultOfTableSeniorRelationDTO()
        .seniorRelation(tableService.findOnetableSeniorRelation(id));
    return new ResponseEntity<>(
        result.message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE),
        HttpStatus.OK);
  }
}
