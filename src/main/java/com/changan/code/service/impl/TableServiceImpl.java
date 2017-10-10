/**
 * 
 */
package com.changan.code.service.impl;

import java.io.File;
import java.util.Arrays;
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

import com.alibaba.druid.util.StringUtils;
import com.changan.anywhere.common.datasource.annotation.ChangeDatasource;
import com.changan.anywhere.common.mvc.page.rest.request.Filter;
import com.changan.anywhere.common.mvc.page.rest.request.PageDTO;
import com.changan.code.common.BaseType;
import com.changan.code.common.Constants;
import com.changan.code.common.PredictUtils;
import com.changan.code.common.component.Dictionary;
import com.changan.code.config.property.GenerateProperties;
import com.changan.code.dao.DatabaseDao;
import com.changan.code.dto.RequestOfTableIdsDTO;
import com.changan.code.dto.SimpleDataObj;
import com.changan.code.entity.ApiBasePO;
import com.changan.code.entity.ColumnPO;
import com.changan.code.entity.DatasourcePO;
import com.changan.code.entity.ProjectPO;
import com.changan.code.entity.TablePO;
import com.changan.code.entity.TableRelationPO;
import com.changan.code.entity.TableSeniorColumnPO;
import com.changan.code.entity.TableSeniorRelationPO;
import com.changan.code.entity.TableSeniorSlavePO;
import com.changan.code.entity.TransferObjFieldPO;
import com.changan.code.entity.TransferObjPO;
import com.changan.code.exception.CodeCommonException;
import com.changan.code.repository.ProjectRepository;
import com.changan.code.repository.TableRelationRepository;
import com.changan.code.repository.TableRepository;
import com.changan.code.repository.TableSeniorColumnRepository;
import com.changan.code.repository.TableSeniorRelationRepository;
import com.changan.code.repository.TableSeniorSlaveRepository;
import com.changan.code.service.IApiBaseService;
import com.changan.code.service.IApiObjService;
import com.changan.code.service.IColumnService;
import com.changan.code.service.IDatasourceService;
import com.changan.code.service.IGenerateService;
import com.changan.code.service.IProjectService;
import com.changan.code.service.ITableService;
import com.changan.code.service.ITransferObjService;
import com.changan.code.utils.GeneratorUtils;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author wenxing
 *
 */
@Service
public class TableServiceImpl implements ITableService {

  // 字典表名
  private final static List<String> DICT_TABLES = Arrays.<String>asList("dict_type", "dict_value");

  @Autowired
  private TableRepository tableRepository;

  @Autowired
  private DatabaseDao databaseDao;

  @Autowired
  private IColumnService columnService;

  @Autowired
  private IProjectService projectService;

  @Autowired
  private IDatasourceService datasourceService;

  @Autowired
  private IApiBaseService apibaseService;

  @Autowired
  private IApiObjService apiobjService;

  @Autowired
  private ITransferObjService transobjService;

  @Autowired
  private IGenerateService generateService;

  @Autowired
  private GenerateProperties genProperties;

  @Autowired
  private ProjectRepository projectRepo;

  @Autowired
  private TableRelationRepository tableRelationRepository;

  @Autowired
  private TableSeniorSlaveRepository tableSeniorSlaveRepository;

  @Autowired
  private TableSeniorRelationRepository seniorRelationRepository;

  @Autowired
  private TableSeniorColumnRepository tableSeniorColumnRepository;

  /**
   * 更新数据库表
   * 
   * @param originTables
   * @param masterTables
   */
  @Override
  @Transactional("jpaTransactionManager")
  public void saveAndDelMasterTables(List<TablePO> originTables, List<TablePO> masterTables,
      DatasourcePO datasource, ProjectPO project) {
    // 列出新的表
    List<TablePO> newTables = Lists.newArrayList();
    // 列出删除的表
    List<TablePO> notExistTables = Lists.newArrayList();
    if (null == masterTables || masterTables.isEmpty()) {
      for (TablePO originTable : originTables) {
        if ((Constants.IS_ACTIVE.equals(project.getIsdictionary())
            && !DICT_TABLES.contains(originTable.getName()))
            || !Constants.IS_ACTIVE.equals(project.getIsdictionary())) {
          newTables.add(originTable.setDefaultValue(datasource.getId()));
        }
      }
    } else {
      // master的table放入map中
      Map<String, TablePO> masterTableMap = Maps.newHashMap();
      for (TablePO masterTable : masterTables) {
        masterTableMap.put(masterTable.getName(), masterTable);
      }
      // 识别新增表
      for (TablePO originTable : originTables) {
        if ((Constants.IS_ACTIVE.equals(project.getIsdictionary())
            && !DICT_TABLES.contains(originTable.getName()))
            || !Constants.IS_ACTIVE.equals(project.getIsdictionary())) {
          if (!masterTableMap.containsKey(originTable.getName())) {
            newTables.add(originTable.setDefaultValue(datasource.getId()));
          } else {
            masterTableMap.remove(originTable.getName());
          }
        }
      }
      // 得到删除表
      for (Entry<String, TablePO> entry : masterTableMap.entrySet()) {
        notExistTables.add(entry.getValue());
      }
    }

    // 新建表
    this.saveNewTables(newTables);
    // 新建实体
    for (TablePO table : newTables) {
      transobjService.createAutoCrudDTO(datasource.getProjectId(), table.getId(), table.getName(),
          datasource.getName(),
          datasource.getPackageName().concat(".").concat(table.getClassName()));
    }
    // 删除 表
    this.deleteNotExistTables(notExistTables);
    // 删除实体
    for (TablePO table : notExistTables) {
      transobjService.deleteAutoCrudDTO(table.getId());
    }
    // TODO 删除自动生成的字典表api
  }

  @Override
  @ChangeDatasource
  public List<TablePO> findTableListFromOriginalDatasource(DatasourcePO datasource) {
    return databaseDao.findTableList(datasource.getDbtype(), "");
  }

  @Override
  public List<TablePO> findTableListFromMasterDatasource(String datasourceId) {
    // 获取table
    return tableRepository.findByDatasourceId(datasourceId);
  }

  @Override
  public void saveNewTables(List<TablePO> tables) {
    tableRepository.save(tables);
  }

  @Override
  public void deleteNotExistTables(List<TablePO> tables) {
    tableRepository.delete(tables);
  }

  @Override
  public Page<TablePO> findTablesPage(String datasourceId, PageDTO searchParams) {
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
    Specification<TablePO> spec = new Specification<TablePO>() {
      @Override
      public Predicate toPredicate(Root<TablePO> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = Lists.newArrayList();
        // 查询参数
        if (null != searchParams.getCollection()) {
          for (Filter filter : searchParams.getCollection().getFilters()) {
            predicates.add(PredictUtils.covertFilterToPredicate(root, cb, filter));
          }
        }
        // 逻辑未删除查询
        predicates.add(cb.equal(root.get("delFlag"), Constants.DATA_IS_NORMAL));
        // datasource id
        predicates.add(cb.equal(root.get("datasourceId"), datasourceId));
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
      }
    };

    return tableRepository.findAll(spec, pagereq);
  }

  @Override
  public List<ColumnPO> findMergedColumns(String tableId) {
    // 主数据库获取配置字段
    List<ColumnPO> masterColumns = columnService.findColumnListFromMasterDatasource(tableId);
    // 获取表信息
    TablePO table = tableRepository.findOne(tableId);
    // 获取对应的数据源
    DatasourcePO datasource = datasourceService.findById(table.getDatasourceId());
    // TODO 获取源数据库表字段，如果表不存在
    List<ColumnPO> originColumns =
        columnService.findColumnListFromOriginalDatasource(datasource, table.getName());
    // 返回合并后的字段
    return columnService.findMergedColumnsByTable(masterColumns, originColumns);
  }

  @Override
  public List<SimpleDataObj> findClassnameByDatasourceId(String datasourceId) {
    List<Object[]> results = tableRepository.findClassNameByDatasourceId(datasourceId);
    List<SimpleDataObj> dataobjs = Lists.newArrayList();
    for (Object[] data : results) {
      SimpleDataObj dataobj = new SimpleDataObj((String) data[0], (String) data[1],
          Constants.IS_INACTIVE, null, (String) data[2]);
      dataobjs.add(dataobj);
    }
    return dataobjs;
  }

  @Override
  public Map<String, List<SimpleDataObj>> findClassnameByProjectId(String projectId) {
    List<DatasourcePO> datasources = datasourceService.findByProjectId(projectId);
    Map<String, List<SimpleDataObj>> pomaps = Maps.newHashMap();
    for (DatasourcePO datasource : datasources) {
      List<SimpleDataObj> tableNames = this.findClassnameByDatasourceId(datasource.getId());
      pomaps.put(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, datasource.getName())
          .replace("_", "."), tableNames);
    }

    return pomaps;
  }

  @Override
  @Transactional("jpaTransactionManager")
  public void activeIsAutoCrud(RequestOfTableIdsDTO tableIds) {
    // 通过datasourceId获取datasource
    DatasourcePO datasource = datasourceService.findById(tableIds.getDatasourceId());
    // 通过projectId获取project
    ProjectPO project = projectService.getProjectById(datasource.getProjectId());
    // 获得该项目datasource的数目
    Long dbcount = datasourceService.countByProjectId(project.getId());
    // 获取table
    List<TablePO> tables = tableRepository.findByIdIn(tableIds.getIds());
    // 获取最早版本的api base
    ApiBasePO firstApibase = apibaseService.findEarlestApiBaseByProjectId(project.getId());
    // 如果为空则创建默认
    if (null == firstApibase) {
      firstApibase = apibaseService.createDefaultApiBase(project.getId(), project.getPackages());
    }
    // 更新状态字段-active
    for (TablePO table : tables) {
      // 判断是否已激活
      if (Constants.IS_ACTIVE.equals(table.getIsAutoCrud())) {
        throw new CodeCommonException(table.getName().concat("自动生成crud设置已被激活"));
      }
      table.setIsAutoCrud(Constants.IS_ACTIVE);
      String className = datasource.getPackageName().concat(".").concat(table.getClassName());
      // 获取transfer obj
      TransferObjPO showdto = transobjService.findTransferObjByTableId(table.getId());
      // 保存api obj
      apiobjService.createAutoCrudApi(firstApibase.getId(), table.getId(),
          table.getName().toLowerCase(), table.getComments(),
          showdto.getPackageName().concat(".").concat(showdto.getName()), className,
          datasource.getName().toLowerCase(), dbcount, null);
      // 找到关联关系
      List<TableRelationPO> relations =
          tableRelationRepository.findByMasterTableIdOrderByCreatedAtAsc(table.getId());
      for (TableRelationPO relation : relations) {
        // 创建主从关系crud api
        apiobjService.createAutoCruApiForRelation(firstApibase.getId(), relation,
            showdto.getPackageName().concat(".").concat(showdto.getName()),
            datasource.getName().toLowerCase(), dbcount);
      }
    }
    // 保存tables
    this.saveNewTables(tables);
  }

  @Override
  @Transactional("jpaTransactionManager")
  public void inactiveIsAutoCrud(RequestOfTableIdsDTO tableIds) {
    // 获取table
    List<TablePO> tables = tableRepository.findByIdIn(tableIds.getIds());
    // 更新状态字段-inactive
    for (TablePO table : tables) {
      // 判断是否未激活
      if (Constants.IS_INACTIVE.equals(table.getIsAutoCrud())) {
        throw new CodeCommonException(table.getName().concat("自动生成crud设置为未激活"));
      }
      table.setIsAutoCrud(Constants.IS_INACTIVE);
      // 删除api
      apiobjService.deleteAutoCrudApi(table.getId());
    }
    // 保存tables
    this.saveNewTables(tables);
  }

  @Override
  public TransferObjPO transColumnPOToTransPO(String tableId) {
    // 获取表信息
    TablePO table = tableRepository.findOne(tableId);
    // 获取字段信息
    List<ColumnPO> columns = this.findMergedColumns(tableId);
    // 创建临时实体
    TransferObjPO transobj = new TransferObjPO();
    transobj.setName(table.getClassName());
    transobj.setComments(table.getComments());
    // 创建临时实体属性列表
    List<TransferObjFieldPO> transfields = Lists.newArrayList();
    for (ColumnPO column : columns) {
      // 创建临时实体属性
      TransferObjFieldPO transfield = new TransferObjFieldPO();
      // 设置属性
      transfield.setName(column.getJavaField());
      transfield.setDescription(column.getComments());
      transfield.setType(BaseType.BASE.toString().toLowerCase());
      transfield.setFormat(column.getJavaType());
      transfields.add(transfield);
    }
    // 设置属性列表
    transobj.setTransferObjField(transfields);
    return transobj;
  }

  @Override
  public void updateTable(String id, TablePO table) {
    TablePO currentTable = tableRepository.findOne(id);
    if (null != currentTable) {
      tableRepository.save(currentTable.updateAttrs(table));
    } else {
      throw new CodeCommonException("数据库查无此数据");
    }
  }

  /**
   * 生成实体代码
   */
  @Override
  public String generateEntityCodeFiles(String tableId) {
    // 通过tableId 获取 table对象
    TablePO table = tableRepository.findOne(tableId);
    // 获得数据源
    DatasourcePO datasource = datasourceService.findById(table.getDatasourceId());
    // 获取项目
    ProjectPO project = projectRepo.findOne(datasource.getProjectId());
    // 获取实体表名
    List<ColumnPO> columns = this.findMergedColumns(tableId);
    // 生成实体文件代码
    generateService.generateEntityFiles(datasource.getPackageName(), project.getName(),
        project.getPackages().toLowerCase(), table, columns);
    return "/codegen/api/v1/" + table.getId() + "/download";
  }

  /**
   * 下载entity文件
   */
  @Override
  public File downLoadFile(String tableId) {
    // 通过tableId 获取 table对象
    TablePO table = tableRepository.findOne(tableId);
    // 获得数据源
    DatasourcePO datasource = datasourceService.findById(table.getDatasourceId());
    // 获取项目
    ProjectPO project = projectRepo.findOne(datasource.getProjectId());
    // 获取实体表名
    List<ColumnPO> columns = this.findMergedColumns(tableId);
    // 生成实体文件代码
    generateService.generateEntityFiles(datasource.getPackageName(), project.getName(),
        project.getPackages().toLowerCase(), table, columns);
    // 设置项目下的文件路径
    String projectZipPath = GeneratorUtils.getEntityPath(
        genProperties.getProjectPath() + project.getName() + "/src/main/java/"
            + project.getPackages().replace(".", "/") + "/entity/" + datasource.getName(),
        table.getClassName());
    return new File(projectZipPath);
  }

  /**
   * 通过数据源id查询表
   */
  @Override
  public List<TablePO> findByDatasourceId(String datasourceId) {
    // 通过数据源id查询表
    List<TablePO> tables = tableRepository.findByDatasourceId(datasourceId);
    return tables;
  }

  /**
   * 新增restFul关联表
   * 
   * @param tableRelation
   */
  @Override
  @Transactional("jpaTransactionManager")
  public TableRelationPO saveTableRelation(TableRelationPO tableRelation) {
    TableRelationPO trp = tableRelationRepository.save(tableRelation);
    TablePO stp = tableRepository.findOne(trp.getSlaveTableId());
    DatasourcePO datasource = datasourceService.findById(stp.getDatasourceId());
    TablePO mtp = tableRepository.findOne(trp.getMasterTableId());
    trp.setMasterTableName(mtp.getName());
    trp.setSlaveTableName(stp.getName());
    trp.setMasterClassName(datasource.getPackageName().concat(".").concat(mtp.getClassName()));
    trp.setSlaveClassName(datasource.getPackageName().concat(".").concat(stp.getClassName()));
    // 获取从表dto
    TransferObjPO dto = transobjService.findTransferObjByTableId(stp.getId());
    // 创建主从表crud
    // 获得该项目datasource的数目
    Long dbcount = datasourceService.countByProjectId(datasource.getProjectId());
    // 获取最早版本的api base
    ApiBasePO firstApibase =
        apibaseService.findEarlestApiBaseByProjectId(datasource.getProjectId());
    // 如果为空则创建默认
    if (null == firstApibase) {
      throw new CodeCommonException("没有找到API版本, 请先创建Api版本");
    }
    // 创建主从关系crud api
    apiobjService.createAutoCruApiForRelation(firstApibase.getId(), tableRelation,
        dto.getPackageName().concat(".").concat(dto.getName()), datasource.getName().toLowerCase(),
        dbcount);
    return trp;
  }

  /**
   * 删除该表restFul关联关系
   * 
   * @param masterTableId
   * @param slaveTableId
   */
  @Override
  @Transactional("jpaTransactionManager")
  public void deletTableRelation(String id) {
    TableRelationPO relation = tableRelationRepository.findOne(id);
    if (null != relation) {
      // 删除主从关系api
      apiobjService.deleteRelationCrudApi(relation.getMasterTableId(), relation.getSlaveTableId());
    }
    tableRelationRepository.delete(id);
  }

  /**
   * 查询该表所有restFul关联表
   * 
   * @param masterTableId
   * @return
   */
  @Override
  public List<TableRelationPO> findTableRelationList(String masterTableId) {
    List<TableRelationPO> result =
        tableRelationRepository.findByMasterTableIdOrderByCreatedAtAsc(masterTableId);
    // 获取从表名称
    for (TableRelationPO tableRelationPO : result) {
      TablePO tp = tableRepository.findOne(tableRelationPO.getSlaveTableId());
      tableRelationPO.setSlaveTableName(tp.getName());
    }
    return result;
  }

  /**
   * 查询从表表所有restFul关联表
   * 
   * @param slaveTableId
   * @return
   */
  @Override
  public List<TableRelationPO> findSlaveTableRelationList(String slaveTableId) {
    List<TableRelationPO> result =
        tableRelationRepository.findBySlaveTableIdOrderByCreatedAtAsc(slaveTableId);
    // 获取主表表名称
    for (TableRelationPO tableRelationPO : result) {
      TablePO tp = tableRepository.findOne(tableRelationPO.getMasterTableId());
      tableRelationPO.setMasterTableName(tp.getName());
    }
    return result;
  }

  /**
   * 高级关联保存
   */
  @Override
  @Transactional("jpaTransactionManager")
  public TableSeniorRelationPO saveTableSeniorRelation(TableSeniorRelationPO tableSeniorRelation) {
    if (StringUtils.isEmpty(tableSeniorRelation.getId())) {
      TableSeniorRelationPO tsr = seniorRelationRepository.save(tableSeniorRelation);
      tableSeniorRelation.setId(tsr.getId());
    } else {
      // 获取该关系所有从表
      List<TableSeniorSlavePO> sspList =
          tableSeniorSlaveRepository.findBySeniorIdOrderByCreatedAtAsc(tableSeniorRelation.getId());
      for (TableSeniorSlavePO ssp : sspList) {
        // 获取从表下 所有关联字段
        List<TableSeniorColumnPO> cpList =
            tableSeniorColumnRepository.findBySeniorSlaveIdOrderByCreatedAtAsc(ssp.getId());
        for (TableSeniorColumnPO po : cpList) {
          tableSeniorColumnRepository.delete(po.getId());
        }
        tableSeniorSlaveRepository.delete(ssp.getId());
      }
    }
    for (TableSeniorSlavePO ssp : tableSeniorRelation.getRelationTables()) {
      ssp.setSeniorId(tableSeniorRelation.getId());
      TableSeniorSlavePO po = tableSeniorSlaveRepository.save(ssp);
      for (TableSeniorColumnPO cp : ssp.getRelationColumns()) {
        cp.setSeniorSlaveId(po.getId());
        tableSeniorColumnRepository.save(cp);
      }
    }

    // 更新dto和crud api
    this.updateSeniorDTOAndCrudApi(tableSeniorRelation.getMasterTableId());

    return null;
  }

  /**
   * 更新高级查询dto和crud api
   * 
   * @param tableId
   */
  private void updateSeniorDTOAndCrudApi(String tableId) {
    // 通过id获取datasource
    TablePO stp = tableRepository.findOne(tableId);
    DatasourcePO datasource = datasourceService.findById(stp.getDatasourceId());
    // 获得该项目datasource的数目
    Long dbcount = datasourceService.countByProjectId(datasource.getProjectId());
    // 获取最早版本的api base
    ApiBasePO firstApibase =
        apibaseService.findEarlestApiBaseByProjectId(datasource.getProjectId());
    // 获取所有的关联关系
    List<TableSeniorRelationPO> ssrList =
        seniorRelationRepository.findByMasterTableIdOrderByCreatedAtAsc(tableId);
    // 删除dto
    transobjService.deleteSeniorTransferObj(tableId);
    // 删除api
    apiobjService.deleteRelationCrudApi(tableId, Constants.API_SENIOR_TAG);
    if (!ssrList.isEmpty()) {
      // 获取关联关系id
      List<String> seniorIds = Lists.newArrayList();
      List<String> relationCounts = Lists.newArrayList();
      int i = 1;
      for (TableSeniorRelationPO relation : ssrList) {
        seniorIds.add(relation.getId());
        relationCounts.add(Constants.API_SENIOR_RELATION_INFIX + i);
        i++;
      }
      // 获取所有从表
      TableSeniorRelationPO tsr = ssrList.get(0);
      tsr.setRelationTables(
          tableSeniorSlaveRepository.findBySeniorIdInOrderByCreatedAtAsc(seniorIds));
      // 更新实体
      List<TransferObjPO> dtos = transobjService.updateAutoCrudSeniorDTO(datasource.getProjectId(),
          datasource.getPackageName(), tsr);
      // 关联实体
      String dtoName = dtos.get(0).getPackageName().concat(".").concat(dtos.get(0).getName());
      // 返回关联实体
      String rdtoName = dtos.get(1).getPackageName().concat(".").concat(dtos.get(1).getName());
      // 创建api
      apiobjService.createAutoCrudApi(firstApibase.getId(), tableId,
          stp.getName().concat("_senior"), stp.getComments(), rdtoName, dtoName,
          datasource.getName(), dbcount, relationCounts);
    }
  }

  /**
   * 获取高级关联所有sql
   */
  @Override
  public List<TableSeniorRelationPO> findTableSeniorRelationSqlList(String masterTableId) {

    // 获取该表所有高级关系
    List<TableSeniorRelationPO> tsrList =
        seniorRelationRepository.findByMasterTableIdOrderByCreatedAtAsc(masterTableId);
    for (TableSeniorRelationPO tsr : tsrList) {
      String sql = "select * from <b>" + tsr.getMasterTableName() + "</b>";
      // 获取该关系所有从表
      List<TableSeniorSlavePO> sspList =
          tableSeniorSlaveRepository.findBySeniorIdOrderByCreatedAtAsc(tsr.getId());
      for (TableSeniorSlavePO ssp : sspList) {
        sql += " <br>" + ssp.getRelation() + " <b>" + ssp.getSlaveTableName() + "</b>";
        sql += " on" + "<br>";
        // 获取从表下 所有关联字段
        List<TableSeniorColumnPO> cpList =
            tableSeniorColumnRepository.findBySeniorSlaveIdOrderByCreatedAtAsc(ssp.getId());
        for (TableSeniorColumnPO po : cpList) {
          sql += " <b>&nbsp;&nbsp;" + tsr.getMasterTableName() + "." + po.getMasterColumnName()
              + "</b>" + po.getOperate() + "<b>" + ssp.getSlaveTableName() + "."
              + po.getSlaveColumnName() + "</b>";
        }
      }
      tsr.setSql(sql);
    }
    return tsrList;
  }

  /**
   * 删除高级关联关系
   */
  @Override
  @Transactional("jpaTransactionManager")
  public void deletTableSeniorRelation(String id) {
    // 获取该表所有高级关系
    TableSeniorRelationPO tsr = seniorRelationRepository.findOne(id);
    // 获取该关系所有从表
    List<TableSeniorSlavePO> sspList =
        tableSeniorSlaveRepository.findBySeniorIdOrderByCreatedAtAsc(tsr.getId());
    for (TableSeniorSlavePO ssp : sspList) {
      // 获取从表下 所有关联字段
      List<TableSeniorColumnPO> cpList =
          tableSeniorColumnRepository.findBySeniorSlaveIdOrderByCreatedAtAsc(ssp.getId());
      for (TableSeniorColumnPO po : cpList) {
        tableSeniorColumnRepository.delete(po.getId());
      }
      tableSeniorSlaveRepository.delete(ssp.getId());
    }
    seniorRelationRepository.delete(id);

    // 更新dto和crud api
    this.updateSeniorDTOAndCrudApi(tsr.getMasterTableId());
  }

  /**
   * 根据id获取指定高级关联关系表
   * 
   * @param id
   * @return
   */
  @Override
  public TableSeniorRelationPO findOnetableSeniorRelation(String id) {
    // 获取该表所有高级关系
    TableSeniorRelationPO tsr = seniorRelationRepository.findOne(id);
    // 获取该关系所有从表
    List<TableSeniorSlavePO> sspList =
        tableSeniorSlaveRepository.findBySeniorIdOrderByCreatedAtAsc(tsr.getId());
    for (TableSeniorSlavePO ssp : sspList) {
      // 获取从表下 所有关联字段
      List<TableSeniorColumnPO> cpList =
          tableSeniorColumnRepository.findBySeniorSlaveIdOrderByCreatedAtAsc(ssp.getId());
      ssp.setRelationColumns(cpList);
    }
    tsr.setRelationTables(sspList);
    return tsr;
  }

  @Override
  public List<String> findIdByDatasourceIdIn(List<String> datasourceIds) {
    return tableRepository.findIdByDatasourceIdIn(datasourceIds);
  }

  @Override
  public Long deleteByDatasourceIdIn(List<String> datasourceIds) {
    return tableRepository.deleteByDatasourceIdIn(datasourceIds);
  }

  /**
   * 是否启用表
   */
  @Override
  public Boolean isDictionary(String tableId) {
    //是否启用字典表
    Boolean dictFlag = false;
    // 获取表信息
    TablePO table = tableRepository.findOne(tableId);
    // 获取对应的数据源
    DatasourcePO datasource = datasourceService.findById(table.getDatasourceId());
    // 获取项目的信息
    ProjectPO project = projectRepo.findOne(datasource.getProjectId());
    //判断是否启用字符串
    for (String str : project.getComponents().split(",")){
      if(Dictionary.dictionary.toString().equals(str)){
        dictFlag = true;
      }else{
        dictFlag = false;
      }
    }
    return dictFlag;
  }
  
  /**
   * 创建dictType表
   * @param datasource
   */
  @Override
  @ChangeDatasource
  public void creatDictType(DatasourcePO datasource){
    databaseDao.creatDictType();
  }
  
  /**
   * 创建dictValue表
   * @param datasource
   */
  @Override
  @ChangeDatasource
  public void creatDictValue(DatasourcePO datasource){
    databaseDao.creatDictValue();
  }
}
