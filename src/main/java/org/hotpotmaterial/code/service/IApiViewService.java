/**
 * 
 */
package org.hotpotmaterial.code.service;

import java.util.List;

import org.hotpotmaterial.code.entity.ApiViewFormConfigPO;
import org.hotpotmaterial.code.entity.ApiViewPO;
import org.hotpotmaterial.code.entity.ApiViewTableConfigPO;
import org.hotpotmaterial.code.entity.TablePO;

/**
 * @author cyj
 *
 */
public interface IApiViewService {
  
  /**
   * 获取页面配置目录列表
   * @param procjectId
   * @return
   */
  public List<ApiViewPO> findApiViewPO(String projectId);
  
  /**
   * 获取各个表对应的Api
   * @param projectId
   * @return
   */
  public List<TablePO> findApiTableList(String projectId);
  
  /**
   * 获取实体类页面表格配置列表
   * @param tableId
   * @return
   */
  public List<ApiViewTableConfigPO> findTableConfigList(String tableId);
  
  /**
   * 获取实体类页面表单配置列表
   * @param tableId
   * @return
   */
  public List<ApiViewFormConfigPO> findFormConfigList(String tableId);
  
  /**
   * 保存配置
   * @param apiViewPO
   * @return
   */
  public ApiViewPO saveApiView(ApiViewPO apiViewPO);
  
  /**
   * 保存整体配置
   * @param apiViewPO
   * @return
   */
  public void saveApiViewConfig(ApiViewPO apiViewPO, List<ApiViewTableConfigPO> tableConfigs, List<ApiViewFormConfigPO> formConfigs);
}
