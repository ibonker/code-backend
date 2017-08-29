/**
 * 
 */
package com.changan.code.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.changan.code.common.BaseDTO;
import com.changan.code.common.Constants;
import com.changan.code.common.DtoType;
import com.changan.code.common.PredictUtils;
import com.changan.code.common.component.Consul;
import com.changan.code.common.component.Security;
import com.changan.code.dto.Component;
import com.changan.code.dto.ComponentCategory;
import com.changan.code.dto.PackObj;
import com.changan.code.dto.RefObjDTO;
import com.changan.code.dto.SimpleDataObj;
import com.changan.code.entity.DatasourcePO;
import com.changan.code.entity.ProjectPO;
import com.changan.code.exception.CodeCommonException;
import com.changan.code.repository.ProjectRepository;
import com.changan.code.service.IDatasourceService;
import com.changan.code.service.IGenerateService;
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

  @Autowired
  private IGenerateService generateService;

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
    // 获取dto
    Map<String, List<SimpleDataObj>> dtos = transObjService.findClassnameByProjectId(id);
    // 获取po
    Map<String, List<SimpleDataObj>> pos = tableService.findClassnameByProjectId(id);
    // dto根据package分类列表
    List<PackObj> dtoPacks = Lists.newArrayList();
    // po根据package分类列表
    List<PackObj> poPacks = Lists.newArrayList();
    // 添加默认类型
    List<SimpleDataObj> defaultdtos = Lists.newArrayList();
    defaultdtos.add(new SimpleDataObj("", BaseDTO.ResultDTO.toString(), Constants.IS_INACTIVE));
    defaultdtos.add(new SimpleDataObj("", BaseDTO.PageDTO.toString(), Constants.IS_INACTIVE));
    defaultdtos.add(new SimpleDataObj("", BaseDTO.ResultPageDTO.toString(), Constants.IS_ACTIVE));
    defaultdtos.add(new SimpleDataObj("", BaseDTO.JsonSchema.toString(), Constants.IS_INACTIVE));
    PackObj defaultPackobj = new PackObj("default", defaultdtos);
    dtoPacks.add(defaultPackobj);

    // dto类型
    for (Entry<String, List<SimpleDataObj>> entry : dtos.entrySet()) {
      dtoPacks.add(new PackObj(entry.getKey(), entry.getValue()));
    }
    // po类型
    for (Entry<String, List<SimpleDataObj>> entry : pos.entrySet()) {
      poPacks.add(new PackObj(entry.getKey(), entry.getValue()));
    }

    return new RefObjDTO(DtoType.REFOBJ.toString().toLowerCase(), DtoType.REFOBJ.getCname(),
        dtoPacks, poPacks);
  }

  @Override
  public void generateCodeFiles(String id) {
    // 获得project
    ProjectPO project = this.getProjectById(id);
    // 获得数据源
    List<DatasourcePO> datasources = datasourceService.findByProjectId(id);
    // 生成配置文件代码
    generateService.generateConfigFiles(project, datasources);
  }

  @Override
  public List<ComponentCategory> getComponents() {
    List<ComponentCategory> categories = Lists.newArrayList();
    // security组件
    categories.add(this.getComponentCategory(() -> Security.isMultiSelect(),
        () -> Security.getTypeCname(), (List<Component> components) -> {
          for (Security security : Security.values()) {
            components.add(new Component(security.name(), security.getCname()));
          }
        }));
    // consul组件
    categories.add(this.getComponentCategory(() -> Security.isMultiSelect(),
        () -> Consul.getTypeCname(), (List<Component> components) -> {
          for (Consul consul : Consul.values()) {
            components.add(new Component(consul.name(), consul.getCname()));
          }
        }));
    return categories;
  }

}
