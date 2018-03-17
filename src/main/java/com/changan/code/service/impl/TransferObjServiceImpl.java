package com.changan.code.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.changan.code.common.BaseDTO;
import com.changan.code.common.BaseFormat;
import com.changan.code.common.BaseType;
import com.changan.code.common.Constants;
import com.changan.code.dto.SimpleDataObj;
import com.changan.code.entity.TableSeniorRelationPO;
import com.changan.code.entity.TableSeniorSlavePO;
import com.changan.code.entity.TransferObjFieldPO;
import com.changan.code.entity.TransferObjPO;
import com.changan.code.exception.CodeCommonException;
import com.changan.code.repository.TransferObjFieldRespository;
import com.changan.code.repository.TransferObjRespository;
import com.changan.code.service.ITransferObjFieldService;
import com.changan.code.service.ITransferObjService;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author xuyufeng
 *
 */
@Service
@Slf4j
public class TransferObjServiceImpl implements ITransferObjService {

  private static Map<String, TransferObjPO> defaultDtoMaps = Maps.newHashMap();
  private static List<String> defaultDtoNames = Lists.newArrayList();

  /**
   * 注入TransferObj Respository对象
   */
  @Autowired
  private TransferObjRespository transferObjRePo;

  /**
   * 注入TransferObjField Respository对象
   */
  @Autowired
  private TransferObjFieldRespository transferObjFieldRePo;

  /**
   * 注入DTO属性接口
   */
  @Autowired
  private ITransferObjFieldService transferObjFieldService;

  /**
   * 更新DTO
   */
  @Override
  public TransferObjPO updateTransferObj(TransferObjPO transferObj) {
    // 当前DTO
    TransferObjPO updateTransferObj =
        transferObjRePo.findByIdAndDelFlag(transferObj.getId(), Constants.DATA_IS_NORMAL);
    if (updateTransferObj != null) {
      // 更新属性
      updateTransferObj.updateAttrs(transferObj);
      // 根据name、projectId查询DTO
      List<TransferObjPO> transferObjs = transferObjRePo.findByNameAndProjectIdAndDelFlag(
          updateTransferObj.getName(), updateTransferObj.getProjectId(), Constants.DATA_IS_NORMAL);
      // 若长度为0则直接更新、若长度为1则判断id是否相同，若不相同则保存失败
      if (transferObjs.isEmpty()) {
        // 保存更新
        return transferObjRePo.save(updateTransferObj);
      } else {
        if (transferObjs.get(0).getId().equals(updateTransferObj.getId())) {
          // 保存更新
          return transferObjRePo.save(updateTransferObj);
        } else {
          throw new CodeCommonException("更新失败！参数名重复！");
        }
      }
    } else {
      throw new CodeCommonException("更新失败！数据不存在！");
    }
  }

  /**
   * 保存DTO，相同projectId下 name不能相同
   */
  @Override
  @Transactional("jpaTransactionManager")
  public TransferObjPO saveTransferObj(TransferObjPO transferObj) {
    // 查询数据库中重复条数
    int sameCount = transferObjRePo.countByNameAndProjectIdAndDelFlag(transferObj.getName(),
        transferObj.getProjectId(), Constants.DATA_IS_NORMAL);
    // 若重复条数为0
    if (sameCount == 0) {
      // 保存TransferObj
      return transferObjRePo.save(transferObj);
    } else {
      throw new CodeCommonException("保存失败，DTO名称重复！");
    }
  }

  /**
   * 删除DTO
   */
  @Override
  @Transactional("jpaTransactionManager")
  public void deleteTransferObj(String id) {
    // 获取需要删除的TransferObj对象
    TransferObjPO deleteTransferObj =
        transferObjRePo.findByIdAndDelFlag(id, Constants.DATA_IS_NORMAL);
    if (deleteTransferObj != null) {
      // 进行逻辑删除
      deleteTransferObj.setDelFlag("1");
      // 保存数据
      transferObjRePo.save(deleteTransferObj);
      // 获取需要删除的TransferObjField对象
      List<TransferObjFieldPO> deleteTransferObjField = transferObjFieldRePo
          .findByTransferObjIdAndDelFlag(deleteTransferObj.getId(), Constants.DATA_IS_NORMAL);
      if (deleteTransferObjField != null && !deleteTransferObjField.isEmpty()) {
        for (TransferObjFieldPO transferObjField : deleteTransferObjField) {
          // 进行逻辑删除
          transferObjField.setDelFlag("1");
        }
        // 保存逻辑删除操作
        transferObjFieldRePo.save(deleteTransferObjField);
      }
    } else {
      throw new CodeCommonException("删除失败！执行删除的数据不存在！");
    }
  }

  /**
   * 通过id查询DTO
   */
  @Override
  public TransferObjPO findTransferObjById(String id) {
    if (this.checkIfIsDefaultDto(id)) {
      return this.getDefaultDtoByName(id);
    }
    // 获取TransferObj
    TransferObjPO transferObj = transferObjRePo.findByIdAndDelFlag(id, Constants.DATA_IS_NORMAL);
    // 获取TransferObjField
    List<TransferObjFieldPO> transferObjField =
        transferObjFieldRePo.findByTransferObjIdAndDelFlag(id, Constants.DATA_IS_NORMAL);
    if (transferObj != null) {
      // 设置返回对象值
      transferObj.setTransferObjField(transferObjField);
    }
    // 返回查询到的TransferObj对象
    return transferObj;
  }

  /**
   * 查询所有DTO
   */
  @Override
  public List<TransferObjPO> findAllTransferObj(String projectId) {
    // 获取查询结果
    return transferObjRePo.findByProjectIdAndDelFlag(projectId, Constants.DATA_IS_NORMAL);
  }

  @Override
  public Map<String, List<SimpleDataObj>> findClassnameByProjectId(String projectId) {
    List<Object[]> results = transferObjRePo.findClassNameByProjectId(projectId);
    Map<String, List<SimpleDataObj>> datamaps = Maps.newHashMap();
    for (Object[] data : results) {
      // 生成新对象
      SimpleDataObj dataobj = new SimpleDataObj((String) data[0], (String) data[1],
          (String) data[3], (String) data[2], (String) data[4]);
      // 根据packagename获取list
      List<SimpleDataObj> objLists = datamaps.get((String) data[2]);
      if (null != objLists) {
        objLists.add(dataobj);
      } else {
        objLists = Lists.newArrayList();
        objLists.add(dataobj);
        datamaps.put((String) data[2], objLists);
      }
    }
    return datamaps;
  }

  /**
   * 自动创建实体
   */
  @Override
  public TransferObjPO createAutoCrudDTO(String projectId, String tableId, String tableName,
      String datasourcePName, String className) {
    String dtoName = "ResultOf"
        .concat(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName)).concat("DTO");
    // 检查dto是否存在
    List<TransferObjPO> showPOs = transferObjRePo.findByNameAndPackageNameAndProjectIdAndDelFlag(
        dtoName, datasourcePName, projectId, Constants.DATA_IS_NORMAL);
    TransferObjPO showPO;
    if (showPOs.isEmpty()) {
      // 详情实体
      showPO = this.genTransferObjPO(projectId, tableId, dtoName, datasourcePName,
          className.concat("详情实体"));
      // 保存详情实体
      showPO = transferObjRePo.save(showPO);
      // 实体属性
      TransferObjFieldPO showPOField = this.genTransferObjFieldPO(showPO.getId(), tableName, "",
          BaseType.PO, className, tableName.concat("对象"));
      showPOField.setSort(0);
      transferObjFieldRePo.save(showPOField);
    } else {
      showPO = showPOs.get(0);
    }
    return showPO;
  }

  /**
   * 创建实体对象
   * 
   * @param postfix
   * @param packageName
   * @return
   */
  private TransferObjPO genTransferObjPO(String projectId, String tableId, String dtoName,
      String packageName, String comments) {
    TransferObjPO po = new TransferObjPO();
    po.setProjectId(projectId);
    po.setName(dtoName);
    po.setPackageName(packageName);
    po.setComments(comments);
    po.setIsGeneric(Constants.IS_INACTIVE);
    po.setInheritObjName(BaseDTO.ResultDTO.toString());
    po.setGenBasedTableId(tableId);

    return po;
  }

  /**
   * 创建实体属性对象
   * 
   * @param transferObjId
   * @param tableName
   * @param postfix
   * @param type
   * @param format
   * @param description
   * @return
   */
  private TransferObjFieldPO genTransferObjFieldPO(String transferObjId, String tableName,
      String postfix, BaseType type, String format, String description) {
    TransferObjFieldPO po = new TransferObjFieldPO();
    po.setName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName).concat(postfix));
    po.setType(type.toString().toLowerCase());
    po.setFormat(format);
    po.setTransferObjId(transferObjId);
    po.setDescription(description);
    return po;
  }

  @Override
  public void deleteAutoCrudDTO(String tableId) {
    // 获取transferObj的id
    List<String> transobjIds = transferObjRePo.findIdByGenBasedTableId(tableId);
    if (null != transobjIds) {
      for (String id : transobjIds) {
        // 删除field
        transferObjFieldService.deleteByTransferObjId(id);
      }
    }
    // 删除transferObj
    transferObjRePo.deleteByGenBasedTableId(tableId);
  }

  /**
   * 通过名称检查是否是默认dto
   */
  @Override
  public boolean checkIfIsDefaultDto(String name) {
    if (defaultDtoNames.contains(name)) {
      return true;
    }
    return false;
  }

  @Override
  public TransferObjPO getDefaultDtoByName(String name) {
    return defaultDtoMaps.get(name);
  }

  // 初始化默认提供的dto
  @PostConstruct
  private void generateDefaultDtoMaps() {
    log.info(">>>> 初始化基础实体");
    // 创建临时实体 - pagedto
    TransferObjPO pagedto = new TransferObjPO();
    pagedto.setName(BaseDTO.PageDTO.name());
    pagedto.setComments(BaseDTO.PageDTO.getComments());
    pagedto.setGenBasedTableId("default");
    // 实体属性 - pagedto
    List<TransferObjFieldPO> pagedtofields = Lists.newArrayList();
    pagedtofields.add(new TransferObjFieldPO(BaseDTO.PageDTO.name(), "collection",
        BaseType.DTO.name().toLowerCase(), "Collection", "过滤参数集合实体"));
    pagedtofields.add(new TransferObjFieldPO(BaseDTO.PageDTO.name(), "pageParms",
        BaseType.DTO.name().toLowerCase(), "PageParms", "分页参数实体"));
    pagedtofields.add(new TransferObjFieldPO(BaseDTO.PageDTO.name(), "orders",
        BaseType.ARRAY.name().toLowerCase(), "Order", "排序对象集合"));
    pagedto.setTransferObjField(pagedtofields);
    defaultDtoNames.add(BaseDTO.PageDTO.name());
    defaultDtoMaps.put(BaseDTO.PageDTO.name(), pagedto);
    // 创建临时实体 - resultdto
    TransferObjPO resultdto = new TransferObjPO();
    resultdto.setName(BaseDTO.ResultDTO.name());
    resultdto.setComments(BaseDTO.ResultDTO.getComments());
    resultdto.setGenBasedTableId("default");
    // 实体属性 - resultdto
    List<TransferObjFieldPO> resultdtofields = Lists.newArrayList();
    resultdtofields.add(new TransferObjFieldPO(BaseDTO.ResultDTO.name(), "statusCode",
        BaseType.BASE.name().toLowerCase(), BaseFormat.String.name(), "返回编码"));
    resultdtofields.add(new TransferObjFieldPO(BaseDTO.ResultDTO.name(), "message",
        BaseType.BASE.name().toLowerCase(), BaseFormat.String.name(), "返回信息"));
    resultdto.setTransferObjField(resultdtofields);
    defaultDtoNames.add(BaseDTO.ResultDTO.name());
    defaultDtoMaps.put(BaseDTO.ResultDTO.name(), resultdto);
    // 创建临时实体 - resultpagedto
    TransferObjPO resultpagedto = new TransferObjPO();
    resultpagedto.setName(BaseDTO.ResultPageDTO.name());
    resultpagedto.setComments(BaseDTO.ResultPageDTO.getComments());
    resultpagedto.setGenBasedTableId("default");
    // 实体属性 - resultpagedto
    List<TransferObjFieldPO> resultpagedtofields = Lists.newArrayList();
    resultpagedtofields.add(new TransferObjFieldPO(BaseDTO.ResultPageDTO.name(), "pageSize",
        BaseType.BASE.name().toLowerCase(), BaseFormat.Long.name(), "每页大小"));
    resultpagedtofields.add(new TransferObjFieldPO(BaseDTO.ResultPageDTO.name(), "totalElements",
        BaseType.BASE.name().toLowerCase(), BaseFormat.Long.name(), "总数"));
    resultpagedtofields.add(new TransferObjFieldPO(BaseDTO.ResultPageDTO.name(), "pageNumber",
        BaseType.BASE.name().toLowerCase(), BaseFormat.Long.name(), "页数"));
    resultpagedtofields.add(new TransferObjFieldPO(BaseDTO.ResultPageDTO.name(), "data",
        BaseType.ARRAY.name().toLowerCase(), "T", "数据(泛型)"));
    resultpagedto.setTransferObjField(resultpagedtofields);
    defaultDtoNames.add(BaseDTO.ResultPageDTO.name());
    defaultDtoMaps.put(BaseDTO.ResultPageDTO.name(), resultpagedto);
    // 创建临时实体 - resultschemadto
    TransferObjPO resultschemadto = new TransferObjPO();
    resultschemadto.setName(BaseDTO.ResultJsonSchemaDTO.name());
    resultschemadto.setComments(BaseDTO.ResultJsonSchemaDTO.getComments());
    resultschemadto.setGenBasedTableId("default");
    // 实体属性 - resultschemadto
    List<TransferObjFieldPO> resultschemadtofields = Lists.newArrayList();
    resultschemadtofields.add(new TransferObjFieldPO(BaseDTO.ResultJsonSchemaDTO.name(),
        "jsonSchema", BaseType.DTO.name().toLowerCase(), "JsonSchema", "jsonSchema实体"));
    resultschemadto.setTransferObjField(resultschemadtofields);
    defaultDtoNames.add(BaseDTO.ResultJsonSchemaDTO.name());
    defaultDtoMaps.put(BaseDTO.ResultJsonSchemaDTO.name(), resultschemadto);
  }

  /**
   * 不包含高级关联
   */
  @Override
  public TransferObjPO findTransferObjByTableId(String tableId) {
    List<TransferObjPO> dtos = transferObjRePo.findByGenBasedTableIdAndIsSeniorNot(tableId, "1");
    if (dtos.isEmpty()) {
      throw new CodeCommonException("未找到DTO: table id -> ".concat(tableId));
    }
    return dtos.get(0);
  }
  
  /**
   * 包含高级关联
   * @param tableId
   * @return
   */
  @Override
  public List<TransferObjPO> findAllTransferObjByTableId(String tableId) {
    List<TransferObjPO> dtos = transferObjRePo.findByGenBasedTableId(tableId);
    if (dtos.isEmpty()) {
      throw new CodeCommonException("未找到DTO: table id -> ".concat(tableId));
    }
    return dtos;
  }


  /**
   * 创建高级查询dto
   * 
   * @param projectId
   * @param tableId
   * @param tableName
   * @param relation
   * @return
   */
  @Override
  public List<TransferObjPO> updateAutoCrudSeniorDTO(String projectId, String datasourcePname,
      TableSeniorRelationPO relation) {
    // 创建的实体列表
    List<TransferObjPO> dtos = Lists.newArrayList();
    // 引入的表名
    List<String> tableNames = Lists.newArrayList();
    // 放入主表名
    tableNames.add(relation.getMasterTableName());
    // 放入从表名
    for (TableSeniorSlavePO slaveTable : relation.getRelationTables()) {
      if (!tableNames.contains(slaveTable.getSlaveTableName())) {
        tableNames.add(slaveTable.getSlaveTableName());
      }
    }
    // 主表名驼峰大写
    String masterTableNameCap = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,
        relation.getMasterTableName().toLowerCase());
    // 创建关联实体
    TransferObjPO po = new TransferObjPO();
    po.setProjectId(projectId);
    po.setName(masterTableNameCap.concat("SeniorDTO"));
    po.setPackageName(datasourcePname);
    po.setComments(datasourcePname.concat(".").concat(masterTableNameCap).concat("PO高级关联查询"));
    po.setIsGeneric(Constants.IS_INACTIVE);
    po.setGenBasedTableId(relation.getMasterTableId());
    po.setIsSenior(Constants.IS_ACTIVE);
    // 保存关联实体
    po = transferObjRePo.save(po);
    // 创建关联实体字段
    int i = 0;
    for (String tableName : tableNames) {
      TransferObjFieldPO fpo = new TransferObjFieldPO();
      fpo.setName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName.toLowerCase()));
      fpo.setType("po");
      fpo.setFormat(datasourcePname.concat(".")
          .concat(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase()))
          .concat("PO"));
      fpo.setTransferObjId(po.getId());
      fpo.setDescription(tableName.concat("对象"));
      fpo.setSort(i);
      // 保存实体字段
      transferObjFieldRePo.save(fpo);
      i++;
    }
    // 添加到返回值中
    dtos.add(po);
    // 创建关联实体
    TransferObjPO dto = new TransferObjPO();
    dto.setProjectId(projectId);
    dto.setName("ResultOf".concat(masterTableNameCap).concat("SeniorDTO"));
    dto.setPackageName(datasourcePname);
    dto.setInheritObjName(BaseDTO.ResultDTO.toString());
    dto.setComments(datasourcePname.concat(".").concat(masterTableNameCap).concat("PO高级关联查询返回实体"));
    dto.setIsGeneric(Constants.IS_INACTIVE);
    dto.setGenBasedTableId(relation.getMasterTableId());
    dto.setIsSenior(Constants.IS_ACTIVE);
    // 保存返回关联实体
    dto = transferObjRePo.save(dto);
    // 创建返回关联实体字段
    TransferObjFieldPO fpo = new TransferObjFieldPO();
    fpo.setName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,
        relation.getMasterTableName().concat("_senior").toLowerCase()));
    fpo.setType("dto");
    fpo.setFormat(datasourcePname.concat(".").concat(po.getName()));
    fpo.setTransferObjId(dto.getId());
    fpo.setDescription(relation.getMasterTableName().concat("高级关联查询对象"));
    // 保存返回关联实体字段
    transferObjFieldRePo.save(fpo);
    dtos.add(dto);
    return dtos;
  }

  /**
   * 根据table id获取高级关联DTO
   * 
   * @param id
   * @return
   */
  @Override
  public List<TransferObjPO> findSeniorTransferObjByTableId(String tableId) {
    List<TransferObjPO> dtos = transferObjRePo.findByGenBasedTableIdAndIsSeniorAndDelFlag(tableId,
        Constants.IS_ACTIVE, Constants.DATA_IS_NORMAL);
    return dtos;
  }

  /**
   * 删除高级关联DTO
   * 
   * @param transferObj
   */
  @Override
  public void deleteSeniorTransferObj(String tableId) {
    // 获取高级关联查询的transferObj的id
    List<String> transobjIds = transferObjRePo.findSeniorIdByGenBasedTableId(tableId);
    if (null != transobjIds) {
      for (String id : transobjIds) {
        // 删除field
        transferObjFieldService.deleteByTransferObjId(id);
      }
    }
    transferObjRePo.deleteByGenBasedTableIdAndIsSenior(tableId, Constants.IS_ACTIVE);
  }

  @Override
  public List<String> findIdByGenBasedTableIdIn(List<String> genBasedTableIds) {
    return transferObjRePo.findIdByGenBasedTableIdIn(genBasedTableIds);
  }

  @Override
  public Long deleteByGenBasedTableIdIn(List<String> genBasedTableIds) {
    return transferObjRePo.deleteByGenBasedTableIdIn(genBasedTableIds);
  }

}
