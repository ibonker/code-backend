package org.hotpotmaterial.code.repository;

import org.hotpotmaterial.code.entity.FileResourcesPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FileResourcesRepository
		extends JpaRepository<FileResourcesPO, String>, JpaSpecificationExecutor<FileResourcesPO> {
	/**
	 * 根据本地服务的名称查询FileResourcesPO实体
	 * @param uploadname
	 * @return
	 */
	FileResourcesPO findByUploadname(String uploadname);
}