package org.hotpotmaterial.code.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hotpotmaterial.code.common.Constants;
import org.hotpotmaterial.code.entity.ApiBasePO;
import org.hotpotmaterial.code.entity.ApiObjPO;
import org.hotpotmaterial.code.exception.CodeCommonException;
import org.hotpotmaterial.code.repository.ApiBaseRepository;
import org.hotpotmaterial.code.service.IApiBaseService;
import org.hotpotmaterial.code.service.IApiObjService;

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
  @Autowired
  ApiBaseRepository apiBaseRepo;

  /**
   * 注入ApiObjService
   */
  @Autowired
  IApiObjService apiObjService;

  /**
   * 保存API，相同版本号操作失败
   */
  @Override
  @Transactional("jpaTransactionManager")
  public ApiBasePO saveApiBase(ApiBasePO apiBase) {
    // 查询是否有重复数
    int sameCount = apiBaseRepo.countByVersionNameAndProjectIdAndDelFlag(apiBase.getVersionName(),
        apiBase.getProjectId(), Constants.DATA_IS_NORMAL);
    if (sameCount == 0) {
      // 保存api
      return apiBaseRepo.save(apiBase);
    } else {
      throw new CodeCommonException("添加失败，版本号已存在!");
    }
  }

  /**
   * 更新API，版本号相同操作失败
   */
  @Override
  public ApiBasePO updateApiBase(ApiBasePO apiBase) {
    // 查询API
    ApiBasePO updateApiBasePO =
        apiBaseRepo.findByIdAndDelFlag(apiBase.getId(), Constants.DATA_IS_NORMAL);
    if (updateApiBasePO != null) {
      // 更新API
      updateApiBasePO.updateAttrs(apiBase);
      // 根据versionName、projectId查询Api
      List<ApiBasePO> apiBases =
          apiBaseRepo.findByVersionNameAndProjectIdAndDelFlag(updateApiBasePO.getVersionName(),
              updateApiBasePO.getProjectId(), Constants.DATA_IS_NORMAL);
      if (apiBases.isEmpty()) {
        // 保存更新
        return apiBaseRepo.save(updateApiBasePO);
      } else {
        if (apiBases.get(0).getId().equals(updateApiBasePO.getId())) {
          // 保存更新
          return apiBaseRepo.save(updateApiBasePO);
        } else {
          throw new CodeCommonException("更新失败，versionName重复！");
        }
      }
    } else {
      throw new CodeCommonException("更新失败，需要更新的数据不存在!");
    }
  }

  /**
   * 查找出项目最早的有效的api base
   */
  @Override
  public ApiBasePO findEarlestApiBaseByProjectId(String projectId) {
    return apiBaseRepo.findFirstByProjectIdAndDelFlagOrderByCreatedAtDesc(projectId,
        Constants.DATA_IS_NORMAL);
  }

  /**
   * 查询所有的Api
   */
  @Override
  public List<ApiBasePO> findAllApiBase(String projectId) {
    // 返回查询结果
    return apiBaseRepo.findByProjectIdAndDelFlagOrderByCreatedAt(projectId,
        Constants.DATA_IS_NORMAL);
  }

  /**
   * 根据id删除Api
   */
  @Override
  @Transactional("jpaTransactionManager")
  public void deleteApiBaseById(String id) {
    // 查询需要删除的数据是否存在
    ApiBasePO apiBase = apiBaseRepo.findByIdAndDelFlag(id, Constants.DATA_IS_NORMAL);
    if (apiBase != null) {
      // 执行删除
      apiBase.setDelFlag(Constants.DATA_IS_INVALID);
      apiBaseRepo.save(apiBase);
      //查询apiBase下所有的方法
      List<ApiObjPO> apiObjs = apiObjService.findAllApiObj(id);
      //如果apiobj不为空则执行apiParam删除
      if(apiObjs != null && !apiObjs.isEmpty()){
        for(ApiObjPO apiObj : apiObjs){
          //删除apiObj下所有的参数s
          apiObjService.deleteApiObj(apiObj.getId());
        }
      }
    } else {
      throw new CodeCommonException("删除失败，删除的数据不存在！");
    }
  }

  /**
   * 创建默认api base
   * 
   * @param projectId
   * @return
   */
  @Override
  public ApiBasePO createDefaultApiBase(String projectId, String packageName) {
    ApiBasePO apibase = new ApiBasePO();
    // 通过package的最后一级作为api的basepath
    String[] packages = packageName.split("\\.");
    // 设置projectId
    apibase.setProjectId(projectId);
    // 设置版本号
    apibase.setVersionName(Constants.API_BASE_DEFAULT_VERSION_NAME);
    // 添加描述
    apibase.setDescription(Constants.API_BASE_DEFAULT_DESCRIPTION);
    // 添加basepath
    apibase.setBasePath("/".concat(packages[packages.length - 1]).concat("/api/")
        .concat(Constants.API_BASE_DEFAULT_VERSION_NAME));
    return apiBaseRepo.save(apibase);
  }

  /**
   * 根据id查询ApiBase
   */
  @Override
  public ApiBasePO findApiBase(String id) {
    // 返回查询结果
    return apiBaseRepo.findByIdAndDelFlag(id, Constants.DATA_IS_NORMAL);
  }

}
