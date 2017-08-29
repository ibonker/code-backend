package com.changan.code.service;

import java.util.List;

import com.changan.code.entity.ApiBasePO;

/**
 * API 接口
 * 
 * @author xuyufeng
 *
 */
public interface IApiBaseService {

  /**
   * 保存API
   * 
   * @param apiBase
   */
  public ApiBasePO saveApiBase(ApiBasePO apiBase);

  /**
   * 更新API
   * 
   * @param apiBase
   */
  public ApiBasePO updateApiBase(ApiBasePO apiBase);

  /**
   * 查询所有的API
   * 
   * @return
   */
  public List<ApiBasePO> findAllApiBase(String projectId);

  /**
   * 删除Api
   * 
   * @param id
   */
  public void deleteApiBaseById(String id);


  /**
   * 查找出项目最早的有效的api base
   */
  public ApiBasePO findEarlestApiBaseByProjectId(String projectId);

  /**
   * 创建默认api base
   * 
   * @param projectId
   * @return
   */
  public ApiBasePO createDefaultApiBase(String projectId, String packageName);

  /**
   * 根据id查询ApiBase
   * 
   * @param id
   * @return
   */
  public ApiBasePO findApiBase(String id);

}
