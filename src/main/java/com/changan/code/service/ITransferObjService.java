package com.changan.code.service;

import java.util.List;
import java.util.Map;

import com.changan.code.dto.SimpleDataObj;
import com.changan.code.entity.TransferObjPO;

/**
 * 
 * @author xuyufeng
 *
 */
public interface ITransferObjService {

  /**
   * 更新DTO
   * 
   * @param transferObj
   */
  public TransferObjPO updateTransferObj(TransferObjPO transferObj);

  /**
   * 保存DTO
   * 
   * @param transferObj
   */
  public TransferObjPO saveTransferObj(TransferObjPO transferObj);

  /**
   * 删除DTO
   * 
   * @param transferObj
   */
  public void deleteTransferObj(String id);

  /**
   * 根据id获取DTO
   * 
   * @param id
   * @return
   */
  public TransferObjPO findTransferObjById(String id);

  /**
   * 查询所有的DTO
   * 
   * @return
   */
  public List<TransferObjPO> findAllTransferObj(String projectId);
  
  /**
   * 获取自定义实体类名
   * 
   * @param projectId
   * @return
   */
  public Map<String, List<SimpleDataObj>> findClassnameByProjectId(String projectId);

  /**
   * 自动创建实体
   * 
   * @param tableName 表名app_info
   * @param datasourcePName 包名ddemo.test
   * @param className 类名 ddemo.test.AppInfoPO
   * @return
   */
  public TransferObjPO createAutoCrudDTO(String projectId, String tableId, String tableName,
      String datasourcePName, String className);

  /**
   * 根据tableId删除api
   * 
   * @param tableId
   */
  public void deleteAutoCrudDTO(String tableId);
  
  /**
   * 根据名称获取默认基础dto
   * @param name
   */
  public TransferObjPO getDefaultDtoByName(String name);
  
  /**
   * 确认是否为基础dto
   * @param name
   */
  public boolean checkIfIsDefaultDto(String name);
}
