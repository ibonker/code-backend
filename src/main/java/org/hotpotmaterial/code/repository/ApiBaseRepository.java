package org.hotpotmaterial.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.hotpotmaterial.code.entity.ApiBasePO;

/**
 * 
 * @author xuyufeng
 *
 */
public interface ApiBaseRepository
    extends JpaRepository<ApiBasePO, String>, JpaSpecificationExecutor<ApiBasePO> {

  /**
   * 查询api表中是否有重复的版本号
   * 
   * @param versionName
   * @param projectId
   * @return
   */
  public int countByVersionNameAndProjectIdAndDelFlag(String versionName, String projectId,
      String delFlag);

  /**
   * 查询指定项目下所有的Api
   * 
   * @param projectId
   * @return
   */
  public List<ApiBasePO> findByProjectIdAndDelFlagOrderByVersionName(String projectId, String delFlag);

  /**
   * 根据id查询Api
   * 
   * @param id
   * @param delFlag
   * @return
   */
  public ApiBasePO findByIdAndDelFlag(String id, String delFlag);

  /**
   * 查找出项目最早的有效的api base
   */
  public ApiBasePO findFirstByProjectIdAndDelFlagOrderByCreatedAtDesc(String projectId,
      String delFlag);

  /**
   * 根据versionName、projectId查询Api
   * 
   * @param versionName
   * @param projectId
   * @param delFlag
   * @return
   */
  public List<ApiBasePO> findByVersionNameAndProjectIdAndDelFlag(String versionName,
      String projectId, String delFlag);
}
