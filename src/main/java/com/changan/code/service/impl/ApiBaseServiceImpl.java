package com.changan.code.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.changan.code.common.Constants;
import com.changan.code.entity.ApiBasePO;
import com.changan.code.exception.CodeCommonException;
import com.changan.code.repository.ApiBaseRepository;
import com.changan.code.service.IApiBaseService;

/**
 * Api service
 * 
 * @author xuyufeng
 *
 */
@Service
public class ApiBaseServiceImpl implements IApiBaseService {

  /**
   * ApiBase Repository注入
   */
  ApiBaseRepository apiBaseRepo;

  /**
   * 保存API，相同版本号操作失败
   */
  @Override
  public void saveApiBase(ApiBasePO apiBase) {
    // 重复数
    int sameCount = 0;
    // 查询是否有重复数
    sameCount = apiBaseRepo.countApiByVersionName(apiBase.getVersionName(), apiBase.getProjectId());
    if (sameCount == 0) {
      // 保存api
      apiBaseRepo.save(apiBase);
    } else {
      throw new CodeCommonException("添加失败，版本号已存在!");
    }
  }

  /**
   * 更新API，版本号相同操作失败
   */
  @Override
  public void updateApiBase(ApiBasePO apiBase) {
    // 重复数
    int sameCount = 0;
    // 查询是否有重复数
    sameCount = apiBaseRepo.countApiByVersionName(apiBase.getVersionName(), apiBase.getProjectId());
    // 查询API
    ApiBasePO updateApiBasePO = apiBaseRepo.findOne(apiBase.getId());
    if (updateApiBasePO != null && sameCount == 0) {
      // 更新API
      updateApiBasePO.updateAttrs(apiBase);
      // 保存api
      this.saveApiBase(updateApiBasePO);
    } else {
      throw new CodeCommonException("更新失败，需要更新的数据不存在或版本号已存在!");
    }
  }

  /**
   * 查询所有的API
   */
  @Override
  public List<ApiBasePO> findAllApiBase() {
    // 查询所有的API
    List<ApiBasePO> allApiBase = apiBaseRepo.findAll();
    // 返回查询结果
    return allApiBase;
  }

  /**
   * 查找出项目最早的有效的api base
   */
  @Override
  public ApiBasePO findEarlestApiBaseByProjectId(String projectId) {
    return apiBaseRepo.findFirstByProjectIdAndDelFlagOrderByCreatedAtASC(projectId, Constants.DATA_IS_NORMAL);
  }

  /**
   * 创建默认api base
   * @param projectId
   * @return
   */
  @Override
  public ApiBasePO createDefaultApiBase(String projectId, String packageName) {
    ApiBasePO apibase = new ApiBasePO();
    // 通过package的最后一级作为api的basepath
    String[] packages = packageName.split(".");
    // 设置版本号
    apibase.setVersionName(Constants.API_BASE_DEFAULT_VERSION_NAME);
    // 添加描述
    apibase.setDescription(Constants.API_BASE_DEFAULT_DESCRIPTION);
    // 添加basepath
    apibase.setBasePath(packages[packages.length-1].concat("/api/").concat(Constants.API_BASE_DEFAULT_VERSION_NAME));
    return apiBaseRepo.save(apibase);
  }

}
