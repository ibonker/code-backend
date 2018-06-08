/**
 * 
 */
package org.hotpotmaterial.code.service.impl;

import org.hotpotmaterial.code.dto.ResultOfPreviewDTO;
import org.hotpotmaterial.code.entity.DatasourcePO;
import org.hotpotmaterial.code.entity.ProjectPO;
import org.hotpotmaterial.code.entity.TablePO;
import org.hotpotmaterial.code.repository.ProjectRepository;
import org.hotpotmaterial.code.repository.TableRepository;
import org.hotpotmaterial.code.service.IDatasourceService;
import org.hotpotmaterial.code.service.IPreviewService;
import org.hotpotmaterial.code.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wenxing
 *
 */
@Service
public class PreviewServiceImpl implements IPreviewService {
  
  @Autowired
  private TableRepository tableRepository;
  
  @Autowired
  private IProjectService projectService;

  @Autowired
  private IDatasourceService datasourceService;
  
  @Autowired
  private ProjectRepository projectRepo;

  /**
   * 预览代码
   */
  public ResultOfPreviewDTO preview(String tableId) {
    // 通过tableId 获取 table对象
    TablePO table = tableRepository.findOne(tableId);
    // 获得数据源
    DatasourcePO datasource = datasourceService.findById(table.getDatasourceId());
    // 获取项目
    ProjectPO project = projectRepo.findOne(datasource.getProjectId());
    // 生成未压缩的项目文件并返回下载url
    return projectService.generateCodeFilesPreview(table, project, datasource);
  }
}
