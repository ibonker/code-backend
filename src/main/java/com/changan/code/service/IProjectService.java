/**
 * 
 */
package com.changan.code.service;

import org.springframework.data.domain.Page;

import com.changan.anywhere.common.mvc.page.rest.request.PageDTO;
import com.changan.code.dto.RefObjDTO;
import com.changan.code.entity.ProjectPO;

/**
 * @author wenxing
 *
 */
public interface IProjectService {
  
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
   * 获取dto和po
   * @param id
   * @return
   */
  public RefObjDTO getProjectDTOandPO(String id);

}
