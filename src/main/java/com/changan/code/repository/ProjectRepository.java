package com.changan.code.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.changan.code.entity.ProjectPO;

/**
 * @author wenxing
 *
 */
public interface ProjectRepository
    extends JpaRepository<ProjectPO, String>, JpaSpecificationExecutor<ProjectPO> {
  
  /**
   * 根据项目id删除项目
   * @param projectId
   * @param delFlag
   */
  @Transactional
  @Modifying
  @Query(value = "UPDATE ProjectPO p SET p.delFlag = :delFlag WHERE p.id = :id")
  void updateByProjectId(@Param("id")String id, @Param("delFlag")String delFlag);

  /**
   * 根据id查询项目
   * @param projectId
   * @param delFlag
   */
  ProjectPO findByIdAndDelFlag(String id, String delFlag);
}
