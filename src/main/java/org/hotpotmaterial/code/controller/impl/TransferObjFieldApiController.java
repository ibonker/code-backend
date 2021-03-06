package org.hotpotmaterial.code.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.common.Constants;
import org.hotpotmaterial.code.common.RestStatus;
import org.hotpotmaterial.code.controller.TransferObjFieldApi;
import org.hotpotmaterial.code.dto.ResultOfTransferObjFieldDTO;
import org.hotpotmaterial.code.entity.TransferObjFieldPO;
import org.hotpotmaterial.code.service.ITransferObjFieldService;

/**
 * TransferObjField API Contoller
 * @author xuyufeng
 *
 */
@Controller
public class TransferObjFieldApiController implements TransferObjFieldApi {

  /**
   * 注入DTO属性接口
   */
  @Autowired
  private ITransferObjFieldService transferObjFieldService;

  /**
   * 通过id查询DTO属性
   */
  @Override
  public ResponseEntity<ResultDTO> transferObjFieldShowGet(@RequestParam String id) {
    // 获得查询到的DTO属性对象
    TransferObjFieldPO transferObjField = transferObjFieldService.findTransferObjFieldById(id);
    // 返回成功信息
    return new ResponseEntity<>(new ResultOfTransferObjFieldDTO()
        .transferObjField(transferObjField).message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE),
        HttpStatus.OK);
  }

  /**
   * 删除DTO属性
   */
  @Override
  public ResponseEntity<ResultDTO> transferObjFieldDelete(@PathVariable String id) {
    // 执行逻辑删除
    transferObjFieldService.deleteTransferObjField(id);
    // 返回成功信息
    return new ResponseEntity<>(
        new ResultDTO().message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }

  /**
   * 保存或修改DTO属性
   */
  @Override
  public ResponseEntity<ResultDTO> transferObjFieldSavePost(
      @RequestBody TransferObjFieldPO transferObjField) {
    TransferObjFieldPO newTransferObjField;
    // 数据库中无此数据，则执行sava，否则执行update
    if (transferObjField.isNew()) {
      // 保存DTO属性
      newTransferObjField = transferObjFieldService.saveTransferObjField(transferObjField);
    } else {
      // 更新DTO属性
      newTransferObjField = transferObjFieldService.updateTransferObjField(transferObjField);
    }
    // 返回成功信息
    return new ResponseEntity<>(new ResultOfTransferObjFieldDTO()
        .transferObjField(newTransferObjField).message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE),
        HttpStatus.OK);
  }

  /**
   * 查询所有DTO属性
   */
  @Override
  public ResponseEntity<ResultDTO> transferObjFieldAllShowGet(@PathVariable String transferObjId) {
    // 获取查询结果
    List<TransferObjFieldPO> transferObjFields = transferObjFieldService.findAllTransferObjField(transferObjId);
    // 返回成功信息
    return new ResponseEntity<>(new ResultOfTransferObjFieldDTO()
        .transferObjFields(transferObjFields).message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE),
        HttpStatus.OK);
  }

  /**
   * 批量保存DTO属性
   */
  @Override
  public ResponseEntity<ResultDTO> transferObjFielSaveAllPost(
      @RequestBody List<TransferObjFieldPO> transferObjFields) {
    // 批量保存DTO属性
    transferObjFieldService.saveAllTransferObjField(transferObjFields);
    // 返回成功信息
    return new ResponseEntity<>(
        new ResultDTO().message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }
}
