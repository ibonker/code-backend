package com.changan.code.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.changan.code.entity.ApiObjPO;

public interface ApiObjRepository 
		extends JpaRepository<ApiObjPO, String>, JpaSpecificationExecutor<ApiObjPO>{
	
	/**
	 * 查询apiObj是否存在重复的uri
	 * @param apiBaseId
	 * @param uri
	 * @return
	 */
	@Query("SELECT COUNT(0) FROM ApiObjPO t WHERE t.apiBaseId = :apiBaseId AND t.uri = :uri")
	public int countApiObjByUri(@Param("apiBaseId") String apiBaseId, @Param("uri") String uri);
	
	/**
	 * 根据ApiBaseId查询 ApiObj
	 * @return
	 */
	public List<ApiObjPO> findByApiBaseId(String apiBaseId);
}
