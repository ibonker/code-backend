package org.hotpotmaterial.code.repository;

import java.util.List;

import org.hotpotmaterial.code.entity.LoginInfoPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LoginInfoRepository extends JpaRepository<LoginInfoPO, String>, JpaSpecificationExecutor<LoginInfoPO> {
	/**
	 * 根据用户id查询登陆记录
	 * @param id
	 * @return
	 */
	List<LoginInfoPO> findByUserId(String userId);

}
