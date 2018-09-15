package org.hotpotmaterial.code.repository;

import org.hotpotmaterial.code.entity.ContextPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContextRepository extends JpaRepository<ContextPO, String>, JpaSpecificationExecutor<ContextPO> {
	/**
	 * 根据目录ID找到对应的文档
	 * @param directoryId
	 * @param string
	 * @return
	 */
	ContextPO findByDirectoryIdAndDelFlag(String directoryId, String string);
	
	

}
