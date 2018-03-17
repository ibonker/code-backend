/**
 * 
 */
package com.changan.code.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.changan.anywhere.common.mvc.page.rest.request.PageDTO;
import com.changan.anywhere.common.mvc.rest.basic.ResultDTO;
import com.changan.anywhere.common.mvc.page.rest.response.ResultPageDTO;
import com.changan.code.common.Constants;
import com.changan.code.common.RestStatus;
import com.changan.code.controller.DatasourceApi;
import com.changan.code.entity.DatasourcePO;
import com.changan.code.entity.TablePO;
import com.changan.code.service.IDatasourceService;
import com.changan.code.service.ITableService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

/**
 * @author wenxing
 *
 */
@Controller
public class DatasourceApiController extends BaseController implements DatasourceApi {

  // 注入数据源service
  @Autowired
  private IDatasourceService datasourceService;

  // 注入表service
  @Autowired
  private ITableService tableService;
 
  /**
   * 检测数据源连接
   */
  @Override
  public ResponseEntity<ResultDTO> datasourceCheckPost(@RequestBody DatasourcePO datasource) {
    String msg = "数据源连接失败";
    if (datasourceService.checkDatasource(datasource)) {
      msg = "数据源连接成功";
    }
    return new ResponseEntity<>(
        new ResultDTO().message(msg).statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }

  /**
   * 同步数据库表
   */
  @Override
  public ResponseEntity<ResultDTO> datasourcesTablesSyncGet(@PathVariable String id) {
    datasourceService.syncTableFromOriginalDatasource(id, getUser().getUsername());
    return new ResponseEntity<>(
        new ResultDTO().message("同步成功").statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }

  /**
   * 数据源表分页
   */
  @Override
  public ResponseEntity<ResultDTO> projectsGet(@PathVariable String id,
      @RequestBody PageDTO searchParams) {
    Page<TablePO> result = tableService.findTablesPage(id, searchParams);
    return new ResponseEntity<>(
        new ResultPageDTO<TablePO>().totalElements(result.getTotalElements())
            .pageNumber(result.getNumber()).pageSize(result.getSize()).data(result.getContent())
            .message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE),
        HttpStatus.OK);
  }

}
