package com.changan.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.changan.code.entity.TransferObjFieldPO;

public interface TransferObjFieldRespository 
	extends JpaRepository<TransferObjFieldPO, String>, JpaSpecificationExecutor<TransferObjFieldPO>{

	public List<TransferObjFieldPO> findByTransferObjIdAndDelFlag(String transferObjId, String delFlag);
	
	/**
	 * 查询DTO属性名称重复行
	 * @param name
	 * @param transferObjId
	 * @return
	 */
	@Query("SELECT count(0) FROM TransferObjFieldPO t WHERE t.name = :name AND t.transferObjId = :transferObjIdn AND t.delFlag = :delFlag")
	public int countTransferObjFieldByName(@Param("name") String name,@Param("transferObjId") String transferObjId ,@Param("delFlag") String delFlag);
	
	/**
	 * 查询所有未逻辑删除的DTO属性
	 * @param delFlag
	 * @return
	 */
	public List<TransferObjFieldPO> findByDelFlag(String delFlag);
	
	/**
	 * 根据id查询DTO属性
	 * @param id
	 * @param delFlag
	 * @return
	 */
	public TransferObjFieldPO findByIdAndDelFlag(String id,String delFlag);
}
