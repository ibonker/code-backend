package com.changan.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.changan.code.entity.ApiParamPO;
/**
 * ApiParam JPA数据库操作
 * @author xuyufeng
 *
 */
public interface ApiParamRepository
    extends JpaRepository<ApiParamPO, String>, JpaSpecificationExecutor<ApiParamPO> {

  /**
   * 通过ApiObj查询参数
   * 
   * @param apiObjId
   * @return
   */
  public List<ApiParamPO> findByApiObjIdAndDelFlag(String apiObjId, String delFlag);

  /**
   * 根据id查询ApiParam
   * 
   * @param id
   * @param delFlag
   * @return
   */
  public ApiParamPO findByIdAndDelFlag(String id, String delFlag);
	
	/**
	 * 根据apiObjId删除数据
	 * @param apiObjId
	 * @return
	 */
	public Long deleteByApiObjId(String apiObjId);
}
