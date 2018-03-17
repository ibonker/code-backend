package com.changan.code.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.changan.anywhere.common.datasource.annotation.ChangeDatasource;
import com.changan.anywhere.common.mvc.page.rest.request.Collection;
import com.changan.anywhere.common.mvc.page.rest.request.Filter;
import com.changan.anywhere.common.mvc.page.rest.request.Operator;
import com.changan.anywhere.common.mvc.page.rest.request.PageDTO;
import com.changan.code.common.Constants;
import com.changan.code.common.PredictUtils;
import com.changan.code.dao.DatabaseDao;
import com.changan.code.dto.ResultDictDTO;
import com.changan.code.entity.DatasourcePO;
import com.changan.code.entity.DictTypePO;
import com.changan.code.entity.DictValuePO;
import com.changan.code.exception.CodeCommonException;
import com.changan.code.repository.DictTypeRepository;
import com.changan.code.repository.DictValueRepository;
import com.changan.code.service.IDictService;
import com.google.common.collect.Lists;



/**
 * DictService
 * 
 * @author xuyufeng
 *
 */
@Service
public class DictServiceImpl implements IDictService {

  @Autowired
  DictTypeRepository dictTypeRepo;

  @Autowired
  DictValueRepository dictValueRepo;

  @Autowired
  DatabaseDao databaseDao;

  /**
   * 查询DictType 和 DictValue
   */
  @Override
  public ResultDictDTO findAll(int version) {
    // 如果版本号相同则直接返回内存中的数据
    return new ResultDictDTO().DictIsUpdate("0");
  }

  /**
   * 更新DictType
   */
  @Override
  public DictTypePO updateDictType(String dictTypeId, DictTypePO updateDictType) {
    // 通过id查询
    DictTypePO dictType = this.findDictType(dictTypeId);
    // 根据code查询DictType，code不能重复，若code重复则新增失败
    DictTypePO selectDictType =
        dictTypeRepo.findByCodeAndDelFlag(updateDictType.getCode(), Constants.DATA_IS_NORMAL);
    if (dictType != null
        && (selectDictType == null || dictType.getId().equals(selectDictType.getId()))) {
      // 更新属性
      dictType.updateAttrs(updateDictType);
      // 更新dictType下的dictValue
      dictValueRepo.updateByDictCode(dictType.getCode(), Constants.DATA_IS_NORMAL);;
      // 更新数据库
      return dictTypeRepo.save(dictType);
    } else {
      throw new CodeCommonException("修改失败,需要修改的数据不存在或CODE已存在!");
    }
  }

  /**
   * 更新DictValue
   */
  @Override
  public DictValuePO updateDictValue(String dictValueId, DictValuePO updateDictValue) {
    // 根据id查询DictValue
    DictValuePO dictValue = this.findDictValue(dictValueId);
    if (dictValue != null) {
      // 更新属性
      dictValue.updateAttrs(updateDictValue);
      // 更新数据库
      return dictValueRepo.save(dictValue);
    } else {
      throw new CodeCommonException("需要修改的数据不存在！");
    }
  }

  /**
   * 逻辑删除DictType
   */
  @Override
  @Transactional("jpaTransactionManager")
  public void deleteDictType(String id) {
    // 如果需要删除的DictType存在
    DictTypePO dictType = this.findDictType(id);
    if (dictType != null) {
      // 设置删除标识符
      dictType.setDelFlag(Constants.DATA_IS_INVALID);
      // 删除DictType
      dictTypeRepo.save(dictType);
      List<DictValuePO> dictValues =
          dictValueRepo.findByDictCodeAndDelFlag(dictType.getCode(), Constants.DATA_IS_NORMAL);
      if (dictValues != null && !dictValues.isEmpty()) {
        // 根据DicteTypeId删除DictValue
        dictValueRepo.modifyByDictCode(dictType.getCode(), Constants.DATA_IS_INVALID);
      }
    } else {
      throw new CodeCommonException("需要删除的数据不存在！");
    }
  }

  /**
   * 新增DictType
   */
  @Override
  public DictTypePO insertDictType(DictTypePO dictType) {
    // 根据code查询DictType，code不能重复，若code重复则新增失败
    DictTypePO selectDictType =
        dictTypeRepo.findByCodeAndDelFlag(dictType.getCode(), Constants.DATA_IS_NORMAL);
    if (selectDictType == null) {
      // 新增DictType
      return dictTypeRepo.save(dictType);
    } else {
      throw new CodeCommonException("CODE:" + dictType.getCode() + "已存在，保存失败!");
    }
  }

  /**
   * 根据id查询DictType
   */
  @Override
  public DictTypePO findDictType(String id) {
    // 返回查询结果
    return dictTypeRepo.findByIdAndDelFlag(id, Constants.DATA_IS_NORMAL);
  }

  /**
   * 根据id查询DictValue
   */
  @Override
  public DictValuePO findDictValue(String id) {
    // 返回查询结果
    return dictValueRepo.findByIdAndDelFlag(id, Constants.DATA_IS_NORMAL);
  }


  /**
   * 根据id删除DictValue
   */
  @Override
  public void deleteDictValue(String id) {
    // 查询dictValue
    DictValuePO dictValue = this.findDictValue(id);
    if (dictValue != null) {
      // 设置删除属性
      dictValue.setDelFlag(Constants.DATA_IS_INVALID);
      // 删除dictValue
      dictValueRepo.save(dictValue);
    } else {
      throw new CodeCommonException("需要删除的数据不存在！");
    }
  }

  /**
   * 新增DictValue
   */
  @Override
  public DictValuePO insertDictValue(String code, DictValuePO dictValue) {
    // 设置属性
    dictValue.setDictCode(code);
    // 新增DictValue
    return dictValueRepo.save(dictValue);
  }

  /**
   * DictType分页
   */
  @Override
  public Page<DictTypePO> findDictTypePage(PageDTO searchParams) {
    List<Order> orders = Lists.newArrayList();

    // 构建删除标识过滤对象
    Filter delFlagFilter = new Filter();
    // 设置属性
    delFlagFilter.setField("delFlag");
    delFlagFilter.setOperator(Operator.EQ);
    delFlagFilter.setValue(Constants.DATA_IS_NORMAL);

    // 若传入参数中没有filters对象，则构建一个并传入filter值
    if (searchParams.getCollection() == null) {
      Collection collection = new Collection();
      List<Filter> filters = new ArrayList<>();
      searchParams.setCollection(collection);
      searchParams.getCollection().setFilters(filters);
    }

    searchParams.getCollection().getFilters().add(delFlagFilter);
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
    Specification<DictTypePO> spec = new Specification<DictTypePO>() {
      @Override
      public Predicate toPredicate(Root<DictTypePO> root, CriteriaQuery<?> query,
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
    return dictTypeRepo.findAll(spec, pagereq);
  }

  /**
   * DictValue分页
   */
  @Override
  public Page<DictValuePO> findDictValuePage(String code, PageDTO searchParams) {
    List<Order> orders = Lists.newArrayList();
    // 构建filter对象
    Filter codefilter = new Filter();
    // 默认添加filter
    codefilter.setField("dictCode");
    codefilter.setOperator(Operator.EQ);
    codefilter.setValue(code);
    // 构建删除标识过滤对象
    Filter delFlagFilter = new Filter();
    // 设置属性
    delFlagFilter.setField("delFlag");
    delFlagFilter.setOperator(Operator.EQ);
    delFlagFilter.setValue(Constants.DATA_IS_NORMAL);
    // 若传入参数中没有filters对象，则构建一个并传入filter值
    if (searchParams.getCollection() == null) {
      Collection collection = new Collection();
      List<Filter> filters = new ArrayList<>();
      searchParams.setCollection(collection);
      searchParams.getCollection().setFilters(filters);
    }
    // 默认添加filter
    searchParams.getCollection().getFilters().add(codefilter);
    searchParams.getCollection().getFilters().add(delFlagFilter);
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
    Specification<DictValuePO> spec = new Specification<DictValuePO>() {
      @Override
      public Predicate toPredicate(Root<DictValuePO> root, CriteriaQuery<?> query,
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
    return dictValueRepo.findAll(spec, pagereq);
  }

  /**
   * 批量保存DictValus
   */
  @Override
  @ChangeDatasource
  public void saveDictValues(DatasourcePO datasource, DictTypePO dictType,
      List<DictValuePO> dictValues) {
    if (databaseDao.findDictTypeByCode(dictType.getCode(),Constants.DATA_IS_NORMAL) == 0) {
      // 设置属性
      dictType.preInsert();
      databaseDao.insertDictType(dictType);
    }
    // 若传入参数为空，则删除该dictCode下所有value
    if (dictValues != null) {
      Map<String, String> map = new HashMap<>();
      // 定义长度
      int length = 1;
      for (DictValuePO dictValue : dictValues) {
        map.put(dictValue.getValue(), dictValue.getName());
        dictValue.setDictCode(dictType.getCode());
        if (map.size() != length) {
          throw new CodeCommonException("参数重复：" + dictValue.getValue());
        }
        length++;
      }
      // 获取数据库中指定dictCode下的value
      List<DictValuePO> allDictValues =
          databaseDao.findDictValueByCode(dictType.getCode(), Constants.DATA_IS_NORMAL);
      // 若数据库中数据为空，则直接保存数据
      if (allDictValues == null || allDictValues.isEmpty()) {
        for (DictValuePO dictValue : dictValues) {
          if (dictValue.isNew()) {
            // 设置属性
            dictValue.preInsert();
            // 保存信息
            databaseDao.saveDictValue(dictValue);
          } else {
            // 更新信息
            databaseDao.updateDictValue(dictValue);
          }
        }
      } else {
        for (DictValuePO dictValueData : allDictValues) {
          // 保存条数
          int saveCount = 0;
          for (DictValuePO dictValue : dictValues) {
            // 若传入参数id为空或者与数据库中id相同则更新，更新条数+1
            if (dictValueData.getId().equals(dictValue.getId())) {
              // 设置属性
              dictValue.preInsert();
              // 更新信息
              databaseDao.updateDictValue(dictValue);
              saveCount++;
            } else if (dictValue.isNew()) {
              // 设置属性
              dictValue.preInsert();
              // 保存信息
              databaseDao.saveDictValue(dictValue);
            }
          }
          // 若没有执行更新操作则执行删除
          if (saveCount == 0) {
            databaseDao.deleteDictValue(dictValueData.getId(), Constants.DATA_IS_INVALID);
          }
        }
      }
    } else {
      // 批量删除values
      databaseDao.deleteDictValues(dictType.getCode(), Constants.DATA_IS_INVALID);
    }
  }

  /**
   * 查询所有DictType
   */
  @Override
  @ChangeDatasource
  public List<DictTypePO> findDictTypes(DatasourcePO datasource) {
    return databaseDao.findDictTypes(Constants.DATA_IS_NORMAL);
  }

  /**
   * 查询指定DictType下的value
   */
  @Override
  @ChangeDatasource
  public List<DictValuePO> findTypeAndValue(DatasourcePO datasource, String code) {
    //查询指定type下所有的value
    return databaseDao.findDictValueByCode(code, Constants.DATA_IS_NORMAL);
  }
  
  /**
   * 新增DictType
   * @param datasource
   * @param dictType
   */
  @Override
  @ChangeDatasource
  public void insertDictType(DatasourcePO datasource, DictTypePO dictType){
    //查询DictTyp是否存在
    int isExist = databaseDao.findDictTypeByCode(dictType.getCode(), Constants.DATA_IS_NORMAL);
    //如果不存在DictType则新增
    if(isExist == 0){
      dictType.preInsert();
      databaseDao.insertDictType(dictType);
    }
  }
}
