package com.changan.code.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.changan.anywhere.common.mvc.page.rest.request.PageDTO;
import com.changan.code.dto.ResultDictDTO;
import com.changan.code.entity.DatasourcePO;
import com.changan.code.entity.DictTypePO;
import com.changan.code.entity.DictValuePO;

public interface IDictService {

  /**
   * 查询DictType 和 DictValue
   * 
   * @param version
   * @return
   */
  public ResultDictDTO findAll(int version);

  /**
   * 更新DictType
   * 
   * @param dictTypePO
   */

  public DictTypePO updateDictType(String dictTypeId, DictTypePO dictTypePO);
  /**
   * 更新DictValue
   * 
   * @param dictValuePO
   */
  public DictValuePO updateDictValue(String dictValueId, DictValuePO dictValue);
  
  /**
   * 根据id删除DictType
   * @param id
   */
  public void deleteDictType(String id);
  
  /**
   * 根据id删除DictValue
   * @param id
   */
  public void deleteDictValue(String id);
  
  /**
   * 新增DictType
   * @param dictTypePO
   */
  public DictTypePO insertDictType(DictTypePO dictType);
  
  /**
   * 新增DictValue
   */
  public DictValuePO insertDictValue(String code, DictValuePO dictValue);
  
  /**
   * 根据id查询DictType
   * @param id
   */
  public DictTypePO findDictType(String id);
  
  /**
   * 根据id查询DictValue
   * @param id
   */
  public DictValuePO findDictValue(String id);
  
  /**
   * 分页查询dictType
   * @return
   */
  public Page<DictTypePO> findDictTypePage(PageDTO searchParams);
  
  /**
   * 分页查询dictValue
   * @return
   */
  public Page<DictValuePO> findDictValuePage(String code, PageDTO searchParams);
  
  /**
   * 批量保存dictValues
   */
  public void saveDictValues(DatasourcePO datasource, DictTypePO dictType, List<DictValuePO> dictValues);
  
  /**
   * 查询所有dictType
   * @return
   */
  public List<DictTypePO> findDictTypes(DatasourcePO datasource);
  
  /**
   * 查询指定Type下的value
   * @return
   */
  public List<DictValuePO> findTypeAndValue(DatasourcePO datasource, String code);
  
  /**
   * 新增DictType
   * @param datasource
   * @param dictType
   */
  public void insertDictType(DatasourcePO datasource, DictTypePO dictType);
}
