/**
 * 
 */
package org.hotpotmaterial.code.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.hotpotmaterial.code.common.Constants;
import org.hotpotmaterial.code.entity.ApiBasePO;
import org.hotpotmaterial.code.entity.ApiViewFormConfigPO;
import org.hotpotmaterial.code.entity.ApiViewPO;
import org.hotpotmaterial.code.entity.ApiViewTableConfigPO;
import org.hotpotmaterial.code.entity.ColumnPO;
import org.hotpotmaterial.code.entity.DatasourcePO;
import org.hotpotmaterial.code.entity.TablePO;
import org.hotpotmaterial.code.repository.ApiBaseRepository;
import org.hotpotmaterial.code.repository.ApiObjRepository;
import org.hotpotmaterial.code.repository.ApiViewFormConfigRepository;
import org.hotpotmaterial.code.repository.ApiViewRepository;
import org.hotpotmaterial.code.repository.ApiViewTableConfigRepository;
import org.hotpotmaterial.code.repository.TableRepository;
import org.hotpotmaterial.code.service.IApiViewService;
import org.hotpotmaterial.code.service.IDatasourceService;
import org.hotpotmaterial.code.service.ITableService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 项目Project Service
 * 
 * @author wenxing
 *
 */
@Service
public class ApiViewServiceImpl implements IApiViewService {
  
  /**
   * apiViewRepo注入
   */
  @Autowired
  private ApiViewRepository apiViewRepo;
  
  /**
   * tableRepo注入
   */
  @Autowired
  private TableRepository tableRepo;
  
  /**
   * datasourceService注入
   */
  @Autowired
  private IDatasourceService datasourceService;
  
  /**
   * ApiBase Repository注入
   */
  @Autowired
  private ApiBaseRepository apiBaseRepo;
  
  /**
   * apiObjRepo注入
   */
  @Autowired
  private ApiObjRepository apiObjRepo;
  
  /**
   * tableService注入
   */
  @Autowired
  private ITableService tableService;
  
  /**
   * ApiViewTableConfigRepository注入
   */
  @Autowired
  private ApiViewTableConfigRepository tcRepo;
  
  /**
   * ApiViewFormConfigRepository注入
   */
  @Autowired
  private ApiViewFormConfigRepository fcRepo;
  
  /**
   * 获取页面配置目录列表
   */
  @Override
  public List<ApiViewPO> findApiViewPO(String projectId) {
    return apiViewRepo.findByProjectIdAndDelFlag(projectId, Constants.DATA_IS_NORMAL);
  }
  
  /**
   * 获取各个表对应的Api
   */
  @Override
  public List<TablePO> findApiTableList(String projectId) {
    // 结果集
    List<TablePO> tpList = Lists.newArrayList();
    // 项目数据源
    List<DatasourcePO> datasources = datasourceService.findByProjectId(projectId);
    for (DatasourcePO dp : datasources) {
      // 获取已经生成Api的表
      tpList.addAll(tableRepo.findByDatasourceIdAndIsAutoCrudAndDelFlag(dp.getId(), "1", Constants.DATA_IS_NORMAL));
    }
    //获取最新Api
    ApiBasePO apibase = apiBaseRepo.findFirstByProjectIdAndDelFlagOrderByCreatedAtDesc(projectId, Constants.DATA_IS_NORMAL);
    for (TablePO tp : tpList) {
      //获取对应表的Api
      tp.setPathList(apiObjRepo.findByApiBaseIdAndGenBasedTableIdAndDelFlag(apibase.getId(), tp.getId(), Constants.DATA_IS_NORMAL));
    }
    return tpList;
  }
  
  /**
   * 获取实体类Api页面表格配置列表
   */
  @Override
  public List<ApiViewTableConfigPO> findTableConfigList(String tableId) {
    //定义返回结果
    List<ApiViewTableConfigPO> result = Lists.newArrayList();
    //获取最新table对应的字段
    List<ColumnPO> cols = tableService.findMergedColumns(tableId);
    //获取表格配置列表
    List<ApiViewTableConfigPO> tps = tcRepo.findByTableIdAndDelFlag(tableId, Constants.DATA_IS_NORMAL);
    Map<String, ApiViewTableConfigPO> mastermaps = Maps.newHashMap();
    //最新table对应字段与配置进行匹配
    for (ApiViewTableConfigPO mastercolumn : tps) {
      mastermaps.put(mastercolumn.getColumndId(), mastercolumn);
    }
    for (ColumnPO col : cols) {
      ApiViewTableConfigPO apo = mastermaps.get(col.getId());
      //初始化新字段
      if (apo == null) {
        apo = new ApiViewTableConfigPO();
        apo.setColumndId(col.getId());
        apo.setJavaField(col.getJavaField());
        apo.setJavaType(col.getJavaType());
        apo.setTableId(col.getTableId());
        apo.setComments(col.getComments());
        apo.setShowFlag(Constants.IS_INACTIVE);
        apo.setSearchFlag(Constants.IS_INACTIVE);
        apo.setDelFlag(Constants.DATA_IS_NORMAL);
        result.add(apo);
      } else {
        result.add(apo);
        //移除已匹配字段
        mastermaps.remove(col.getId());
      }
    }
    //删除多余字段
    if (mastermaps.size() > 0) {
      for (String columnId : mastermaps.keySet())  {
        tcRepo.delete(mastermaps.get(columnId).getId());
      }
    }
    return result;
  }
  
  /**
   * 获取实体类Api页面表单配置列表
   */
  @Override
  public List<ApiViewFormConfigPO> findFormConfigList(String tableId) {
    //定义返回结果
    List<ApiViewFormConfigPO> result = Lists.newArrayList();
    //获取最新table对应的字段
    List<ColumnPO> cols = tableService.findMergedColumns(tableId);
    //获取表单配置列表
    List<ApiViewFormConfigPO> tps = fcRepo.findByTableIdAndDelFlag(tableId, Constants.DATA_IS_NORMAL);
    Map<String, ApiViewFormConfigPO> mastermaps = Maps.newHashMap();
    //最新table对应字段与配置进行匹配
    for (ApiViewFormConfigPO mastercolumn : tps) {
      mastermaps.put(mastercolumn.getColumndId(), mastercolumn);
    }
    for (ColumnPO col : cols) {
      ApiViewFormConfigPO apo = mastermaps.get(col.getId());
      //初始化新字段
      if (apo == null) {
        apo = new ApiViewFormConfigPO();
        apo.setColumndId(col.getId());
        apo.setJavaField(col.getJavaField());
        apo.setJavaType(col.getJavaType());
        apo.setTableId(col.getTableId());
        apo.setComments(col.getComments());
        apo.setIsNullable(Constants.IS_ACTIVE.equals(col.getIsNullable())? Constants.IS_ACTIVE : Constants.IS_INACTIVE);
        apo.setReadOnly(Constants.IS_ACTIVE.equals(col.getReadOnly())? Constants.IS_ACTIVE : Constants.IS_INACTIVE);
        apo.setIsValidate(Constants.IS_INACTIVE);
        apo.setPattern(col.getPattern());
        apo.setMax(col.getMax());
        apo.setMin(col.getMin());
        apo.setShowFlag(Constants.DATA_IS_NORMAL);
        apo.setDelFlag(Constants.DATA_IS_NORMAL);
        result.add(apo);
      } else {
        result.add(apo);
        //移除已匹配字段
        mastermaps.remove(col.getId());
      }
    }
    //删除多余字段
    if (mastermaps.size() > 0) {
      for (String columnId : mastermaps.keySet())  {
        fcRepo.delete(mastermaps.get(columnId).getId());
      }
    }
    return result;
  }

  @Override
  public ApiViewPO saveApiView(ApiViewPO apiViewPO) {
    return apiViewRepo.save(apiViewPO);
  }

  @Override
  public void saveApiViewConfig(ApiViewPO apiViewPO, List<ApiViewTableConfigPO> tableConfigs,
      List<ApiViewFormConfigPO> formConfigs) {
    apiViewRepo.save(apiViewPO);
    tcRepo.save(tableConfigs);
    fcRepo.save(formConfigs);
  }


}
