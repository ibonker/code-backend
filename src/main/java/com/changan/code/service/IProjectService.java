/**
 * 
 */
package com.changan.code.service;

import java.io.File;
import java.util.List;

import org.springframework.data.domain.Page;

import com.changan.anywhere.common.mvc.page.rest.request.PageDTO;
import com.changan.code.dto.ComponentCategory;
import com.changan.code.dto.RefObjDTO;
import com.changan.code.entity.ProjectPO;

/**
 * @author wenxing
 *
 */
public interface IProjectService extends IComponentService {
  
  /**
   * 分页查询project
   * @return
   */
  public Page<ProjectPO> findProjecsPage(PageDTO searchParams);
  
  /**
   * 更新project
   * @param project
   */
  public ProjectPO updateProject(ProjectPO project);
  
  /**
   * 保存project
   * @param project
   */
  public ProjectPO saveProject(ProjectPO project);
  
  /**
   * 根据id获取project
   * @param id
   * @return
   */
  public ProjectPO getProjectById(String id);
  
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
  public String generateCodeFiles(String id);
  
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
  public File downloadZipUIFiles(String projectName);
  
  /**
   * 判断是否生成dictType和dictValue表
   * @param projectId
   */
  public String creatDictTable(String projectId);
}
