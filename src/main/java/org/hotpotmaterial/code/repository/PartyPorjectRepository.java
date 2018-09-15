package org.hotpotmaterial.code.repository;

import java.util.List;

import org.hotpotmaterial.code.entity.PartyProjectPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface PartyPorjectRepository extends JpaRepository<PartyProjectPO, String>, JpaSpecificationExecutor<PartyProjectPO> {
	/**
	 * 根据用户ID与组织ID对应的所有项目ID
	 * @param userId 用户ID
	 * @param organizeId 组织ID
	 * @return
	 */
	@Query("SELECT DISTINCT new PartyProjectPO(pp.projectId) FROM PartyProjectPO pp WHERE pp.partyId = ?1 OR pp.partyId = ?2 ")
	List<PartyProjectPO> findByUserIdAndOrganizeId(String userId,String organizeId);
	/**
	 * 根据用户ID与组织ID通过条件查询对应的项目
	 * @param userId
	 * @param organizeId
	 * @param name  项目名查询条件
	 * @param description  项目描述查询条件
	 * @return
	 *//*
	@Query("SELECT p FROM ProjectPO p WHERE p.id IN ( SELECT DISTINCT pp.projectId FROM PartyProjectPO pp WHERE pp.partyId = ?1 OR pp.partyId = ?2 ) and p.name like '%:name%' and p.description like '%:description%'")
	List<ProjectPO> findByUserIdAndOrganizeId(String userId,String organizeId,Object name,Object description);*/
	
	 /**
	  * 根据项目ID查询对应的实体PartyProjectPO
	  * @param projectId
	  * @param style
	  * @return
	  */
	@Query("SELECT pp.partyId,pp.flag FROM PartyProjectPO pp WHERE pp.projectId = ?1 AND pp.style = ?2 ")
	 List<Object[]> findByProjectIdAndStyle(String projectId, String style);
	

}
