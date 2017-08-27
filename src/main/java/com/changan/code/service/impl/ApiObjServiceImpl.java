package com.changan.code.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;

import com.changan.code.common.BaseDTO;
import com.changan.code.common.Constants;
import com.changan.code.common.DtoType;
import com.changan.code.common.ParamIn;
import com.changan.code.entity.ApiObjPO;
import com.changan.code.entity.ApiParamPO;
import com.changan.code.exception.CodeCommonException;
import com.changan.code.repository.ApiObjRepository;
import com.changan.code.repository.ApiParamRepository;
import com.changan.code.service.IApiObjService;
import com.changan.code.service.IApiParamService;
import com.google.common.base.CaseFormat;

/**
 * apiObj service
 * 
 * @author xuyufeng
 *
 */
@Service
public class ApiObjServiceImpl implements IApiObjService {

  /**
   * 注入ApiObj Repository
   */
  @Autowired
  ApiObjRepository apiObjRepo;

  /**
   * 注入ApiParam Repository
   */
  @Autowired
  ApiParamRepository apiParamRepo;

  /**
   * 注入IApiParamService
   */
  @Autowired
  IApiParamService apiParamService;

  /**
   * 保存apiObj,若api_base_id相同，
   */
  @Override
  public void saveApiObj(ApiObjPO apiObj) {
    // 重复数
    int sameCount = 0;
    // 查询重复数
    sameCount = apiObjRepo.countApiObjByUri(apiObj.getApiBaseId(), apiObj.getUri());
    if (sameCount == 0) {
      // 保存apiObj
      apiObjRepo.save(apiObj);
      // 若参数不为空
      if (apiObj.getApiParam() != null) {
        // 保存参数
        apiParamRepo.save(apiObj.getApiParam());
      }
    } else {
      throw new CodeCommonException("保存失败！");
    }
  }

  /**
   * 更新apiObj
   */
  @Override
  public void updateApiObj(ApiObjPO apiObj) {
    // 重复数
    int sameCount = 0;
    // 查询重复数
    sameCount = apiObjRepo.countApiObjByUri(apiObj.getApiBaseId(), apiObj.getUri());
    // 查询数据库中是否有此条数据
    ApiObjPO updateApiObj = apiObjRepo.findOne(apiObj.getId());
    if (updateApiObj != null && sameCount == 0) {
      // 更新属性
      updateApiObj.updateAttrs(apiObj);
      // 保存apiObj
      this.updateApiObj(updateApiObj);
      // 获取apiObj中的参数
      List<ApiParamPO> apiParams = apiObj.getApiParam();
      if (!apiParams.isEmpty() && apiParams != null) {
        for (ApiParamPO apiParam : apiParams) {
          // 设置ApiObj
          apiParam.setApiObjId(updateApiObj.getId());
          // 保存apiParam
          apiParamService.saveApiParam(apiParam);
        }
      }
    } else {
      throw new CodeCommonException("更新失败！");
    }
  }

  /**
   * 查询所有的apiObj
   * 
   * @return
   */
  @Override
  public List<ApiObjPO> findAllApiObj(String apiBaseId) {
    // 执行查询
    List<ApiObjPO> apiObjPO = apiObjRepo.findByApiBaseId(apiBaseId);
    return apiObjPO;
  }

  /**
   * 根据id查询apiObj
   */
  @Override
  public ApiObjPO findOneApiObj(String id) {
    // 根据id查询apiObj
    ApiObjPO apiObj = apiObjRepo.findOne(id);
    return apiObj;
  }

  /**
   * 自动创建crud的api
   * 
   * @param apiBaseId
   */
  @Override
  @Transactional("jpaTransactionManager")
  public void createAutoCrudApi(String apiBaseId, String tableName, String dtoName,
      String className, String datasourcePName, String dbname, Long dbcount) {
    // 列表接口
    ApiObjPO pageApiObj = this.genApiObjPO(apiBaseId, tableName, BaseDTO.ResultPageDTO.toString(),
        dtoName, className, datasourcePName, dbname, dbcount, "s", "s", Constants.API_PRODUCES,
        "分页列表", RequestMethod.POST.toString());
    pageApiObj = apiObjRepo.save(pageApiObj);
    // 列表接口参数
    ApiParamPO pageApiparam = this.genApiParamPO(pageApiObj.getId(), "searchParams", "分页参数",
        ParamIn.BODY, DtoType.REFOBJ, BaseDTO.PageDTO.toString());
    apiParamRepo.save(pageApiparam);
    // 详情接口
    ApiObjPO showApiObj = this.genApiObjPO(apiBaseId, tableName, dtoName, null, className,
        datasourcePName, dbname, dbcount, "s/{id}/show", "sShow", Constants.API_PRODUCES, "详情",
        RequestMethod.GET.toString());
    showApiObj = apiObjRepo.save(showApiObj);
    // 详情接口参数
    ApiParamPO showApiparam = this.genApiParamPO(pageApiObj.getId(), "id", "对象id",
        ParamIn.PATH, DtoType.BASE, "String");
    apiParamRepo.save(showApiparam);
    // 保存接口
    ApiObjPO saveApiObj = this.genApiObjPO(apiBaseId, tableName, dtoName, null, className,
        datasourcePName, dbname, dbcount, "/save", "save", Constants.API_PRODUCES, "保存（新增、修改）",
        RequestMethod.POST.toString());
    saveApiObj = apiObjRepo.save(saveApiObj);
    // 保存接口参数
    ApiParamPO saveApiparam = this.genApiParamPO(pageApiObj.getId(),
        CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName), "实体对象参数",
        ParamIn.BODY, DtoType.REFOBJ, className);
    apiParamRepo.save(saveApiparam);
    // jsonschema接口
    ApiObjPO schemaApiObj = this.genApiObjPO(apiBaseId, tableName, BaseDTO.JsonSchema.toString(),
        null, className, datasourcePName, dbname, dbcount, "s", "s", "application/schema+json",
        "schema", RequestMethod.POST.toString());
    schemaApiObj = apiObjRepo.save(schemaApiObj);
  }

  /**
   * 生成api实体
   * 
   * @param apiBaseId
   * @param tableName
   * @param dtoName
   * @param className
   * @param datasourcePName
   * @param dbname
   * @param dbcount
   * @param urlPostfix
   * @param namePostfix
   * @param produces
   * @param requestMethod
   * @return
   */
  private ApiObjPO genApiObjPO(String apiBaseId, String tableName, String dtoName,
      String genericName, String className, String datasourcePName, String dbname, Long dbcount,
      String urlPostfix, String namePostfix, String produces, String description,
      String requestMethod) {
    ApiObjPO apiObj = new ApiObjPO();
    apiObj.setApiBaseId(apiBaseId);
    String prefix = "";
    if (dbcount > 1) {
      prefix = dbname.concat("_");
    }
    String[] tableNames = tableName.split("_");
    String apiname = prefix.concat(tableName).toLowerCase();
    apiObj.setUri(apiname.replace("_", "/").concat(urlPostfix));
    apiObj.setName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, apiname)
        .concat(namePostfix).concat(requestMethod));
    apiObj.setRequestMethod(RequestMethod.GET.toString());
    apiObj.setResponseObjName(dtoName);
    apiObj.setResponseObjGeneric(genericName);
    apiObj.setSummary("实体".concat(className).concat(description));
    apiObj.setDescription("实体".concat(className).concat(description));
    apiObj.setProduces(produces);
    apiObj.setTag(
        CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, prefix.concat(tableNames[0])));
    apiObj.setIsAutoGen(Constants.IS_ACTIVE);
    return apiObj;
  }

  /**
   * 生成参数对象
   * 
   * @param apiObjId
   * @param name
   * @param description
   * @param form
   * @param type
   * @param format
   * @return
   */
  private ApiParamPO genApiParamPO(String apiObjId, String name, String description, ParamIn form,
      DtoType type, String format) {
    ApiParamPO apiparam = new ApiParamPO();
    apiparam.setApiObjId(apiObjId);
    apiparam.setName(name);
    apiparam.setDescription(description);
    apiparam.setForm(form.toString().toLowerCase());
    apiparam.setType(type.toString().toLowerCase());
    apiparam.setFormat(format);
    return apiparam;
  }

}
