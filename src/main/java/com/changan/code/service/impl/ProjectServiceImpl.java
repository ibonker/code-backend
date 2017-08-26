/**
 * 
 */
package com.changan.code.service.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.changan.anywhere.common.mvc.page.rest.request.Filter;
import com.changan.anywhere.common.mvc.page.rest.request.PageDTO;
import com.changan.code.common.Constants;
import com.changan.code.common.PredictUtils;
import com.changan.code.dto.RefObjDTO;
import com.changan.code.dto.SimpleDataObj;
import com.changan.code.entity.DatasourcePO;
import com.changan.code.entity.ProjectPO;
import com.changan.code.exception.CodeCommonException;
import com.changan.code.repository.ProjectRepository;
import com.changan.code.service.IDatasourceService;
import com.changan.code.service.IProjectService;
import com.changan.code.service.ITableService;
import com.changan.code.service.ITransferObjService;
import com.google.common.collect.Lists;

/**
 * 项目Project Service
 * 
 * @author wenxing
 *
 */
@Service
public class ProjectServiceImpl implements IProjectService {

  // 注入project repository
  @Autowired
  private ProjectRepository projectRepo;
  
  // 注入datasource service
  @Autowired
  private IDatasourceService datasourceService;
  
  @Autowired
  private ITransferObjService transObjService;
  
  @Autowired
  private ITableService tableService;

  /**
   * 分页查询project
   * 
   * @return
   */
  @Override
  public Page<ProjectPO> findProjecsPage(PageDTO searchParams) {
    List<Order> orders = Lists.newArrayList();
    // 排序
    if (searchParams.getOrders() != null) {
      for (com.changan.anywhere.common.mvc.page.rest.request.Order order : searchParams
          .getOrders()) {
        orders.add(new Order(Direction.valueOf(order.getOrderType()), order.getFieldName()));
      }
    }
    Sort sort = new Sort(orders);
    // 分页
    PageRequest pagereq = new PageRequest(searchParams.getPageParms().getPageIndex(),
        searchParams.getPageParms().getPageSize(), sort);
    // 使用specification查询
    Specification<ProjectPO> spec = new Specification<ProjectPO>() {
      @Override
      public Predicate toPredicate(Root<ProjectPO> root, CriteriaQuery<?> query,
          CriteriaBuilder cb) {
        List<Predicate> predicates = Lists.newArrayList();
        // 查询参数
        if (null != searchParams.getCollection()) {
          for (Filter filter : searchParams.getCollection().getFilters()) {
            predicates.add(PredictUtils.covertFilterToPredicate(root, cb, filter));
          }
        }
        // 逻辑未删除查询
        predicates.add(cb.equal(root.get("delFlag"), Constants.DATA_IS_NORMAL));
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
      }
    };
    // 获取数据
    return projectRepo.findAll(spec, pagereq);
  }

  /**
   * 更新数据
   */
  @Override
  @Transactional("jpaTransactionManager")
  public ProjectPO updateProject(ProjectPO project) {
    // 当前的project
    ProjectPO updateProject = projectRepo.findOne(project.getId());
    if (null == updateProject) {
      throw new CodeCommonException("未找到项目");
    }
    // 删除之前的数据源
    List<DatasourcePO> datasources = datasourceService.findByProjectId(project.getId());
    if (datasources != null) {
      datasourceService.deleteDatasources(datasources);
    }
    // 更新属性
    updateProject.updateAttrs(project);
    // 更新数据库
    return this.saveProject(updateProject);
  }

  /**
   * 保存数据
   */
  @Override
  @Transactional("jpaTransactionManager")
  public ProjectPO saveProject(ProjectPO project) {
    project = projectRepo.save(project);
    List<DatasourcePO> datasources = project.getDatasources();
    // 存入数据源
    if (datasources != null) {
      for (DatasourcePO datasource : datasources) {
        datasource.setProjectId(project.getId());
      }
      datasourceService.saveDatasources(datasources);
    }
    
    return project;
  }

  /**
   * 获取项目
   */
  @Override
  public ProjectPO getProjectById(String id) {
    // 项目
    ProjectPO project = projectRepo.findOne(id);
    // 项目数据源
    List<DatasourcePO> datasources = datasourceService.findByProjectId(id);
    // 注入datasources
    project.setDatasources(datasources);
    return project;
  }

  @Override
  public RefObjDTO getProjectDTOandPO(String id) {
    List<SimpleDataObj> dtos = transObjService.findClassnameByProjectId(id);
    Map<String, List<SimpleDataObj>> pos = tableService.findClassnameByProjectId(id);
    
    return new RefObjDTO("refobj", "实体", dtos, pos);
  }

}
