package org.hotpotmaterial.code.repository;

import java.util.List;

import org.hotpotmaterial.code.entity.DirectoryPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface DirectoryRepository extends JpaRepository<DirectoryPO, String>, JpaSpecificationExecutor<DirectoryPO> {
	
	/**
	 * 获取所有根目录
	 * @param pid
	 * @param delFlag
	 * @return
	 */
	@Query("select d from DirectoryPO d where (d.pid = '0' or d.pid is null) and d.delFlag = '0'")
	List<DirectoryPO> findByPidIsNullOrPidAndDelFlag();
	
	/**
	 * 根据目录ID查询它的子目录
	 * @param directoryId
	 * @return
	 */
	@Query("select c from DirectoryPO c where c.pid = ?1 and c.delFlag = '0'")
	List<DirectoryPO> selectDirectorysById(String directoryId);
	
}
