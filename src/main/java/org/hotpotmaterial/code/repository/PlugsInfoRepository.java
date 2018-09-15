package org.hotpotmaterial.code.repository;

import org.hotpotmaterial.code.entity.PlugsInfoPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PlugsInfoRepository extends JpaRepository<PlugsInfoPO, String>, JpaSpecificationExecutor<PlugsInfoPO> {
}