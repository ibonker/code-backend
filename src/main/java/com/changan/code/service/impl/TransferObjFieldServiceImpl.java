package com.changan.code.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.changan.code.common.Constants;
import com.changan.code.entity.TransferObjFieldPO;
import com.changan.code.exception.CodeCommonException;
import com.changan.code.repository.TransferObjFieldRespository;
import com.changan.code.service.ITransferObjFieldService;

/**
 * DTO属性 方法实现
 * 
 * @author xuyufeng
 *
 */
@Service
public class TransferObjFieldServiceImpl implements ITransferObjFieldService {

	/**
	 * 注入TransferObjFieldRespository对象
	 */
	@Autowired
	private TransferObjFieldRespository transferObjRePo;

	/**
	 * 保存DTO属性
	 */
	@Override
	@Transactional("jpaTransactionManager")
	public TransferObjFieldPO saveTransferObjField(TransferObjFieldPO transferObjField) {
		// 重复条数
		int sameCount = 0;
		// 查询重复条数
		sameCount = transferObjRePo.countTransferObjFieldByName(transferObjField.getName(), transferObjField.getTransferObjId(), Constants.DATA_IS_NORMAL);
		if (sameCount == 0) {
			// 保存DTO属性对象
			transferObjField = transferObjRePo.save(transferObjField);
			return transferObjField;
		} else {
			throw new CodeCommonException("保存失败！");
		}
	}

	/**
	 * 删除DTO属性
	 */
	@Override
	@Transactional("jpaTransactionManager")
	public void deleteTransferObjField(String id) {

		// 查询数据库中DTO属性对象是否存在
		TransferObjFieldPO transferObjField = transferObjRePo.findByIdAndDelFlag(id, Constants.DATA_IS_NORMAL);
		if (transferObjField != null) {
			// 逻辑删除DTO属性对象
			transferObjField.setDelFlag(Constants.DATA_IS_INVALID);
			// 保存DTO属性对象
			transferObjRePo.save(transferObjField);
		} else {
			throw new CodeCommonException("删除失败！执行删除的数据不存在！");
		}

	}

	/**
	 * 查询DTO属性对象
	 */
	@Override
	public TransferObjFieldPO findTransferObjFieldById(String id) {

		// 查询数据库中DTO属性对象
		TransferObjFieldPO transferObjField = transferObjRePo.findByIdAndDelFlag(id, Constants.DATA_IS_NORMAL);
		// 返回查询结果
		return transferObjField;

	}

	/**
	 * 更新DTO属性对象
	 */
	@Override
	public TransferObjFieldPO updateTransferObjField(TransferObjFieldPO transferObjField) {
		// 查询数据库中需要更新的DTO属性对象
		TransferObjFieldPO updateTransfer = transferObjRePo.findByIdAndDelFlag(transferObjField.getId(), Constants.DATA_IS_NORMAL);
		//当重复为0且数据库中存在此条数据时
		if (updateTransfer != null) {
			// 更新DTO属性对象信息
			updateTransfer.updateAttrs(transferObjField);
			// 保存已更新的信息
			updateTransfer = transferObjRePo.save(updateTransfer);
			return updateTransfer;
		} else {
			throw new CodeCommonException("更新失败！");
		}

	}

	/**
	 * 查询所有的DTO属性对象
	 */
	@Override
	public List<TransferObjFieldPO> findAllTransferObjField() {
		// 获取查询结果
		List<TransferObjFieldPO> transferObjFields = transferObjRePo.findByDelFlag(Constants.DATA_IS_NORMAL);
		// 返回结果
		return transferObjFields;
	}

	/**
	 * 批量保存DTO属性对象
	 */
	@Override
	public void saveAllTransferObjField(List<TransferObjFieldPO> transferObjFields) {
		Map<String, String> map = new HashMap<String,String>();
		//初始化长度
		int length = 1;
		for(TransferObjFieldPO transferObjField : transferObjFields){
			//将属性name放入map，若map长度未增加则，name重复
			map.put(transferObjField.getName(), transferObjField.getName());
			if(map.size() != length){
				throw new CodeCommonException("DTO属性重复："+transferObjField.getName());
			}
			length++;
		}
		//批量保存DTO属性对象
		transferObjRePo.save(transferObjFields);
	}

}