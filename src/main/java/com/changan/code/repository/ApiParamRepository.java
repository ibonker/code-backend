package com.changan.code.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.changan.code.entity.ApiParamPO;

public interface ApiParamRepository 
		extends JpaRepository<ApiParamPO, String>, JpaSpecificationExecutor<ApiParamPO>{

	/**
	 * 查询Api参数是否重复
	 * @param name
	 * @param apiObjId
	 * @return
	 */
	@Query("SELECT COUNT(t.name) FROM ApiParamPO t WHERE t.name = :name AND t.apiObjId = :apiObjId")
	public int countApiParamByName(@Param("name") String name, @Param("apiObjId") String apiObjId);
	
	/**
	 * 通过ApiObj查询参数
	 * @param apiObjId
	 * @return
	 */
	public List<ApiParamPO> findByApiObjId(String apiObjId);
}
