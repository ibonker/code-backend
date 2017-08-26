package com.changan.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.changan.code.entity.TransferObjPO;

/**
 * 
 * @author xuyufeng
 *
 */
public interface TransferObjRespository
		extends JpaRepository<TransferObjPO, String>, JpaSpecificationExecutor<TransferObjPO> {

	/**
	 * 查询DTO名称重复行数量
	 * @param name
	 * @param projectId
	 * @return
	 */
	@Query("SELECT count(0) FROM TransferObjPO t WHERE t.name = :name AND t.projectId = :projectId AND t.delFlag = :delFlag")
	public int countTransferObjByName(@Param("name") String name, @Param("projectId") String projectId, @Param("delFlag") String delFlag);
	
	/**
	 * 获取类名
	 * 
	 * @param datasourceId
	 * @return
	 */
	@Query("SELECT t.id as id, t.name as name FROM TransferObjPO t WHERE t.projectId = ?1")
	List<Object[]> findClassNameByProjectId(String projectId);
	
	/**
	 * 通过id查询TranferObj
	 * @param id
	 * @return
	 */
	public TransferObjPO findByIdAndDelFlag(String id, String delFlag);
	
	
	/**
	 * 查询所有TransferObj
	 * @return
	 */
	public List<TransferObjPO> findByProjectIdAndDelFlag(String projectId, String delFlag);
}
