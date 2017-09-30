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
    // 查询重复条数
    int sameCount = transferObjRePo.countByNameAndTransferObjIdAndDelFlag(transferObjField.getName(),
        transferObjField.getTransferObjId(), Constants.DATA_IS_NORMAL);
    if (sameCount == 0) {
      // 保存DTO属性对象
      return transferObjRePo.save(transferObjField.setArrayType());
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
    TransferObjFieldPO transferObjField =
        transferObjRePo.findByIdAndDelFlag(id, Constants.DATA_IS_NORMAL);
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
    return transferObjRePo.findByIdAndDelFlag(id, Constants.DATA_IS_NORMAL);
  }

  /**
   * 更新DTO属性对象
   */
  @Override
  public TransferObjFieldPO updateTransferObjField(TransferObjFieldPO transferObjField) {
    // 查询数据库中需要更新的DTO属性对象
    TransferObjFieldPO updateTransferField =
        transferObjRePo.findByIdAndDelFlag(transferObjField.getId(), Constants.DATA_IS_NORMAL);
    // 当重复为0且数据库中存在此条数据时
    if (updateTransferField != null) {
      // 更新DTO属性对象信息
      updateTransferField.updateAttrs(transferObjField);
      // 根据name和transferObjId查询
      List<TransferObjFieldPO> transferObjFields =
          transferObjRePo.findByNameAndDelFlagAndTransferObjId(updateTransferField.getName(),
              Constants.DATA_IS_NORMAL, updateTransferField.getTransferObjId());
      // 如果长度为0则直接更新
      if (transferObjFields.isEmpty()) {
        // 保存属性
        return transferObjRePo.save(updateTransferField.setArrayType());
      } else {
        // 若长度为1则判断id是否相同
        if (transferObjFields.get(0).getId().equals(transferObjField.getId())) {
          // 保存属性
          return transferObjRePo.save(updateTransferField.setArrayType());
        } else {
          throw new CodeCommonException("更新失败！参数名重复！");
        }
      }
    } else {
      throw new CodeCommonException("更新失败！数据不存在！");
    }
  }

  /**
   * 查询所有的DTO属性对象
   */
  @Override
  public List<TransferObjFieldPO> findAllTransferObjField(String transferObjId) {
    // 获取查询结果
    return transferObjRePo.findByTransferObjIdAndDelFlag(transferObjId, Constants.DATA_IS_NORMAL);
  }

  /**
   * 批量保存DTO属性对象
   */
  @Override
  public void saveAllTransferObjField(List<TransferObjFieldPO> transferObjFields) {
    Map<String, String> map = new HashMap<>();
    // 初始化长度
    int length = 1;
    for (TransferObjFieldPO transferObjField : transferObjFields) {
      // 将属性name放入map，若map长度未增加则，name重复
      transferObjField.setArrayType();
      map.put(transferObjField.getName(), transferObjField.getName());
      if (map.size() != length) {
        throw new CodeCommonException("DTO属性重复：" + transferObjField.getName());
      }
      length++;
    }
    // 批量保存DTO属性对象
    transferObjRePo.save(transferObjFields);
  }


  /**
   * 根据transferObjId删除参数
   */
  @Override
  public void deleteByTransferObjId(String transferObjId) {
    transferObjRePo.deleteByTransferObjId(transferObjId);
  }

  /**
   * 根据排序查询所有未逻辑删除的DTO属性
   * 
   * @param delFlag
   * @return
   */
  @Override
  public List<TransferObjFieldPO> findAllTransferObjFieldBySort(String transferObjId) {
    // 获取查询结果
    return transferObjRePo.findByTransferObjIdAndDelFlagOrderBySortAsc(transferObjId, Constants.DATA_IS_NORMAL);
  }

  @Override
  public Long deleteByTransferObjIdIn(List<String> transferObjIds) {
    return transferObjRePo.deleteByTransferObjIdIn(transferObjIds);
  }
}
