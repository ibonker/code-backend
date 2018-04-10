package org.hotpotmaterial.code.service;

import java.util.List;

import org.hotpotmaterial.code.entity.ApiParamPO;

/**
 * ApiParam Service接口
 * @author xuyufeng
 *
 */
public interface IApiParamService {


  /**
   * 保存Api方法参数
   * 
   * @param apiParam
   */
  public void saveApiParam(List<ApiParamPO> apiParams, String apiObjId);

  /**
   * 查询所有Api参数
   * 
   * @return
   */
  public List<ApiParamPO> findAllApiParam(String apiObjId);

  /**
   * 根据id执行删除
   * 
   * @param apiObjId
   */
  public void deleteApiParamById(String id);

  /**
   * 批量删除ApiParam
   * 
   * @param apiObjId
   */
  public void deleteAllParam(String apiObjId);
  
  /**
   * 根据apiObjId删除数据
   * 
   * @param apiObjId
   * @return
   */
  public void deleteByApiObjId(String apiObjId);
  
  /**
   * 查询所有Api参数并根据sort排序
   * 
   * @return
   */
  public List<ApiParamPO> findAllApiParamOrderBySort(String apiObjId);
}
