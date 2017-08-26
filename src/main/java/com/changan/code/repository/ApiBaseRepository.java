package com.changan.code.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.changan.code.entity.ApiBasePO;
/**
 * 
 * @author xuyufeng
 *
 */
public interface ApiBaseRepository 
		extends JpaRepository<ApiBasePO, String>, JpaSpecificationExecutor<ApiBasePO>{
		
	/**
	 * 通过版本号查询
	 * @param versionName
	 * @return
	 */
	public ApiBasePO findByVersionName(String versionName);
	
	/**
	 * 查询api表中是否有重复的版本号
	 * @param versionName
	 * @param projectId
	 * @return
	 */
	@Query("SELECT COUNT(t.versionName) FROM ApiBasePO t WHERE t.versionName = :versionName AND  t.projectId = :projectId")
	public int countApiByVersionName(@Param("versionName") String versionName,@Param("projectId") String projectId);
	
	/**
     * 查找出项目最早的有效的api base
     */
	public ApiBasePO findFirstByProjectIdAndDelFlagOrderByCreatedAtASC(String projectId, String delFlag);
}
