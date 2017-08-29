/**
 * 
 */
package com.changan.code.service;

import java.util.List;
import java.util.Map;

import com.changan.code.dto.Template;
import com.changan.code.entity.DatasourcePO;
import com.changan.code.entity.ProjectPO;

/**
 * @author wenxing
 *
 */
public interface IGenerateService {

  /**
   * 
   * @param tpl
   * @param model
   * @param isReplaceFile
   * @param subProjectName
   * @return
   */
  public String generateToFile(Template tpl, Map<String, Object> model, boolean isReplaceFile);
  
  /**
   * 生成配置文件
   * @param datasources
   * @return
   */
  public void generateConfigFiles(ProjectPO project, List<DatasourcePO> datasources);

}
