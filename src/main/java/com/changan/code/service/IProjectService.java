/**
 * 
 */
package com.changan.code.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;

import com.changan.anywhere.common.mvc.page.rest.request.PageDTO;
import com.changan.code.dto.ComponentCategory;
import com.changan.code.dto.RefObjDTO;
import com.changan.code.entity.DatasourcePO;
import com.changan.code.entity.ProjectPO;
import com.changan.code.entity.TablePO;

/**
 * @author wenxing
 *
 */
public interface IProjectService extends IComponentService {
  
  /**
   * 分页查询project
   * @return
   */
  public Page<ProjectPO> findProjecsPage(PageDTO searchParams, String usercode);
  
  /**
   * 新建project
   * @param project
   */
  public ProjectPO createProject(ProjectPO project, String token, HttpServletRequest request, String usercode);
  
  /**
   * 更新project
   * @param project
   */
  public ProjectPO updateProject(ProjectPO project, String token, HttpServletRequest request, String usercode);
  
  /**
   * 保存project
   * @param project
   */
  public ProjectPO saveProject(ProjectPO project, String token, HttpServletRequest request);
  
  /**
   * 根据id获取project
   * @param id
   * @return
   */
  public ProjectPO getProjectById(String id, String usercode);
  
  /**
   * 获取dto
   * @param id
   * @return
   */
  public RefObjDTO getProjectDTO(String id);
  
  /**
   * 获取po
   * @param id
   * @return
   */
  public RefObjDTO getProjectPO(String id);
  
  /**
   * 生成代码
   * @param id
   */
  public String generateCodeFiles(String id, String usercode);
  
  /**
   * 安全组件
   * @return
   */
  public List<ComponentCategory> getComponents();
  
  /**
   * 下载文件
   * @param projectName
   * @return
   */
  public File downloadZipFiles(String projectName);
  
  /**
   * 下载前台文件
   * @param projectName
   * @return
   */
  public File downloadZipUIFiles(String projectId, String usercode);
  
  /**
   * 判断是否生成dictType和dictValue表
   * @param projectId
   */
  public String creatNeedTables(ProjectPO project);
  
  /**
   * 根据id删除项目及项目下所有信息
   * @param projectId
   */
  public void deleteProject(String projectId);
  
  /**
   * 生成未压缩的项目文件
   * @param projectId
   */
  public String generateCodeFilesUnzip(TablePO mainTable, ProjectPO project,
      DatasourcePO datasource);
}
