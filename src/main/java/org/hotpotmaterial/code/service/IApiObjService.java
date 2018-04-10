package org.hotpotmaterial.code.service;

import java.util.List;

import org.hotpotmaterial.code.entity.ApiObjPO;
import org.hotpotmaterial.code.entity.TableRelationPO;

/**
 * ApiObj Service接口
 * 
 * @author xuyufeng
 *
 */
public interface IApiObjService {

  /**
   * 保存ApiObj
   * 
   * @param apiObj
   */
  public ApiObjPO saveApiObj(ApiObjPO apiObj);

  /**
   * 更新ApiObj
   * 
   * @param apiObj
   */
  public ApiObjPO updateApiObj(ApiObjPO apiObj);

  /**
   * 查询所有ApiObj
   * 
   * @return
   */
  public List<ApiObjPO> findAllApiObj(String apiBaseId);
  
  /**
   * 查询所有ApiObj
   * 
   * @return
   */
  public List<ApiObjPO> findAllApiObjByTableId(String tableId);

  /**
   * 根据id查询ApiObj
   * 
   * @param id
   * @return
   */
  public ApiObjPO findOneApiObj(String id);

  /**
   * 根据id删除ApiObj
   * 
   * @param id
   */
  public void deleteApiObj(String id);

  /**
   * 自动创建api
   * 
   * @param apiBaseId
   * @param tableName app_info
   * @param dtoName ddemo.test.ResultOfAppInfoDTO
   * @param className ddemo.test.AppInfoPO
   * @param datasourcePName ddemo_test
   * @param dbname ddemo_test
   * @param dbcount 1
   */
  public void createAutoCrudApi(String apiBaseId, String tableId, String tableName,
      String tableComment, String dtoName, String className, String dbname, Long dbcount,
      List<String> relations);

  /**
   * 根据tableId删除api
   * 
   * @param tableId
   */
  public void deleteAutoCrudApi(String tableId);
  
  /**
   * 根据genBasedTableId列表删除数据
   * 
   * @param genBasedTableId
   * @return
   */
  public Long deleteByGenBasedTableIdIn(List<String> genBasedTableIds);
  
  /**
   * 根据tableId删除关联关系crud api
   * 
   * @param tableId
   */
  public void deleteRelationCrudApi(String masterTableId, String slaveTableId);

  /**
   * 创建主从表crud api
   * 
   * @param apiBaseId
   * @param tableRelation
   * @param dbname
   */
  public void createAutoCruApiForRelation(String apiBaseId, TableRelationPO tableRelation,
      String dtoName, String dbname, Long dbcount);
}
