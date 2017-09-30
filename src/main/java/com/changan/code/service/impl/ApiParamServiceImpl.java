package com.changan.code.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.changan.code.common.Constants;
import com.changan.code.entity.ApiParamPO;
import com.changan.code.exception.CodeCommonException;
import com.changan.code.repository.ApiParamRepository;
import com.changan.code.service.IApiParamService;

/**
 * ApiParam Service实现类
 * 
 * @author xuyufeng
 *
 */
@Service
public class ApiParamServiceImpl implements IApiParamService {

  /**
   * 注入ApiParam Repository
   */
  @Autowired
  private ApiParamRepository apiParamRepo;

  /**
   * 保存Api参数
   */
  @Override
  @Transactional("jpaTransactionManager")
  public void saveApiParam(List<ApiParamPO> apiParams, String apiObjId) {
    // 若传入参数不为空，则判断数据操作，若为空则直接删除apiObj中所有的参数
    if (apiParams != null) {
      Map<String, String> map = new HashMap<>();
      // 定义长度
      int length = 1;
      int sort = 0;
      for (ApiParamPO apiParam : apiParams) {
        map.put(apiParam.getName(), null);
        // 给ApiParam设置ApiObjId
        apiParam.setApiObjId(apiObjId);
        apiParam.setArrayType();
        apiParam.setSort(sort);
        if (map.size() != length) {
          throw new CodeCommonException("参数重复：" + apiParam.getName());
        }
        length++;
      }
      // 获取数据库中api方法参数
      List<ApiParamPO> allApiParam =
          apiParamRepo.findByApiObjIdAndDelFlag(apiObjId, Constants.DATA_IS_NORMAL);
      // 若数据库为空则直接保存数据
      if (allApiParam == null || allApiParam.isEmpty()) {
        // 保存数据
        apiParamRepo.save(apiParams);
      } else {
        // 批量保存参数
        this.saveAllApiParam(apiObjId, apiParams, allApiParam);
      }
    } else {
      // 逻辑删除ApiObj下所有的参数
      this.deleteAllParam(apiObjId);
    }
  }

  /**
   * 查询指定ApiObj所有Api参数
   */
  @Override
  public List<ApiParamPO> findAllApiParam(String apiObjId) {
    // 获取所有Api参数
    return apiParamRepo.findByApiObjIdAndDelFlag(apiObjId, Constants.DATA_IS_NORMAL);
  }

  /**
   * 根据id执行删除
   */
  @Override
  public void deleteApiParamById(String id) {
    // 通过id查询参数
    ApiParamPO apiParam = apiParamRepo.findByIdAndDelFlag(id, Constants.DATA_IS_NORMAL);
    if (apiParam != null) {
      // 执行删除
      apiParam.setDelFlag(Constants.DATA_IS_INVALID);
    } else {
      throw new CodeCommonException("操作失败需要删除的数据不存在！");
    }
  }

  /**
   * 批量删除ApiParam
   */
  @Override
  public void deleteAllParam(String apiObjId) {
    // 获取所有Api参数
    List<ApiParamPO> apiParams =
        apiParamRepo.findByApiObjIdAndDelFlag(apiObjId, Constants.DATA_IS_NORMAL);
    if(!apiParams.isEmpty() || apiParams != null){
      for (ApiParamPO apiParam : apiParams) {
        // 执行删除
        apiParam.setDelFlag(Constants.DATA_IS_INVALID);
      }
      apiParamRepo.save(apiParams); 
    }
  }

  /**
   * 根据ApiObjId删除参数
   */
  @Override
  public void deleteByApiObjId(String apiObjId) {
    apiParamRepo.deleteByApiObjId(apiObjId);
  }

  /**
   * 批量保存apiParam
   * 
   * @param apiObjId
   * @param apiParams
   */
  public void saveAllApiParam(String apiObjId, List<ApiParamPO> apiParams,
      List<ApiParamPO> allApiParam) {
    for (ApiParamPO apiParamData : allApiParam) {
      // 保存条数
      int saveCount = 0;
      for (ApiParamPO apiParam : apiParams) {
        // 若传入参数id为空或者与数据库中id相同则更新，更新条数+1
        if (apiParam.getId() == null || apiParam.getId().equals(apiParamData.getId())) {
          apiParamRepo.save(apiParam);
          saveCount++;
        }
      }
      // 若没有执行更新操作则执行删除
      if (saveCount == 0) {
        this.deleteApiParamById(apiParamData.getId());
      }
    }
  }

  @Override
  public List<ApiParamPO> findAllApiParamOrderBySort(String apiObjId) {
    // 获取所有Api参数
    return apiParamRepo.findByApiObjIdAndDelFlagOrderBySortAsc(apiObjId, Constants.DATA_IS_NORMAL);
  }
}
