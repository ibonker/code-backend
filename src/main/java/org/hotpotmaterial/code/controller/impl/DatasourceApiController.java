/**
 * 
 */
package org.hotpotmaterial.code.controller.impl;

import org.hotpotmaterial.anywhere.common.mvc.page.rest.request.PageDTO;
import org.hotpotmaterial.anywhere.common.mvc.page.rest.response.ResultPageDTO;
import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.common.Constants;
import org.hotpotmaterial.code.common.RestStatus;
import org.hotpotmaterial.code.controller.DatasourceApi;
import org.hotpotmaterial.code.entity.DatasourcePO;
import org.hotpotmaterial.code.entity.TablePO;
import org.hotpotmaterial.code.service.IDatasourceService;
import org.hotpotmaterial.code.service.ITableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
      return new ResponseEntity<>(
          new ResultDTO().message(msg).statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
    }
    return new ResponseEntity<>(
        new ResultDTO().message(msg).statusCode(Constants.EXCEPTION_API_CODE), HttpStatus.OK);
  }

  /**
   * 同步数据库表
   */
  @Override
  public ResponseEntity<ResultDTO> datasourcesTablesSyncGet(@PathVariable String id) {
    datasourceService.syncTableFromOriginalDatasource(id, "");
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
