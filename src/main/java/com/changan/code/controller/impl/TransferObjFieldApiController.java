package com.changan.code.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.common.Constants;
import com.changan.code.controller.TransferObjFieldApi;
import com.changan.code.dto.ResultOfTransferObjFieldDTO;
import com.changan.code.entity.TransferObjFieldPO;
import com.changan.code.service.ITransferObjFieldService;

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
		return new ResponseEntity<ResultDTO>(new ResultOfTransferObjFieldDTO().transferObjField(transferObjField)
				.message("成功").statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
	}

	/**
	 * 删除DTO属性
	 */
	@Override
	public ResponseEntity<ResultDTO> transferObjFieldDeleteGet(@PathVariable String id) {
		// 执行逻辑删除
		transferObjFieldService.deleteTransferObjField(id);
		// 返回成功信息
		return new ResponseEntity<ResultDTO>(new ResultDTO().message("成功").statusCode(Constants.SUCCESS_API_CODE),
				HttpStatus.OK);
	}

	/**
	 * 保存或修改DTO属性
	 */
	@Override
	public ResponseEntity<ResultDTO> transferObjFieldSavePost(@RequestBody TransferObjFieldPO transferObjField) {
		TransferObjFieldPO newTransferObjField =  new TransferObjFieldPO();
		// 数据库中无此数据，则执行sava，否则执行update
		if (transferObjField.isNew()) {
			// 保存DTO属性
			newTransferObjField = transferObjFieldService.saveTransferObjField(transferObjField);
		} else {
			// 更新DTO属性
			newTransferObjField = transferObjFieldService.updateTransferObjField(transferObjField);
		}
		// 返回成功信息
		return new ResponseEntity<ResultDTO>(new ResultOfTransferObjFieldDTO().transferObjField(newTransferObjField)
				.message("成功").statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
	}

	/**
	 * 查询所有DTO属性
	 */
	@Override
	public ResponseEntity<ResultDTO> transferObjFieldAllShowGet() {
		// 获取查询结果
		List<TransferObjFieldPO> transferObjFields = transferObjFieldService.findAllTransferObjField();
		//返回成功信息
		return new ResponseEntity<ResultDTO>(new ResultOfTransferObjFieldDTO().transferObjFields(transferObjFields)
				.message("成功").statusCode(Constants.SUCCESS_API_CODE), HttpStatus.OK);
	}

	/**
	 * 批量保存DTO属性
	 */
	@Override
	public ResponseEntity<ResultDTO> transferObjFielSaveAllPost(@RequestBody List<TransferObjFieldPO> transferObjFields) {
		//批量保存DTO属性
		transferObjFieldService.saveAllTransferObjField(transferObjFields);
		// 返回成功信息
		return new ResponseEntity<ResultDTO>(new ResultDTO().message("成功").statusCode(Constants.SUCCESS_API_CODE),
				HttpStatus.OK);
	}

}
