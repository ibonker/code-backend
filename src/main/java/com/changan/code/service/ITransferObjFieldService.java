package com.changan.code.service;

import java.util.List;

import com.changan.code.entity.TransferObjFieldPO;

/**
 * 
 * @author xuyufeng
 *
 */
public interface ITransferObjFieldService {

  /**
   * 保存DTO属性对象
   * 
   * @param transferObjField
   */
  public TransferObjFieldPO saveTransferObjField(TransferObjFieldPO transferObjField);

  /**
   * 删除DTO属性对象
   * 
   * @param id
   */
  public void deleteTransferObjField(String id);

  /**
   * 根据id查询DTO属性对象
   * 
   * @param id
   * @return
   */
  public TransferObjFieldPO findTransferObjFieldById(String id);

  /**
   * 更新DTO属性对象
   * 
   * @param transferObjField
   */
  public TransferObjFieldPO updateTransferObjField(TransferObjFieldPO transferObjField);

  /**
   * 查询所有的DTO属性对象
   * 
   * @return
   */
  public List<TransferObjFieldPO> findAllTransferObjField(String transferObjId);
  
  /**
   * 根据排序查询所有的DTO属性对象
   * 
   * @return
   */
  public List<TransferObjFieldPO> findAllTransferObjFieldBySort(String transferObjId);

  /**
   * 批量保存DTO属性
   * 
   * @return
   */
  public void saveAllTransferObjField(List<TransferObjFieldPO> transferObjFields);

  /**
   * 根据transferObjId删除数据
   * 
   * @param apiObjId
   * @return
   */
  public void deleteByTransferObjId(String transferObjId);
  
  /**
   * 根据transferObjId列表删除数据
   * 
   * @param apiObjId
   * @return
   */
  public Long deleteByTransferObjIdIn(List<String> transferObjIds);
}
