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

import com.changan.anywhere.common.datasource.annotation.ChangeDatasource;
import com.changan.anywhere.common.mvc.page.rest.request.Filter;
import com.changan.anywhere.common.mvc.page.rest.request.PageDTO;
import com.changan.code.common.Constants;
import com.changan.code.common.DtoType;
import com.changan.code.common.PredictUtils;
import com.changan.code.dao.DatabaseDao;
import com.changan.code.dto.RequestOfTableIdsDTO;
import com.changan.code.dto.SimpleDataObj;
import com.changan.code.entity.ApiBasePO;
import com.changan.code.entity.ColumnPO;
import com.changan.code.entity.DatasourcePO;
import com.changan.code.entity.ProjectPO;
import com.changan.code.entity.TablePO;
import com.changan.code.entity.TransferObjFieldPO;
import com.changan.code.entity.TransferObjPO;
import com.changan.code.exception.CodeCommonException;
import com.changan.code.repository.TableRepository;
import com.changan.code.service.IApiBaseService;
import com.changan.code.service.IApiObjService;
import com.changan.code.service.IColumnService;
import com.changan.code.service.IDatasourceService;
import com.changan.code.service.IProjectService;
import com.changan.code.service.ITableService;
import com.changan.code.service.ITransferObjService;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author wenxing
 *
 */
@Service
public class TableServiceImpl implements ITableService {

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

  /**
   * 更新数据库表
   * 
   * @param originTables
   * @param masterTables
   */
  @Override
  @Transactional("jpaTransactionManager")
  public void saveAndDelMasterTables(List<TablePO> originTables, List<TablePO> masterTables,
      String datasourceId) {
    // 列出新的表
    List<TablePO> newTables = Lists.newArrayList();
    // 列出删除的表
    List<TablePO> notExistTables = Lists.newArrayList();
    if (null == masterTables || masterTables.isEmpty()) {
      for (TablePO originTable : originTables) {
        newTables.add(originTable.setDefaultValue(datasourceId));
      }
    } else {
      // master的table放入map中
      Map<String, TablePO> masterTableMap = Maps.newHashMap();
      for (TablePO masterTable : masterTables) {
        masterTableMap.put(masterTable.getName(), masterTable);
      }
      // 识别新增表
      for (TablePO originTable : originTables) {
        if (!masterTableMap.containsKey(originTable.getName())) {
          newTables.add(originTable.setDefaultValue(datasourceId));
        } else {
          masterTableMap.remove(originTable.getName());
        }
      }
      // 得到删除表
      for (Entry<String, TablePO> entry : masterTableMap.entrySet()) {
        notExistTables.add(entry.getValue());
      }
    }

    // 新建表
    this.saveNewTables(newTables);
    // 删除表
    this.deleteNotExistTables(notExistTables);
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
      SimpleDataObj dataobj = new SimpleDataObj((String) data[0], (String) data[1], Constants.IS_INACTIVE, null);
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
      // 保存transfer obj
      TransferObjPO showdto = transobjService.createAutoCrudDTO(project.getId(), table.getId(), table.getName(),
          datasource.getPackageName(), className);
      // 保存api obj
      apiobjService.createAutoCrudApi(firstApibase.getId(), table.getId(), table.getName().toLowerCase(),
          showdto.getPackageName().concat(".").concat(showdto.getName()), className,
          datasource.getPackageName().toLowerCase(), datasource.getName().toLowerCase(), dbcount);
    }
    // 保存tables
    tableRepository.save(tables);
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
      // 删除transobj
      transobjService.deleteAutoCrudDTO(table.getId());
    }
    // 保存tables
    tableRepository.save(tables);
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
      transfield.setType(DtoType.BASE.toString().toLowerCase());
      transfield.setFormat(column.getJavaType());
      transfields.add(transfield);
    }
    // 设置属性列表
    transobj.setTransferObjField(transfields);
    return transobj;
  }

}
