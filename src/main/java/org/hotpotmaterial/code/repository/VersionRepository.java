package org.hotpotmaterial.code.repository;

import java.util.List;

import org.hotpotmaterial.code.entity.VersionPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VersionRepository extends JpaRepository<VersionPO, String>, JpaSpecificationExecutor<VersionPO> {
	/**
	 * 根据flag查询对应的版本信息
	 * @param flag
	 * @return
	 */
	List<VersionPO> findByFlag(String flag);

}
