/**
 * 
 */
package com.changan.code.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
import com.changan.code.common.BaseType;
import com.changan.code.common.Constants;
import com.changan.code.common.PredictUtils;
import com.changan.code.common.component.Consul;
import com.changan.code.common.component.Dictionary;
import com.changan.code.common.component.Security;
import com.changan.code.config.property.GenerateProperties;
import com.changan.code.dto.Component;
import com.changan.code.dto.ComponentCategory;
import com.changan.code.dto.PackObj;
import com.changan.code.dto.RefObjDTO;
import com.changan.code.dto.SeniorDtoAttribute;
import com.changan.code.dto.SeniorDtoRelation;
import com.changan.code.dto.SimpleDataObj;
import com.changan.code.entity.ApiBasePO;
import com.changan.code.entity.ApiObjPO;
import com.changan.code.entity.ApiParamPO;
import com.changan.code.entity.ColumnPO;
import com.changan.code.entity.DatasourcePO;
import com.changan.code.entity.ProjectPO;
import com.changan.code.entity.TablePO;
import com.changan.code.entity.TableRelationPO;
import com.changan.code.entity.TableSeniorRelationPO;
import com.changan.code.entity.TableSeniorSlavePO;
import com.changan.code.entity.TransferObjFieldPO;
import com.changan.code.entity.TransferObjPO;
import com.changan.code.exception.CodeCommonException;
import com.changan.code.repository.ProjectRepository;
import com.changan.code.repository.TableSeniorColumnRepository;
import com.changan.code.repository.TableSeniorRelationRepository;
import com.changan.code.repository.TableSeniorSlaveRepository;
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
import com.changan.code.utils.PatternUtils;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

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
  private TableSeniorSlaveRepository tableSeniorSlaveRepository;

  @Autowired
  private TableSeniorRelationRepository seniorRelationRepository;

  @Autowired
  private TableSeniorColumnRepository tableSeniorColumnRepository;

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

      List<String> delDbIds = Lists.newArrayList();
      for (DatasourcePO db : delDbs) {
        delDbIds.add(db.getId());
      }
      if (!delDbIds.isEmpty()) {
        // 删除表和实体
        // 获取应该删除的表的id
        List<String> delTableIds = tableService.findIdByDatasourceIdIn(delDbIds);
        if (!delTableIds.isEmpty()) {
          // 获取应该删除的dto的id
          List<String> delDtoIds = transObjService.findIdByGenBasedTableIdIn(delTableIds);
          // 删除dto的field
          transferObjFieldService.deleteByTransferObjIdIn(delDtoIds);
          // 删除dto
          transObjService.deleteByGenBasedTableIdIn(delTableIds);
        }
        // 删除表
        tableService.deleteByDatasourceIdIn(delDbIds);
        // 删除数据源
        datasourceService.deleteDatasources(delDbs);
      }
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
              && compareStr(project.getComponents(), cop.getCode()))
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
      defaultdtos.add(new SimpleDataObj(basedto.name(), basedto.toString(),
          basedto.equals(BaseDTO.ResultPageDTO) ? Constants.IS_ACTIVE : Constants.IS_INACTIVE,
          "default", basedto.getComments()));
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
    Map<String, TablePO> alltablemap = Maps.newHashMap();
    // 获取最新的apibase
    List<ApiBasePO> ApiBases = apiBaseService.findAllApiBase(id);
    // 获取最新的apibase
    ApiBasePO firstApiBase = apiBaseService.findEarlestApiBaseByProjectId(id);
    // 生成配置文件代码
    generateService.generateConfigFiles(project, datasources, ApiBases, firstApiBase);
    // 查询对应项目下的DTO信息
    List<TransferObjPO> transferObjs = transObjService.findAllTransferObj(id);
    // 需要创建service的表
    Set<String> tableNeedServiceCreated = Sets.newHashSet();
    for (TransferObjPO transferObj : transferObjs) {
      // 查询DTO下对应DTO属性
      List<TransferObjFieldPO> transferObjFileds =
          transferObjFieldService.findAllTransferObjFieldBySort(transferObj.getId());
      // 生成DTO文件代码
      generateService.generateDTOFiles(project.getName(), project.getPackages().toLowerCase(),
          transferObj, transferObjFileds);
    }

    // 生成advice代码
    generateService.generateAdvice(project.getPackages(), project.getName());

    // 取出表和字段方便之后使用
    for (DatasourcePO datasource : datasources) {
      // 查询对应数据源下的所有表
      List<TablePO> tables = tableService.findByDatasourceId(datasource.getId());
      Map<String, TablePO> tableMaps = Maps.newHashMap();
      for (TablePO table : tables) {
        // 所有数据库所有表
        alltablemap.put(table.getId(), table);
        List<ColumnPO> columns = tableService.findMergedColumns(table.getId());
        Map<String, ColumnPO> columnMaps = Maps.newHashMap();
        for (ColumnPO column : columns) {
          // 该表所有字段
          if (Constants.IS_ACTIVE.equals(column.getIsPk())) {
            column.setSortWeight(Integer.MIN_VALUE);
          }
          columnMaps.put(column.getName(), column);
        }
        table.setColumnMaps(columnMaps);
        // 该数据库所有表
        tableMaps.put(table.getId(), table);
      }
      // 添加关系
      for (TablePO table : tableMaps.values()) {
        // 获取主表关系表
        List<TableRelationPO> tableRelations =
            tableService.findSlaveTableRelationList(table.getId());
        if (Constants.IS_ACTIVE.equals(table.getIsAutoCrud())) {
          tableNeedServiceCreated.add(table.getId());
        }
        // 注入从表数据库类型
        for (TableRelationPO tableRelation : tableRelations) {
          tableNeedServiceCreated.add(tableRelation.getMasterTableId());
          tableNeedServiceCreated.add(tableRelation.getSlaveTableId());
        }
        table.setMasterTableRelations(tableRelations);
        // 获取从表关系表
        List<TableRelationPO> tableRelationsForMaster =
            tableService.findTableRelationList(table.getId());
        table.setSlaveTableRelations(tableRelationsForMaster);
      }
      // 放入数据源
      datasource.setTableMaps(tableMaps);
    }

    // 获取需要自动生成crud的table
    boolean isAutoGen = false;
    for (DatasourcePO datasource : datasources) {
      // 获得对应数据源下的所有表
      Set<TablePO> tables = Sets.newHashSet();
      for (TablePO table : datasource.getTableMaps().values()) {
        if (tableNeedServiceCreated.contains(table.getId())) {
          tables.add(table);
          table.setPackageName(datasource.getPackageName());
          // 生成servcie和serviceImpl
          generateService.generateIServiceAndServiceImpl(datasource.getPackageName(),
              project.getName(), project.getPackages(), table.getName(),
              table.getMasterTableRelations(), null, false);
          // TODO 判断mybatis和jpa 生成JPAService和JPAServiceImpl
          // generateService.generateJPAServiceAndJPAServiceImpl(datasource.getPackageName(),
          // project.getName(), project.getPackages(), table, transferObjs.get(0).getPackageName());
        }
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
      for (TablePO table : datasource.getTableMaps().values()) {
        // 生成实体文件代码
        generateService.generateEntityFiles(datasource.getPackageName(), project.getName(),
            project.getPackages().toLowerCase(), table,
            Lists.newArrayList(table.getColumnMaps().values()));
        // 生成高级查询代码
        this.generateSeniorSearchCode(project, alltablemap, datasource, table);
        // TODO 判断mybatis和jpa 生成Jpa Repository
        // generateService.generateRepository(datasource.getPackageName(), project.getName(),
        // project.getPackages().toLowerCase(), table.getName());
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
        List<ApiParamPO> apiparams = apiParamService.findAllApiParamOrderBySort(apiobj.getId());
        apiobj.setApiParams(apiparams);
        // 添加包名
        if (Constants.IS_ACTIVE.equals(apiobj.getIsAutoGen())) {
          // 判断是否为从表
          String tableName;
          if (StringUtils.isNotBlank(apiobj.getGenRelatedTableId()) && !apiobj.getIsSenior()) {
            apiobj.setSlaveUri(true);
            // 去掉添加的"s"结尾
            tableName = alltablemap.get(apiobj.getGenRelatedTableId()).getName().toLowerCase();
          } else if (apiobj.getIsSenior()) {
            apiobj.setSlaveUri(false);
            // 高级查询映射的实体驼峰名
            tableName = alltablemap.get(apiobj.getGenBasedTableId()).getName().concat("_senior");
          } else {
            apiobj.setSlaveUri(false);
            // 如果是主表则取直接取表名
            tableName = alltablemap.get(apiobj.getGenBasedTableId()).getName();
          }
          apiobj.setServiceName(alltablemap.get(apiobj.getGenBasedTableId()).getPackageName()
              .concat(".I")
              .concat(
                  CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase()))
              .concat("Service"));
          apiobj.setRelateTableName(tableName);
          // 获取表主键名
          List<String> substrs =
              PatternUtils.getMatchedSubString(apiobj.getUri(), "(?<=\\{)(.+?)(?=\\})");
          if (apiobj.getIsSlaveUri()) {
            if (substrs.size() > 1) {
              // uri的结构为"/a/{aid}/b/{bid}";
              apiobj.setFirstPathVar(substrs.get(substrs.size() - 2));
              apiobj.setLastPathVar(substrs.get(substrs.size() - 1));
            } else {
              apiobj.setFirstPathVar(substrs.get(0));
            }
          } else {
            if (substrs.size() == 1) {
              // uri的结构为"/a/{aid}";
              apiobj.setLastPathVar(substrs.get(0));
            }
          }
          // 如果是高级关联查询
          if (apiobj.getIsSenior()) {
            List<String> subRelStrs = PatternUtils.getMatchedSubString(apiobj.getUri(),
                "(?<=" + tableName + "s\\/)(.+?)(?=\\/pages)");
            if (!subRelStrs.isEmpty()) {
              apiobj.setSeniorRelationVar(subRelStrs.get(0));
            }
          }
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
   * 生成高级查询的Mapper, Mapper.xml, Service
   * 
   * @param project
   * @param alltablemap
   * @param datasources
   */
  private void generateSeniorSearchCode(ProjectPO project, Map<String, TablePO> alltablemap,
      DatasourcePO datasource, TablePO table) {
    // 获取表的高级关联关系
    List<TableSeniorRelationPO> relations =
        seniorRelationRepository.findByMasterTableIdOrderByCreatedAtAsc(table.getId());
    String seniorTableName = table.getName().concat("_senior");
    // dto包含的表
    List<SeniorDtoAttribute> attrs = Lists.newArrayList();
    // 用于去重
    List<String> tableNames = Lists.newArrayList();
    // dto包含的关系
    List<SeniorDtoRelation> relationMethods = Lists.newArrayList();
    // 如果不为空
    if (!relations.isEmpty()) {
      List<ColumnPO> columns = Lists.newArrayList(table.getColumnMaps().values());
      // 排序
      columns.sort((ColumnPO a, ColumnPO b) -> Integer.valueOf(a.getSortWeight())
          .compareTo(Integer.valueOf(b.getSortWeight())));
      // 放入主表
      SeniorDtoAttribute masterAttr = new SeniorDtoAttribute(
          CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, table.getName().toLowerCase()),
          table.getName(), table.getClassName(), columns);
      attrs.add(masterAttr);
      tableNames.add(table.getId());
      // 创建service
      generateService.generateIServiceAndServiceImpl(datasource.getPackageName(), project.getName(),
          project.getPackages(), seniorTableName, null, relations, true);
      // 创建mapper文件
      int i = 1;
      for (TableSeniorRelationPO relation : relations) {
        // 关系包含的表
        List<SeniorDtoAttribute> relationAttrs = Lists.newArrayList();
        relationAttrs.add(masterAttr);
        // 用于去重
        List<String> relationTableNames = Lists.newArrayList();
        relationTableNames.add(table.getId());
        // 关联从表
        List<TableSeniorSlavePO> slaves =
            tableSeniorSlaveRepository.findBySeniorIdOrderByCreatedAtAsc(relation.getId());
        for (TableSeniorSlavePO slave : slaves) {
          if (!tableNames.contains(slave.getSlaveTableId())) {
            List<ColumnPO> slavecolumns = Lists.newArrayList(table.getColumnMaps().values());
            // 排序
            slavecolumns.sort((ColumnPO a, ColumnPO b) -> Integer.valueOf(a.getSortWeight())
                .compareTo(Integer.valueOf(b.getSortWeight())));
            // 获取从表用于dto
            TablePO slaveTable = alltablemap.get(slave.getSlaveTableId());
            SeniorDtoAttribute slaveAttr = new SeniorDtoAttribute(
                CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,
                    slaveTable.getName().toLowerCase()),
                slaveTable.getName(), slaveTable.getClassName(), slavecolumns);
            attrs.add(slaveAttr);
            tableNames.add(slave.getSlaveTableId());
          }
          if (!relationTableNames.contains(slave.getSlaveTableId())) {
            List<ColumnPO> slavecolumns = Lists.newArrayList(table.getColumnMaps().values());
            // 排序
            slavecolumns.sort((ColumnPO a, ColumnPO b) -> Integer.valueOf(a.getSortWeight())
                .compareTo(Integer.valueOf(b.getSortWeight())));
            // 获取从表用于关联关系sql
            TablePO slaveTable = alltablemap.get(slave.getSlaveTableId());
            SeniorDtoAttribute slaveAttr = new SeniorDtoAttribute(
                CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,
                    slaveTable.getName().toLowerCase()),
                slaveTable.getName(), slaveTable.getClassName(), slavecolumns);
            relationAttrs.add(slaveAttr);
            relationTableNames.add(slave.getSlaveTableId());
          }
          // 关联从表字段
          slave.setRelationColumns(
              tableSeniorColumnRepository.findBySeniorSlaveIdOrderByCreatedAtAsc(slave.getId()));
        }
        relation.setRelationTables(slaves);
        // 添加关系
        SeniorDtoRelation relationMethod =
            new SeniorDtoRelation("Relation" + i, relationAttrs, slaves);
        relationMethods.add(relationMethod);
        i++;
      }
      generateService.generateDAOFiles(datasource.getPackageName(), project.getName(),
          project.getPackages(), seniorTableName, relations, attrs, relationMethods);
    }
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
    // dictionary组件
    categories.add(this.getComponentCategory(() -> Dictionary.isMultiSelect(),
        () -> Dictionary.getTypeCname(), (List<Component> components) -> {
          for (Dictionary dictionary : Dictionary.values()) {
            components.add(new Component(dictionary.name(), dictionary.getCname()));
          }
        }));
    return categories;
  }

  /**
   * 打包前台项目代码
   * 
   * @param projectName
   * @return 压缩地址
   */
  private String zipUicode(String projectName) {
    // 压缩生成的项目
    String projectZipPath =
        GeneratorUtils.getProjectZipPath(genProperties.getProjectZipPath(), projectName);
    // 删除旧压缩文件及文件夹
    FileUtil.delete(new File(projectZipPath));
    File f = new File(genProperties.getProjectUiPath());
    String srcPath = f.getParent() + File.separator + projectName;
    FileUtils.zipFiles(srcPath, "*", projectZipPath);
    FileUtil.delete(new File(srcPath));
    return projectZipPath;
  }

  /**
   * 下载前台代码
   */
  @Override
  public File downloadZipUIFiles(String projectName) {
    generateService.generateUIFiles(projectName);
    String projectZipPath = zipUicode(projectName);
    return new File(projectZipPath);
  }

  /**
   * 比较字符串
   * 
   * @param compoents
   * @param emun
   * @return
   */
  public Boolean compareStr(String compoents, String emun) {
    for (String str : compoents.split(",")) {
      if (emun.equals(str)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 判断是否生成dictType和dictValue表
   */
  @Override
  public String creatDictTable(String projectId) {
    // 获取compoent
    String compoents = projectRepo.findOne(projectId).getComponents();
    // 创建判断表示
    Boolean dictFlag = false;
    // 判断是否启用字典表
    for (String compoent : compoents.split(",")) {
      if (Dictionary.dictionary.toString().equals(compoent)) {
        dictFlag = true;
      }
    }
    if (dictFlag) {
      // 获取数据源列表
      List<DatasourcePO> datasources = datasourceService.findByProjectId(projectId);
      for (DatasourcePO datasource : datasources) {
        // 判断数据源中是否存在表
        switch (this.isExist(datasource)) {
          case Constants.Type_Value_Exit:
            break;
          case Constants.Type_Value_Unexit:
            tableService.creatDictType(datasource);
            tableService.creatDictValue(datasource);
            break;
          case Constants.Value_Exit:
            tableService.creatDictType(datasource);
            break;
          case Constants.Type_Exit:
            tableService.creatDictValue(datasource);
            break;
        }
      }
      return "字典表已启用";
    } else {
      return "字典表已关闭";
    }
  }

  /**
   * 判断表是否存在
   */
  public String isExist(DatasourcePO datasource) {
    // 创建判断条件
    String check = Constants.Type_Value_Unexit;
    // 创建判断标识
    int exitNum = 0;
    // 查询副数据库
    List<TablePO> originalTables = tableService.findTableListFromOriginalDatasource(datasource);
    // 返回判断结果
    for (TablePO table : originalTables) {
      if (Constants.Table_Dict_Type.equals(table.getName())) {
        check = Constants.Type_Exit;
        exitNum++;
      }
      if (Constants.Table_Dict_Value.equals(table.getName())) {
        check = Constants.Value_Exit;
        exitNum++;
      }
    }
    if (exitNum == 2) {
      check = Constants.Type_Value_Exit;
    }
    return check;
  }
}
