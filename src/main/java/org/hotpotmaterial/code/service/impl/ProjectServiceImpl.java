/**
 * 
 */
package org.hotpotmaterial.code.service.impl;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hotpotmaterial.anywhere.common.mvc.page.rest.request.Filter;
import org.hotpotmaterial.anywhere.common.mvc.page.rest.request.PageDTO;
import org.hotpotmaterial.anywhere.common.utils.FileUtil;
import org.hotpotmaterial.anywhere.common.utils.FileUtils;
import org.hotpotmaterial.code.common.BaseDTO;
import org.hotpotmaterial.code.common.BaseType;
import org.hotpotmaterial.code.common.Constants;
import org.hotpotmaterial.code.common.PredictUtils;
import org.hotpotmaterial.code.common.component.Dictionary;
import org.hotpotmaterial.code.common.component.Excel;
import org.hotpotmaterial.code.common.component.Security;
import org.hotpotmaterial.code.common.component.UiConfig;
import org.hotpotmaterial.code.config.property.GenerateProperties;
import org.hotpotmaterial.code.dto.Component;
import org.hotpotmaterial.code.dto.ComponentCategory;
import org.hotpotmaterial.code.dto.PackObj;
import org.hotpotmaterial.code.dto.RefObjDTO;
import org.hotpotmaterial.code.dto.SeniorDtoAttribute;
import org.hotpotmaterial.code.dto.SeniorDtoRelation;
import org.hotpotmaterial.code.dto.SimpleDataObj;
import org.hotpotmaterial.code.entity.ApiBasePO;
import org.hotpotmaterial.code.entity.ApiObjPO;
import org.hotpotmaterial.code.entity.ApiParamPO;
import org.hotpotmaterial.code.entity.ColumnPO;
import org.hotpotmaterial.code.entity.DatasourcePO;
import org.hotpotmaterial.code.entity.ProjectPO;
import org.hotpotmaterial.code.entity.TablePO;
import org.hotpotmaterial.code.entity.TableRelationPO;
import org.hotpotmaterial.code.entity.TableSeniorRelationPO;
import org.hotpotmaterial.code.entity.TableSeniorSlavePO;
import org.hotpotmaterial.code.entity.TransferObjFieldPO;
import org.hotpotmaterial.code.entity.TransferObjPO;
import org.hotpotmaterial.code.exception.CodeCommonException;
import org.hotpotmaterial.code.repository.ProjectRepository;
import org.hotpotmaterial.code.repository.TableSeniorColumnRepository;
import org.hotpotmaterial.code.repository.TableSeniorRelationRepository;
import org.hotpotmaterial.code.repository.TableSeniorSlaveRepository;
import org.hotpotmaterial.code.service.IApiBaseService;
import org.hotpotmaterial.code.service.IApiObjService;
import org.hotpotmaterial.code.service.IApiParamService;
import org.hotpotmaterial.code.service.IDatasourceService;
import org.hotpotmaterial.code.service.IGenerateService;
import org.hotpotmaterial.code.service.IProjectService;
import org.hotpotmaterial.code.service.ITableService;
import org.hotpotmaterial.code.service.ITransferObjFieldService;
import org.hotpotmaterial.code.service.ITransferObjService;
import org.hotpotmaterial.code.utils.GeneratorUtils;
import org.hotpotmaterial.code.utils.PatternUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.CaseFormat;
import com.google.common.base.Function;
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
  public Page<ProjectPO> findProjecsPage(PageDTO searchParams, String usercode) {
    List<Order> orders = Lists.newArrayList();
    // 排序
    if (searchParams.getOrders() != null) {
      for (org.hotpotmaterial.anywhere.common.mvc.page.rest.request.Order order : searchParams
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
   * 创建数据
   */
  @Override
  @Transactional("jpaTransactionManager")
  public ProjectPO createProject(ProjectPO project, String token, HttpServletRequest request,
      String usercode) {
    project.setCreatedBy(usercode);
    return this.saveProject(project, token, request);
  }

  /**
   * 更新数据
   */
  @Override
  @Transactional("jpaTransactionManager")
  public ProjectPO updateProject(ProjectPO project, String token, HttpServletRequest request,
      String usercode) {
    // 当前的project
    ProjectPO updateProject =
        projectRepo.findByIdAndDelFlag(project.getId(), Constants.DATA_IS_NORMAL);
    if (null == updateProject) {
      throw new CodeCommonException("未找到项目");
    }
    // 旧数据源
    List<DatasourcePO> datasources = datasourceService.findByProjectId(project.getId());
    // 需要删除的数据源
    List<DatasourcePO> delDbs = Lists.newArrayList();
    // 需要新增的数据源
    List<DatasourcePO> newDbs = Lists.newArrayList();
    if (!datasources.isEmpty() && datasources != null) {
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
          // TODO 删除api
          apiObjService.deleteByGenBasedTableIdIn(delTableIds);
        }
        // 删除表
        tableService.deleteByDatasourceIdIn(delDbIds);
        // 删除数据源
        datasourceService.deleteDatasources(delDbs);
      }
    } else {
      newDbs = project.getDatasources();
    }
    // 添加新增的数据源
    project.setDatasources(newDbs);
    // 更新属性
    updateProject.updateAttrs(project);
    // 更新数据库
    project = this.saveProject(updateProject, token, request);
    return project;
  }

  /**
   * 保存数据
   */
  @Override
  public ProjectPO saveProject(ProjectPO project, String token, HttpServletRequest request) {
    List<DatasourcePO> datasources = project.getDatasources();
    project = projectRepo.save(project);
    // 存入数据源
    if (datasources != null) {
      for (DatasourcePO datasource : datasources) {
        datasource.setProjectId(project.getId());
      }
      datasourceService.saveDatasources(datasources);
    }
    // 创建表
    this.creatNeedTables(project);
    return project;
  }

  /**
   * 获取项目
   */
  @Override
  public ProjectPO getProjectById(String id, String usercode) {
    // 项目
    ProjectPO project = projectRepo.findByIdAndDelFlag(id, Constants.DATA_IS_NORMAL);
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

  /**
   * 获取项目dto
   * 
   * @param id
   * @return
   */
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

  /**
   * 获取项目po
   * 
   * @param id
   * @return
   */
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
  public String generateCodeFiles(String id, String usercode) {
    // 日期版本
    String version = String.valueOf(System.currentTimeMillis());
    // 获得project
    ProjectPO project = this.getProjectById(id, usercode);
    // 获得数据源
    List<DatasourcePO> datasources = datasourceService.findByProjectId(id);
    // 获取表map
    Map<String, TablePO> alltablemap = Maps.newHashMap();
    // 需要创建service的表
    Set<String> tableNeedServiceCreated = Sets.newHashSet();
    // 获取最新的apibase
    List<ApiBasePO> ApiBases = apiBaseService.findAllApiBase(id);
    // 获取最新的apibase
    ApiBasePO firstApiBase = apiBaseService.findEarlestApiBaseByProjectId(id);
    // 获取组件
    List<String> components = Lists.newArrayList();
    components.addAll(Arrays.asList(project.getComponents().split(",")));
    // 生成配置文件代码
    generateService.generateConfigFiles(version, project, datasources, ApiBases, firstApiBase,
        components);

    // 生成advice代码
    generateService.generateAdvice(version, project.getPackages(), project.getName());

    // 取出表和字段方便之后使用
    for (DatasourcePO datasource : datasources) {
      // 查询对应数据源下的所有表
      List<TablePO> tables = tableService.findByDatasourceId(datasource.getId());
      Map<String, TablePO> tableMaps = Maps.newHashMap();
      for (TablePO table : tables) {
        // 所有数据库所有表
        alltablemap.put(table.getId(), table);
        List<ColumnPO> columns = tableService.findMergedColumns(table.getId());
        LinkedHashMap<String, ColumnPO> columnMaps = Maps.newLinkedHashMap();
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
      List<TablePO> tables = Lists.newArrayList();
      for (TablePO table : datasource.getTableMaps().values()) {
        if (tableNeedServiceCreated.contains(table.getId())) {
          tables.add(table);
          table.setPackageName(datasource.getPackageName());
          // 生成servcie和serviceImpl
          generateService.generateIServiceAndServiceImpl(version, datasource.getPackageName(),
              project.getName(), project.getPackages(), table.getName(),
              table.getMasterTableRelations(), null, false, components);
          // TODO 判断mybatis和jpa 生成JPAService和JPAServiceImpl
          // generateService.generateJPAServiceAndJPAServiceImpl(datasource.getPackageName(),
          // project.getName(), project.getPackages(), table, transferObjs.get(0).getPackageName());
        }
      }
      // 数据库代码生成xml
      if (!tables.isEmpty()) {
        generateService.generateGeneratorConfigFiles(version, project.getName(),
            project.getPackages().toLowerCase(), datasource, tables);
        isAutoGen = true;
      }
    }

    // 生成mybatis代码
    if (isAutoGen) {
      generateService.generateMybatisFiles(version, project.getName());
    }

    // 生成实体
    for (DatasourcePO datasource : datasources) {
      // 查询对应数据源下的所有表
      for (TablePO table : datasource.getTableMaps().values()) {
        // 生成实体文件代码
        if (!Constants.TABLE_NOENTITY.contains(table.getName().toLowerCase())) {
          generateService.generateEntityFiles(version, datasource.getPackageName(), project.getName(),
              project.getPackages().toLowerCase(), table,
              Lists.newArrayList(table.getColumnMaps().values()));
          // 生成高级查询代码
          this.generateSeniorSearchCode(version, project, alltablemap, datasource, table);
          // TODO 判断mybatis和jpa 生成Jpa Repository
          // generateService.generateRepository(datasource.getPackageName(), project.getName(),
          // project.getPackages().toLowerCase(), table.getName());
        }
      }
    }

    // 查询对应项目下的DTO信息
    List<TransferObjPO> transferObjs = transObjService.findAllTransferObj(id);
    for (TransferObjPO transferObj : transferObjs) {
      // 查询DTO下对应DTO属性
      List<TransferObjFieldPO> transferObjFileds =
          transferObjFieldService.findAllTransferObjFieldBySort(transferObj.getId());
      // 获取高级关系
      List<SeniorDtoRelation> relations = Lists.newArrayList();
      String seniorName = "";
      if (Constants.IS_ACTIVE.equals(transferObj.getIsSenior())) {
        TablePO table = alltablemap.get(transferObj.getGenBasedTableId());
        if (null != table) {
          relations = table.getRelationMethods();
          seniorName = table.getName().toLowerCase().concat("_senior");
        }
      }
      generateService.generateDTOFiles(version, project.getName(),
          project.getPackages().toLowerCase(), transferObj, transferObjFileds, relations,
          seniorName);
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
              // 一对多情况
              // uri的结构为"/a/{aid}/b/{bid}";
              apiobj.setFirstPathVar(substrs.get(substrs.size() - 2));
              apiobj.setLastPathVar(substrs.get(substrs.size() - 1));
            } else {
              // 单表或者一对一
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
        generateService.generateControllerFiles(version, project, apibase, entry.getKey(),
            entry.getValue());
      }
    }

    // 文件名
    String dirname = project.getName().concat("_").concat(version);

    // 打包代码
    this.zipcode(dirname);

    return "/codegen/api/v1/projects/" + dirname + "/download";
  }

  /**
   * 生成高级查询的Mapper, Mapper.xml, Service
   * 
   * @param project
   * @param alltablemap
   * @param datasources
   */
  private void generateSeniorSearchCode(String version, ProjectPO project,
      Map<String, TablePO> alltablemap, DatasourcePO datasource, TablePO table) {
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
    // 获取组件
    List<String> components = Lists.newArrayList();
    components.addAll(Arrays.asList(project.getComponents().split(",")));
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
      generateService.generateIServiceAndServiceImpl(version, datasource.getPackageName(),
          project.getName(), project.getPackages(), seniorTableName, null, relations, true,
          components);
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
        // 从表map
        Map<String, TablePO> slavetables = Maps.newHashMap();
        // 关系描述
        String relationDesc = table.getName();
        for (TableSeniorSlavePO slave : slaves) {
          TablePO slaveTable = null;
          if (!tableNames.contains(slave.getSlaveTableId())) {
            // 获取从表用于dto
            slaveTable = alltablemap.get(slave.getSlaveTableId());
            // 如果为空
            if (null == slaveTable) {
              slaveTable = slavetables.get(slave.getSlaveTableId());
              if (null == slaveTable) {
                // 数据库获取表
                slaveTable = tableService.findById(slave.getSlaveTableId());
                // 数据库获取字段
                List<ColumnPO> slavecolumns = tableService.findMergedColumns(slaveTable.getId());
                LinkedHashMap<String, ColumnPO> columnMaps = Maps.newLinkedHashMap();
                for (ColumnPO column : slavecolumns) {
                  // 该表所有字段
                  if (Constants.IS_ACTIVE.equals(column.getIsPk())) {
                    column.setSortWeight(Integer.MIN_VALUE);
                  }
                  columnMaps.put(column.getName(), column);
                }
                // 注入字段map
                slaveTable.setColumnMaps(columnMaps);
                // 将表实体放入map中
                slavetables.put(slave.getSlaveTableId(), slaveTable);
              }
            }
            // 获取字段
            List<ColumnPO> slavecolumns = Lists.newArrayList(slaveTable.getColumnMaps().values());
            // 排序
            slavecolumns.sort((ColumnPO a, ColumnPO b) -> Integer.valueOf(a.getSortWeight())
                .compareTo(Integer.valueOf(b.getSortWeight())));
            // 属性
            SeniorDtoAttribute slaveAttr = new SeniorDtoAttribute(
                CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,
                    slaveTable.getName().toLowerCase()),
                slaveTable.getName(), slaveTable.getClassName(), slavecolumns);
            attrs.add(slaveAttr);
            tableNames.add(slave.getSlaveTableId());
          }
          if (!relationTableNames.contains(slave.getSlaveTableId())) {
            // 获取从表用于关联关系sql
            List<ColumnPO> slavecolumns = Lists.newArrayList(slaveTable.getColumnMaps().values());
            // 排序
            slavecolumns.sort((ColumnPO a, ColumnPO b) -> Integer.valueOf(a.getSortWeight())
                .compareTo(Integer.valueOf(b.getSortWeight())));
            SeniorDtoAttribute slaveAttr = new SeniorDtoAttribute(
                CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,
                    slaveTable.getName().toLowerCase()),
                slaveTable.getName(), slaveTable.getClassName(), slavecolumns);
            relationAttrs.add(slaveAttr);
            // 添加表到关系描述
            relationDesc = relationDesc.concat("与").concat(slaveTable.getName());
            relationTableNames.add(slave.getSlaveTableId());
          }
          // 关联从表字段
          slave.setRelationColumns(
              tableSeniorColumnRepository.findBySeniorSlaveIdOrderByCreatedAtAsc(slave.getId()));
        }
        relation.setRelationTables(slaves);
        // 添加关系
        SeniorDtoRelation relationMethod = new SeniorDtoRelation("relation_" + i,
            relationDesc.concat("高级关联查询"), relationAttrs, slaves);
        relationMethods.add(relationMethod);
        i++;
      }
      // 保存高级关联
      table.setRelationMethods(relationMethods);
      // 生成dao
      generateService.generateDAOFiles(version, datasource.getPackageName(), project.getName(),
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
    // dictionary组件
    categories.add(this.getComponentCategory(() -> Dictionary.isMultiSelect(),
        () -> Dictionary.getTypeCname(), (List<Component> components) -> {
          for (Dictionary dictionary : Dictionary.values()) {
            components.add(new Component(dictionary.name(), dictionary.getCname()));
          }
        }));
    // uiconfig组件
    categories.add(this.getComponentCategory(() -> UiConfig.isMultiSelect(),
        () -> UiConfig.getTypeCname(), (List<Component> components) -> {
          for (UiConfig uiconfig : UiConfig.values()) {
            components.add(new Component(uiconfig.name(), uiconfig.getCname()));
          }
        }));
    // excel组件
    categories.add(this.getComponentCategory(() -> Excel.isMultiSelect(),
        () -> Excel.getTypeCname(), (List<Component> components) -> {
          for (Excel excel : Excel.values()) {
            components.add(new Component(excel.name(), excel.getCname()));
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
    String projectZipPath = GeneratorUtils.getProjectZipPath(genProperties.getProjectZipPath(),
        projectName.concat("-ui"));
    // 删除旧压缩文件及文件夹
    FileUtil.delete(new File(projectZipPath));
    String srcPath = genProperties.getProjectUiTempPath().concat(projectName);
    FileUtils.zipFiles(srcPath, "*", projectZipPath);
    FileUtil.delete(new File(srcPath));
    return projectZipPath;
  }

  /**
   * 下载前台代码
   */
  @Override
  public File downloadZipUIFiles(String projectId, String usercode) {
    // 获取项目信息
    ProjectPO project = this.getProjectById(projectId, usercode);
    // 复制ui文件
    FileUtils.copyDirectoryCover(genProperties.getProjectUiPath(),
        genProperties.getProjectUiTempPath().concat(project.getName()), true);
    // 生成util文件
    generateService.generateUIFiles(project.getDescription(), project.getName(),
        project.getPackages(), project.getAppId());
    String projectZipPath = zipUicode(project.getName());
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
   * 判断是否生成dictType和dictValue和uiconfig表
   */
  @Override
  public String creatNeedTables(ProjectPO project) {
    // 创建判断表示
    Boolean dictFlag = false;
    // 判断是否启用字典表
    for (String compoent : project.getComponents().split(",")) {
      if (Dictionary.dictionary.toString().equals(compoent)) {
        dictFlag = true;
        break;
      }
    }
    // 判断是否启用前端配置表
    Boolean uienableFlag = false;
    for (String compoent : project.getComponents().split(",")) {
      if (UiConfig.uiConfigEnabled.toString().equals(compoent)) {
        uienableFlag = true;
        break;
      }
    }
    // 判断是否启用前权限组件表
    Boolean securityenableFlag = false;
    for (String compoent : project.getComponents().split(",")) {
      if (Security.enablesecurity.toString().equals(compoent)) {
        securityenableFlag = true;
        break;
      }
    }
    // 获取数据源列表
    List<DatasourcePO> datasources = datasourceService.findByProjectId(project.getId());
    // 默认第一个数据库添加
    if (null != datasources && !datasources.isEmpty()) {
      // 需要创建的表
      List<String> keyTables = this.isExist(datasources.get(0));
      // 判断数据源中是否存在表
      if (dictFlag && !keyTables.contains(Constants.Table_Dict_Type)) {
        tableService.creatDictType(datasources.get(0));
      }
      if (dictFlag && !keyTables.contains(Constants.Table_Dict_Value)) {
        tableService.creatDictValue(datasources.get(0));
      }
      if (uienableFlag && !keyTables.contains(Constants.Table_UICONFIG)) {
        tableService.createUiConfig(datasources.get(0));
      }
      if (securityenableFlag && !keyTables.contains(Constants.TABLE_NOENTITY)) {
        tableService.createSecurity(datasources.get(0));
      }
    }

    return null;
  }

  /**
   * 判断表是否存在
   */
  private List<String> isExist(DatasourcePO datasource) {
    // 查询副数据库
    List<TablePO> originalTables = tableService.findTableListFromOriginalDatasource(datasource);
    List<String> keyTables = Lists.newArrayList();
    // 返回判断结果
    for (TablePO table : originalTables) {
      if (Constants.Table_Dict_Type.equals(table.getName().toLowerCase())
          || Constants.Table_Dict_Value.equals(table.getName().toLowerCase())
          || Constants.Table_UICONFIG.equals(table.getName().toLowerCase())) {
        keyTables.add(table.getName().toLowerCase());
      }
    }
    return keyTables;
  }

  /**
   * 逻辑删除项目
   */
  @Override
  public void deleteProject(String projectId) {
    // 判断项目是否存在
    if (projectRepo.findByIdAndDelFlag(projectId, Constants.DATA_IS_NORMAL) != null) {
      // 执行逻辑删除
      projectRepo.updateByProjectId(projectId, Constants.DATA_IS_INVALID);
    } else {
      throw new CodeCommonException("需要删除的项目不存在!");
    }
  }

  /**
   * 构建description
   * 
   * @param components
   * @return
   */
  public String getDescription(String components) {
    // 构建description字符串
    StringBuffer description = new StringBuffer();
    // 拆分组件字符串
    String[] component = components.split(",");
    for (String str : component) {
      if (Dictionary.dictionary.toString().equals(str)) {
        description.append(Dictionary.dictionary.toString() + ",");
      }
    }
    return description.toString();
  }

  /**
   * 生成单表相关代码
   */
  public String generateCodeFilesUnzip(TablePO mainTable, ProjectPO project,
      DatasourcePO datasource) {
    // 获取组件
    List<String> components = Lists.newArrayList();
    components.addAll(Arrays.asList(project.getComponents().split(",")));
    // 日期版本
    String version = String.valueOf(System.currentTimeMillis());
    // 需要创建service的关联表
    Map<String, TablePO> tableMap = Maps.newHashMap();
    tableMap.put(mainTable.getId(), mainTable);
    // 获取主表关系表
    List<TableRelationPO> tableRelations =
        tableService.findSlaveTableRelationList(mainTable.getId());
    mainTable.setMasterTableRelations(tableRelations);
    // 主表实体
    for (TableRelationPO tableRelation : tableRelations) {
      tableMap.put(tableRelation.getMasterTableId(), tableRelation.getMasterTable());
    }
    // 获取从表关系表
    List<TableRelationPO> tableRelationsForMaster =
        tableService.findTableRelationList(mainTable.getId());
    mainTable.setSlaveTableRelations(tableRelationsForMaster);
    // 从表实体
    for (TableRelationPO tableRelation : tableRelationsForMaster) {
      tableMap.put(tableRelation.getSlaveTableId(), tableRelation.getSlaveTable());
    }
    // 获取所有表的字段
    for (TablePO table : tableMap.values()) {
      List<ColumnPO> columns = tableService.findMergedColumns(table.getId());
      LinkedHashMap<String, ColumnPO> columnMaps = Maps.newLinkedHashMap();
      for (ColumnPO column : columns) {
        // 该表所有字段
        if (Constants.IS_ACTIVE.equals(column.getIsPk())) {
          column.setSortWeight(Integer.MIN_VALUE);
        }
        columnMaps.put(column.getName(), column);
      }
      // 注入字段map
      table.setColumnMaps(columnMaps);
    }
    // 获取需要自动生成crud的table
    boolean isAutoGen = false;
    // 生成servcie和serviceImpl
    for (TablePO table : tableMap.values()) {
      table.setPackageName(datasource.getPackageName());
      generateService.generateIServiceAndServiceImpl(version, datasource.getPackageName(),
          project.getName(), project.getPackages(), table.getName(),
          table.getMasterTableRelations(), null, false, components);
    }
    // 数据库代码生成xml
    if (!tableMap.values().isEmpty()) {
      generateService.generateGeneratorConfigFiles(version, project.getName(),
          project.getPackages().toLowerCase(), datasource, Lists.newArrayList(tableMap.values()));
      isAutoGen = true;
    }
    // 生成mybatis代码
    if (isAutoGen) {
      // 创建resources文件夹
      FileUtils.createDirectory(
          genProperties.getProjectPath() + File.separator + project.getName() + "_" + version
              + File.separator + "src" + File.separator + "main" + File.separator + "resources");
      generateService.generateMybatisFiles(version, project.getName());
    }
    // 生成实体及高级查询
    for (TablePO table : tableMap.values()) {
      // 生成实体代码
      generateService.generateEntityFiles(version, datasource.getPackageName(), project.getName(),
          project.getPackages().toLowerCase(), table,
          Lists.newArrayList(table.getColumnMaps().values()));
      // 生成高级查询代码
      this.generateSeniorSearchCode(version, project, tableMap, datasource, table);
    }

    // 查询表对应的DTO信息
    List<TransferObjPO> transferObjs =
        transObjService.findAllTransferObjByTableId(mainTable.getId());
    for (TransferObjPO transferObj : transferObjs) {
      // 查询DTO下对应DTO属性
      List<TransferObjFieldPO> transferObjFileds =
          transferObjFieldService.findAllTransferObjFieldBySort(transferObj.getId());
      // 获取高级关系
      List<SeniorDtoRelation> relations = Lists.newArrayList();
      String seniorName = "";
      if (Constants.IS_ACTIVE.equals(transferObj.getIsSenior())) {
        TablePO table = tableMap.get(transferObj.getGenBasedTableId());
        if (null != table) {
          relations = table.getRelationMethods();
          seniorName = table.getName().toLowerCase().concat("_senior");
        }
      }
      // 生成DTO文件代码
      generateService.generateDTOFiles(version, project.getName(),
          project.getPackages().toLowerCase(), transferObj, transferObjFileds, relations,
          seniorName);
    }

    // 获取apibase
    List<ApiBasePO> apibases = apiBaseService.findAllApiBase(project.getId());
    Map<String, ApiBasePO> apibaseMap =
        Maps.uniqueIndex(apibases, new Function<ApiBasePO, String>() {
          public String apply(ApiBasePO from) {
            return from.getId();
          }
        });
    // 获取api obj
    List<ApiObjPO> apiobjs = apiObjService.findAllApiObjByTableId(mainTable.getId());
    Map<String, Map<String, List<ApiObjPO>>> controllerMap = Maps.newHashMap();
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
          tableName = tableMap.get(apiobj.getGenRelatedTableId()).getName().toLowerCase();
        } else if (apiobj.getIsSenior()) {
          apiobj.setSlaveUri(false);
          // 高级查询映射的实体驼峰名
          tableName = tableMap.get(apiobj.getGenBasedTableId()).getName().concat("_senior");
        } else {
          apiobj.setSlaveUri(false);
          // 如果是主表则取直接取表名
          tableName = tableMap.get(apiobj.getGenBasedTableId()).getName();
        }
        apiobj
            .setServiceName(tableMap.get(apiobj.getGenBasedTableId()).getPackageName().concat(".I")
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
      Map<String, List<ApiObjPO>> apiMap = controllerMap.get(apiobj.getApiBaseId());
      if (null == apiMap) {
        apiMap = Maps.newHashMap();
      }
      List<ApiObjPO> apilist = apiMap.get(apiobj.getControllerName());
      if (null == apilist) {
        apilist = Lists.newArrayList();
        apilist.add(apiobj);
        apiMap.put(apiobj.getControllerName(), apilist);
      } else {
        apilist.add(apiobj);
      }
      controllerMap.put(apiobj.getApiBaseId(), apiMap);
    }

    // 循环controller map生成controller文件
    for (Entry<String, Map<String, List<ApiObjPO>>> entry1 : controllerMap.entrySet()) {
      for (Entry<String, List<ApiObjPO>> entry2 : entry1.getValue().entrySet()) {
        generateService.generateControllerFiles(version, project, apibaseMap.get(entry1.getKey()),
            entry2.getKey(), entry2.getValue());
      }
    }

    String dirname = project.getName().concat("_").concat(version);

    // 打包代码
    this.zipcode(dirname);

    return "/codegen/api/v1/tables/" + dirname + "/download";
  }
}
