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
import com.changan.code.common.RestStatus;
import com.changan.code.controller.TransferObjApi;
import com.changan.code.dto.ResultOfTransferObjDTO;
import com.changan.code.entity.TransferObjPO;
import com.changan.code.service.ITransferObjService;

/**
 * 
 * @author xuyufeng
 *
 */
@Controller
public class TransferObjApiController implements TransferObjApi {

  /**
   * 注入DTO接口
   */
  @Autowired
  private ITransferObjService transferObjService;

  /**
   * 查询DTO
   */
  @Override
  public ResponseEntity<ResultDTO> transferObjShowGet(@PathVariable String id) {
    // 通过id查询，获取TransferObj对象
    TransferObjPO transferObj = transferObjService.findTransferObjById(id);
    return new ResponseEntity<>(new ResultOfTransferObjDTO().transferObj(transferObj)
        .message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }

  /**
   * 保存DTO
   */
  @Override
  public ResponseEntity<ResultDTO> transferObjSavePost(@RequestBody TransferObjPO transferObj) {
    // 构建返回对象
    TransferObjPO newTransferObj;
    if (transferObj.isNew()) {
      // 保存DTO
      newTransferObj = transferObjService.saveTransferObj(transferObj);
    } else {
      // 更新DTO
      newTransferObj = transferObjService.updateTransferObj(transferObj);
    }
    return new ResponseEntity<>(new ResultOfTransferObjDTO().transferObj(newTransferObj)
        .message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }

  /**
   * 删除DTO
   */
  @Override
  public ResponseEntity<ResultDTO> transferObjDelete(@PathVariable String id) {
    // 执行逻辑删除
    transferObjService.deleteTransferObj(id);
    return new ResponseEntity<>(
        new ResultDTO().message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }

  /**
   * 查询所有DTO
   */
  @Override
  public ResponseEntity<ResultDTO> transferObjallShowGet(@PathVariable String projectId) {
    // 获得查询结果
    List<TransferObjPO> transferObjs = transferObjService.findAllTransferObj(projectId);
    return new ResponseEntity<>(new ResultOfTransferObjDTO().transferObjs(transferObjs)
        .message(RestStatus.RESULT_SUCCESS.message()).statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
  }

}
