package com.changan.code.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.changan.code.common.BaseDTO;
import com.changan.code.common.Constants;
import com.changan.code.common.DtoType;
import com.changan.code.dto.SimpleDataObj;
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

/**
 * 
 * @author xuyufeng
 *
 */
@Service
public class TransferObjServiceImpl implements ITransferObjService {

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
      // 更新数据库
      updateTransferObj = this.saveTransferObj(updateTransferObj);
      return updateTransferObj;
    } else {
      throw new CodeCommonException("更新失败！");
    }
}
  /**
   * 保存DTO，相同projectId下 name不能相同
   */
  @Override
  @Transactional("jpaTransactionManager")
  public TransferObjPO saveTransferObj(TransferObjPO transferObj) {
    // 重复条数
    int sameCount = 0;
    // 查询数据库中重复条数
    sameCount = transferObjRePo.countByNameAndProjectIdAndDelFlag(transferObj.getName(),
        transferObj.getProjectId(), Constants.DATA_IS_NORMAL);
    // 若重复条数为0
    if (sameCount == 0) {
      // 保存TransferObj
      transferObj = transferObjRePo.save(transferObj);
      return transferObj;
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
    // 获取TransferObj
    TransferObjPO transferObj = transferObjRePo.findByIdAndDelFlag(id, Constants.DATA_IS_NORMAL);
    // 获取TransferObjField
    List<TransferObjFieldPO> transferObjField =
        transferObjFieldRePo.findByTransferObjIdAndDelFlag(id, Constants.DATA_IS_NORMAL);
    // 设置返回对象值
    transferObj.setTransferObjField(transferObjField);
    // 返回查询到的TransferObj对象
    return transferObj;
  }

  /**
   * 查询所有DTO
   */
  @Override
  public List<TransferObjPO> findAllTransferObj(String projectId) {
    // 获取查询结果
    List<TransferObjPO> allTransferObj =
        transferObjRePo.findByProjectIdAndDelFlag(projectId, Constants.DATA_IS_NORMAL);
    // 返回结果
    return allTransferObj;
  }

  @Override
  public Map<String, List<SimpleDataObj>> findClassnameByProjectId(String projectId) {
    List<Object[]> results = transferObjRePo.findClassNameByProjectId(projectId);
    Map<String, List<SimpleDataObj>> datamaps = Maps.newHashMap();
    for (Object[] data : results) {
      // 生成新对象
      SimpleDataObj dataobj = new SimpleDataObj((String) data[0], (String) data[1], Constants.IS_INACTIVE);
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
    // 详情实体
    TransferObjPO showPO =
        this.genTransferObjPO(projectId, tableId, tableName, "", datasourcePName, tableName.concat("详情实体"));
    // 保存详情实体
    showPO = transferObjRePo.save(showPO);
    // 实体属性
    TransferObjFieldPO showPOField = this.genTransferObjFieldPO(showPO.getId(), tableName, "",
        DtoType.ARRAY, className, tableName.concat("对象"));
    transferObjFieldRePo.save(showPOField);
    // 列表实体
    TransferObjPO listPO =
        this.genTransferObjPO(projectId, tableId, tableName, "s", datasourcePName, tableName.concat("列表实体"));
    // 保存列表实体
    listPO = transferObjRePo.save(listPO);
    // 实体属性
    TransferObjFieldPO listPOField = this.genTransferObjFieldPO(listPO.getId(), tableName, "s",
        DtoType.REFOBJ, className, tableName.concat("对象列表"));
    transferObjFieldRePo.save(listPOField);
    return showPO;
  }

  /**
   * 创建实体对象
   * 
   * @param postfix
   * @param packageName
   * @return
   */
  private TransferObjPO genTransferObjPO(String projectId, String tableId, String tableName,
      String postfix, String packageName, String comments) {
    TransferObjPO po = new TransferObjPO();
    po.setProjectId(projectId);
    po.setName("ResultOf".concat(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName))
        .concat(postfix).concat("DTO"));
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
      String postfix, DtoType type, String format, String description) {
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
}
