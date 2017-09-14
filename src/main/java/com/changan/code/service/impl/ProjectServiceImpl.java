/**
 * 
 */
package com.changan.code.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
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
import com.changan.anywhere.common.utils.FileUtil;
import com.changan.anywhere.common.utils.FileUtils;
import com.changan.code.common.BaseDTO;
import com.changan.code.common.Constants;
import com.changan.code.common.BaseType;
import com.changan.code.common.PredictUtils;
import com.changan.code.common.component.Consul;
import com.changan.code.common.component.Security;
import com.changan.code.config.property.GenerateProperties;
import com.changan.code.dto.Component;
import com.changan.code.dto.ComponentCategory;
import com.changan.code.dto.PackObj;
import com.changan.code.dto.RefObjDTO;
import com.changan.code.dto.SimpleDataObj;
import com.changan.code.entity.ApiBasePO;
import com.changan.code.entity.ApiObjPO;
import com.changan.code.entity.ApiParamPO;
import com.changan.code.entity.ColumnPO;
import com.changan.code.entity.DatasourcePO;
import com.changan.code.entity.ProjectPO;
import com.changan.code.entity.TablePO;
import com.changan.code.entity.TransferObjFieldPO;
import com.changan.code.entity.TransferObjPO;
import com.changan.code.exception.CodeCommonException;
import com.changan.code.repository.ProjectRepository;
import com.changan.code.repository.TableRepository;
import com.changan.code.service.IApiBaseService;
import com.changan.code.service.IApiObjService;
import com.changan.code.service.IApiParamService;
import com.changan.code.service.IDatasourceService;
import com.changan.code.service.IGenerateService;
import com.changan.code.service.IProjectService;
import com.changan.code.service.ITableService;
import com.changan.code.service.ITransferObjFieldService;
import com.changan.code.service.ITransferObjService;
import com.changan.code.utils.GeneratorUtils;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 项目Project Service
 * 
 * @author wenxing
 *
 */
@Service
public class ProjectServiceImpl implements IProjectService {

  @Autowired
  private GenerateProperties genProperties;

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

  @Autowired
  private ITransferObjFieldService transferObjFieldService;

  @Autowired
  private IApiBaseService apiBaseService;

  @Autowired
  private IApiObjService apiObjService;

  @Autowired
  private IApiParamService apiParamService;

  @Autowired
  private TableRepository tableRepo;

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
    // 旧数据源
    List<DatasourcePO> datasources = datasourceService.findByProjectId(project.getId());
    // 需要删除的数据源
    List<DatasourcePO> delDbs = Lists.newArrayList();
    // 需要新增的数据源
    List<DatasourcePO> newDbs = Lists.newArrayList();
    if (datasources != null) {
      // 旧数据源map
      Map<String, DatasourcePO> oldDbMap = Maps.newHashMap();
      for (DatasourcePO oldDb : datasources) {
        oldDbMap.put(oldDb.getId(), oldDb);
      }
      for (DatasourcePO newDb : project.getDatasources()) {
        if (StringUtils.isNotBlank(newDb.getId())) {
          oldDbMap.remove(newDb.getId());
        } else {
          newDbs.add(newDb);
        }
      }
      for (Entry<String, DatasourcePO> entry : oldDbMap.entrySet()) {
        delDbs.add(entry.getValue());
      }
      // 删除数据源
      datasourceService.deleteDatasources(delDbs);
    }
    // 添加新增的数据源
    project.setDatasources(newDbs);
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
    List<DatasourcePO> datasources = project.getDatasources();
    project = projectRepo.save(project);
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
    if (null == project) {
      throw new CodeCommonException("数据库查无此数据");
    }
    // 项目数据源
    List<DatasourcePO> datasources = datasourceService.findByProjectId(id);
    // 注入datasources
    project.setDatasources(datasources);
    // 组合项目组件
    Map<String, Object> componentMap = Maps.newHashMap();
    List<ComponentCategory> list = getComponents();
    for (ComponentCategory co : list) {
      List<String> codes = co.getComponents().stream()
          .filter(cop -> project.getComponents() != null
              && project.getComponents().indexOf(cop.getCode()) > -1)
          .map(Component::getCode).collect(Collectors.toList());
      if (co.getIsMultiSelect().equals(Constants.DATA_IS_INVALID)) {
        componentMap.put(co.getCategory(), codes);
      } else {
        componentMap.put(co.getCategory(), codes.size() == 1 ? codes.get(0) : null);
      }
    }
    project.setComponentsMap(componentMap);
    return project;
  }

  @Override
  public RefObjDTO getProjectDTO(String id) {
    // 获取dto
    Map<String, List<SimpleDataObj>> dtos = transObjService.findClassnameByProjectId(id);
    // dto根据package分类列表
    List<PackObj> dtoPacks = Lists.newArrayList();
    // 添加默认类型
    List<SimpleDataObj> defaultdtos = Lists.newArrayList();
    for (BaseDTO basedto : BaseDTO.values()) {
      defaultdtos.add(new SimpleDataObj("", basedto.toString(),
          basedto.equals(BaseDTO.ResultPageDTO) ? Constants.IS_ACTIVE : Constants.IS_INACTIVE,
          null));
    }
    PackObj defaultPackobj = new PackObj("default", defaultdtos);
    dtoPacks.add(defaultPackobj);

    // 自定义dto类型
    for (Entry<String, List<SimpleDataObj>> entry : dtos.entrySet()) {
      dtoPacks.add(new PackObj(entry.getKey(), entry.getValue()));
    }

    return new RefObjDTO(BaseType.DTO.toString().toLowerCase(), BaseType.DTO.getCname(), dtoPacks);
  }

  @Override
  public RefObjDTO getProjectPO(String id) {
    // 获取po
    Map<String, List<SimpleDataObj>> pos = tableService.findClassnameByProjectId(id);
    // po根据package分类列表
    List<PackObj> poPacks = Lists.newArrayList();
    // po类型
    for (Entry<String, List<SimpleDataObj>> entry : pos.entrySet()) {
      poPacks.add(new PackObj(entry.getKey(), entry.getValue()));
    }

    return new RefObjDTO(BaseType.PO.toString().toLowerCase(), BaseType.PO.getCname(), poPacks);
  }

  /**
   * 生成项目代码
   */
  @Override
  public String generateCodeFiles(String id) {
    // 获得project
    ProjectPO project = this.getProjectById(id);
    // 获得数据源
    List<DatasourcePO> datasources = datasourceService.findByProjectId(id);
    // 获取表map
    Map<String, TablePO> tablemap = Maps.newHashMap();
    // 获取最新的apibase
    ApiBasePO firstApibase = apiBaseService.findEarlestApiBaseByProjectId(id);
    // 生成配置文件代码
    generateService.generateConfigFiles(project, datasources, firstApibase.getBasePath());
    // 查询对应项目下的DTO信息
    List<TransferObjPO> transferObjs = transObjService.findAllTransferObj(id);
    for (TransferObjPO transferObj : transferObjs) {
      // 查询DTO下对应DTO属性
      List<TransferObjFieldPO> transferObjFileds =
          transferObjFieldService.findAllTransferObjField(transferObj.getId());
      // 生成DTO文件代码
      generateService.generateDTOFiles(project.getName(), project.getPackages().toLowerCase(),
          transferObj, transferObjFileds);
    }

    //生成advice代码
    generateService.generateAdvice(project.getPackages(), project.getName());
    
    // 获取需要自动生成crud的table
    boolean isAutoGen = false;
    for (DatasourcePO datasource : datasources) {
      // 查询对应数据源下的所有表
      List<TablePO> tables = tableRepo.findByDatasourceIdAndIsAutoCrudAndDelFlag(datasource.getId(),
          Constants.IS_ACTIVE, Constants.DATA_IS_NORMAL);
      for (TablePO table : tables) {
        table.setPackageName(datasource.getPackageName());
        tablemap.put(table.getId(), table);
        // 生成servcie和serviceImpl
        generateService.generateIServiceAndServiceImpl(datasource.getPackageName(),
            project.getName(), project.getPackages(), table, transferObjs.get(0).getPackageName());
        // TODO 判断mybatis和jpa 生成JPAService和JPAServiceImpl
        // generateService.generateJPAServiceAndJPAServiceImpl(datasource.getPackageName(),
        // project.getName(), project.getPackages(), table, transferObjs.get(0).getPackageName());
      }
      // 数据库代码生成xml
      if (!tables.isEmpty()) {
        generateService.generateGeneratorConfigFiles(project.getName(),
            project.getPackages().toLowerCase(), datasource, tables);
        isAutoGen = true;
      }

    }
    // 生成mybatis代码
    if (isAutoGen) {
      generateService.generateMybatisFiles(project.getName());
    }

    // 获取实体表名
    for (DatasourcePO datasource : datasources) {
      // 查询对应数据源下的所有表
      List<TablePO> tables = tableService.findByDatasourceId(datasource.getId());
      for (TablePO table : tables) {
        List<ColumnPO> columns = tableService.findMergedColumns(table.getId());
        // 生成实体文件代码
        generateService.generateEntityFiles(datasource.getPackageName(), project.getName(),
            project.getPackages().toLowerCase(), table, columns);
        // TODO 判断mybatis和jpa 生成Jpa Repository
//        generateService.generateRepository(datasource.getPackageName(), project.getName(),
//            project.getPackages().toLowerCase(), table.getName());
      }
    }
    // 获取apibase
    List<ApiBasePO> apibases = apiBaseService.findAllApiBase(project.getId());
    for (ApiBasePO apibase : apibases) {
      // 获取api obj
      List<ApiObjPO> apiobjs = apiObjService.findAllApiObj(apibase.getId());
      Map<String, List<ApiObjPO>> controllerMap = Maps.newHashMap();
      for (ApiObjPO apiobj : apiobjs) {
        // 获取api param
        List<ApiParamPO> apiparams = apiParamService.findAllApiParam(apiobj.getId());
        apiobj.setApiParams(apiparams);
        // 添加包名
        if (Constants.IS_ACTIVE.equals(apiobj.getIsAutoGen())) {
          String tableName = tablemap.get(apiobj.getGenBasedTableId()).getName();
          apiobj.setServiceName(tablemap.get(apiobj.getGenBasedTableId()).getPackageName()
              .concat(".I")
              .concat(
                  CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase()))
              .concat("Service"));
          apiobj.setRelateTableName(tableName);
        }
        // controller分类
        List<ApiObjPO> apilist = controllerMap.get(apiobj.getControllerName());
        if (null == apilist) {
          apilist = Lists.newArrayList();
          apilist.add(apiobj);
          controllerMap.put(apiobj.getControllerName(), apilist);
        } else {
          apilist.add(apiobj);
        }
      }

      // 循环controller map生成controller文件
      for (Entry<String, List<ApiObjPO>> entry : controllerMap.entrySet()) {
        generateService.generateControllerFiles(project, apibase, entry.getKey(), entry.getValue());
      }
    }

    // 打包代码
    this.zipcode(project.getName());

    return "/codegen/api/v1/projects/" + project.getName() + "/download";
  }

  /**
   * 打包项目代码
   * 
   * @param projectName
   */
  private void zipcode(String projectName) {
    // 压缩生成的项目
    String projectPath = GeneratorUtils.getProjectPath(genProperties.getProjectPath(), projectName);
    String projectZipPath =
        GeneratorUtils.getProjectZipPath(genProperties.getProjectZipPath(), projectName);
    FileUtils.zipFiles(projectPath, "*", projectZipPath);
    // 删除生成的未压缩文件及文件夹
    FileUtil.delete(new File(projectPath));
  }

  /**
   * 下载文件
   */
  @Override
  public File downloadZipFiles(String projectName) {
    String projectZipPath =
        GeneratorUtils.getProjectZipPath(genProperties.getProjectZipPath(), projectName);

    return new File(projectZipPath);
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
    categories.add(this.getComponentCategory(() -> Consul.isMultiSelect(),
        () -> Consul.getTypeCname(), (List<Component> components) -> {
          for (Consul consul : Consul.values()) {
            components.add(new Component(consul.name(), consul.getCname()));
          }
        }));
    return categories;
  }

}
