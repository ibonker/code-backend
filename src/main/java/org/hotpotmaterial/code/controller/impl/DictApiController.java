package org.hotpotmaterial.code.controller.impl;

import org.hotpotmaterial.anywhere.common.mvc.page.rest.request.PageDTO;
import org.hotpotmaterial.anywhere.common.mvc.page.rest.response.ResultPageDTO;
import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.common.RestStatus;
import org.hotpotmaterial.code.controller.DictApi;
import org.hotpotmaterial.code.dto.ResultDictDTO;
import org.hotpotmaterial.code.entity.DictTypePO;
import org.hotpotmaterial.code.entity.DictValuePO;
import org.hotpotmaterial.code.service.IColumnService;
import org.hotpotmaterial.code.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * DictApiController
 * 
 * @author xuyufeng
 *
 */
@Controller
public class DictApiController implements DictApi {

  /**
   * 注入IDictService
   */
  @Autowired
  IDictService dictService;
  
  @Autowired
  IColumnService columnService;

  /**
   * 查询DictType和DictValue
   */
  @Override
  public ResponseEntity<ResultDTO> DictPost(@RequestParam int version) {
    return new ResponseEntity<ResultDTO>(dictService.findAll(version)
        .message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
        HttpStatus.OK);
  }

  /**
   * 新增DictType
   */
  @Override
  public ResponseEntity<ResultDTO> DictTypePost(@RequestBody DictTypePO dictType) {
    // 保存DictType成功信息
    return new ResponseEntity<ResultDTO>(new ResultDictDTO()
        .DictType(dictService.insertDictType(dictType)).message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }

  /**
   * 删除DictType
   */
  @Override
  public ResponseEntity<ResultDTO> DictTypeDelete(@PathVariable String dictTypeId) {
    // 根据id删除DictType
    dictService.deleteDictType(dictTypeId);
    return new ResponseEntity<ResultDTO>(new ResultDTO()
        .message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
        HttpStatus.OK);
  }

  /**
   * 修改DictType
   */
  @Override
  public ResponseEntity<ResultDTO> DictTypePut(@PathVariable String dictTypeId,
      @RequestBody DictTypePO dictType) {
    // 返回成功信息
    return new ResponseEntity<ResultDTO>(new ResultDictDTO()
        .DictType(dictService.updateDictType(dictTypeId, dictType))
        .message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
        HttpStatus.OK);
  }

  /**
   * 新增DictValue
   */
  @Override
  public ResponseEntity<ResultDTO> DictValuePost(@PathVariable String code,
      @RequestBody DictValuePO dictValue) {
    // 返回新增DictValue成功信息
    return new ResponseEntity<ResultDTO>(new ResultDictDTO()
        .DictValue(dictService.insertDictValue(code, dictValue))
        .message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
        HttpStatus.OK);
  }

  /**
   * 修改DictValue
   */
  @Override
  public ResponseEntity<ResultDTO> DictValuePut(@PathVariable String dictValueId,
      @PathVariable String code, @RequestBody DictValuePO dictValue) {
    // 返回修改DictValue
    return new ResponseEntity<ResultDTO>(new ResultDictDTO()
        .DictValue(dictService.updateDictValue(dictValueId, dictValue))
        .message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
        HttpStatus.OK);
  }

  /**
   * 删除DictValue
   */
  @Override
  public ResponseEntity<ResultDTO> DictValueDelete(@PathVariable String code,
      @PathVariable String dictValueId) {
    // 删除DictValue
    dictService.deleteDictValue(dictValueId);
    return new ResponseEntity<ResultDTO>(new ResultDTO()
        .message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
        HttpStatus.OK);
  }

  /**
   * 根据id查询DictType
   */
  @Override
  public ResponseEntity<ResultDTO> DictTypeShow(@PathVariable String dictTypeId) {
    // 根据id查询DictType
    return new ResponseEntity<ResultDTO>(new ResultDictDTO()
        .DictType(dictService.findDictType(dictTypeId)).message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }

  /**
   * 根据id查询DictValue
   */
  @Override
  public ResponseEntity<ResultDTO> DictValueShow(@PathVariable String code,
      @PathVariable String dictValueId) {
    // 根据id查询DictValue
    return new ResponseEntity<ResultDTO>(new ResultDictDTO()
        .DictValue(dictService.findDictValue(dictValueId))
        .message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
        HttpStatus.OK);
  }

  /**
   * 分页查询DictType
   * 
   * @param searchParams
   * @return
   */
  @Override
  public ResponseEntity<ResultDTO> dictTypePagesPost(@RequestBody PageDTO searchParams) {
    Page<DictTypePO> result = dictService.findDictTypePage(searchParams);
    return new ResponseEntity<>(new ResultPageDTO<DictTypePO>()
        .totalElements(result.getTotalElements()).pageNumber(result.getNumber())
        .pageSize(result.getSize()).data(result.getContent())
        .message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
        HttpStatus.OK);
  }

  /**
   * 分页查询DictValue
   */
  @Override
  public ResponseEntity<ResultDTO> dictValuePagesPost(@PathVariable String code, @RequestBody PageDTO searchParams) {
    Page<DictValuePO> result = dictService.findDictValuePage(code, searchParams);
    return new ResponseEntity<>(new ResultPageDTO<DictValuePO>()
        .totalElements(result.getTotalElements()).pageNumber(result.getNumber())
        .pageSize(result.getSize()).data(result.getContent())
        .message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
        HttpStatus.OK);
  }

  /**
   * 查询所有DictType
   */
  @Override
  public ResponseEntity<ResultDTO> dictTypeAllGet(@PathVariable String tableId) {
    // 查询所有DictType
    return new ResponseEntity<ResultDTO>(new ResultDictDTO()
        .DictTypes(columnService.findDictTypes(tableId)).message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }

  /**
   * DictValue批量保存
   */
  @Override
  public ResponseEntity<ResultDTO> dictValuesPost(@PathVariable String tableId,
      @PathVariable String dictCode, @RequestBody ResultDictDTO dictTypeAndValue) {
    // 批量保存数据
    columnService.getDataSource(tableId, dictTypeAndValue.getDictType(), dictTypeAndValue.getDictValues());
    return new ResponseEntity<ResultDTO>(new ResultDTO()
        .message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
        HttpStatus.OK);
  }

  /**
   * 查询指定DictType下的DcitValue
   */
  @Override
  public ResponseEntity<ResultDTO> findValues(@PathVariable String tableId, @PathVariable String dictCode) {
    // 查询DictType下的DictValue
    return new ResponseEntity<ResultDTO>(new ResultDictDTO()
        .DictValues(columnService.findTypeAndValue(tableId, dictCode)).message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }
}
