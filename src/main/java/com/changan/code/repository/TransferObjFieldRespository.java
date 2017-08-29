package com.changan.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.changan.code.entity.TransferObjFieldPO;

public interface TransferObjFieldRespository extends JpaRepository<TransferObjFieldPO, String>,
    JpaSpecificationExecutor<TransferObjFieldPO> {

  /**
   * 查询DTO属性名称重复行
   * 
   * @param name
   * @param transferObjId
   * @return
   */
  public int countByNameAndTransferObjIdAndDelFlag(String name, String transferObjId, String delFlag);

  /**
   * 查询所有未逻辑删除的DTO属性
   * 
   * @param delFlag
   * @return
   */
  public List<TransferObjFieldPO> findByTransferObjIdAndDelFlag(String TransferObjId, String delFlag);

  /**
   * 根据id查询DTO属性
   * 
   * @param id
   * @param delFlag
   * @return
   */
  public TransferObjFieldPO findByIdAndDelFlag(String id, String delFlag);

  /**
   * 根据transferObjId删除数据
   * 
   * @param apiObjId
   * @return
   */
  public Long deleteByTransferObjId(String transferObjId);
}