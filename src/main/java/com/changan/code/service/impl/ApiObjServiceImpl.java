package com.changan.code.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
  @Transactional("jpaTransactionManager")
  public ApiObjPO saveApiObj(ApiObjPO apiObj) {
    // 重复数
    int sameCount = 0;
    // 查询重复数
    sameCount = apiObjRepo.countByApiBaseIdAndUriAndDelFlag(apiObj.getApiBaseId(), apiObj.getUri(),
        Constants.DATA_IS_NORMAL);
    if (sameCount == 0) {
      // 保存apiObj
      apiObjRepo.save(apiObj);
      // 若参数不为空
      if (apiObj.getApiParam() != null && !apiObj.getApiParam().isEmpty()) {
        // 批量保存参数
        apiParamService.saveApiParam(apiObj.getApiParam(), apiObj.getId());
      }
      return apiObj;
    } else {
      throw new CodeCommonException("保存失败！Url已存在！");
    }
  }


  /**
   * 更新apiObj
   */
  @Override
  public ApiObjPO updateApiObj(ApiObjPO apiObj) {
    // 查询数据库中是否有此条数据
    ApiObjPO updateApiObj = apiObjRepo.findByIdAndDelFlag(apiObj.getId(), Constants.DATA_IS_NORMAL);
    if (updateApiObj != null) {
      // 更新属性
      updateApiObj.updateAttrs(apiObj);
      // 保存apiObj
      this.saveApiObj(updateApiObj);
      // 返回更新对象
      return updateApiObj;
    } else {
      throw new CodeCommonException("更新失败！数据不存在！");
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
    List<ApiObjPO> apiObjPO =
        apiObjRepo.findByApiBaseIdAndDelFlag(apiBaseId, Constants.DATA_IS_NORMAL);
    return apiObjPO;
  }


  /**
   * 根据id查询apiObj
   */
  @Override
  public ApiObjPO findOneApiObj(String id) {
    // 根据id查询apiObj
    ApiObjPO apiObj = apiObjRepo.findByIdAndDelFlag(id, Constants.DATA_IS_NORMAL);
    return apiObj;
  }

  /**
   * 根据id删除ApiObj
   */
  @Override
  @Transactional("jpaTransactionManager")
  public void deleteApiObj(String id) {
    // 根据id删除ApiObj
    ApiObjPO apiObj = apiObjRepo.findByIdAndDelFlag(id, Constants.DATA_IS_NORMAL);
    if (apiObj != null) {
      // 删除apiObj
      apiObj.setDelFlag(Constants.DATA_IS_INVALID);
      // 删除apiObj下的参数
      apiParamService.deleteAllParam(apiObj.getId());
      // 保存删除
      apiObjRepo.save(apiObj);
    } else {
      throw new CodeCommonException("删除失败！需要删除的数据不存在！");
    }
  }


  /**
   * 自动创建crud的api
   * 
   * @param apiBaseId
   */
  @Override
  @Transactional("jpaTransactionManager")
  public void createAutoCrudApi(String apiBaseId, String tableId, String tableName, String dtoName,
      String className, String datasourcePName, String dbname, Long dbcount) {
    // 列表接口
    ApiObjPO pageApiObj = this.genApiObjPO(apiBaseId, tableId, tableName,
        BaseDTO.ResultPageDTO.toString(), dtoName, className, datasourcePName, dbname, dbcount, "s",
        "s", Constants.API_PRODUCES, "分页列表", RequestMethod.POST.toString());
    pageApiObj = apiObjRepo.save(pageApiObj);
    // 列表接口参数
    ApiParamPO pageApiparam = this.genApiParamPO(pageApiObj.getId(), "searchParams", "分页参数",
        ParamIn.BODY, DtoType.REFOBJ, BaseDTO.PageDTO.toString());
    apiParamRepo.save(pageApiparam);
    // 详情接口
    ApiObjPO showApiObj = this.genApiObjPO(apiBaseId, tableId, tableName, dtoName, null, className,
        datasourcePName, dbname, dbcount, "s/{id}/show", "sShow", Constants.API_PRODUCES, "详情",
        RequestMethod.GET.toString());
    showApiObj = apiObjRepo.save(showApiObj);
    // 详情接口参数
    ApiParamPO showApiparam =
        this.genApiParamPO(pageApiObj.getId(), "id", "对象id", ParamIn.PATH, DtoType.BASE, "String");
    apiParamRepo.save(showApiparam);
    // 删除接口
    ApiObjPO delApiObj = this.genApiObjPO(apiBaseId, tableId, tableName, dtoName, null, className,
        datasourcePName, dbname, dbcount, "s/{id}/delete", "s", Constants.API_PRODUCES, "删除",
        RequestMethod.DELETE.toString());
    showApiObj = apiObjRepo.save(delApiObj);
    // 详情接口参数
    ApiParamPO delApiparam =
        this.genApiParamPO(pageApiObj.getId(), "id", "对象id", ParamIn.PATH, DtoType.BASE, "String");
    apiParamRepo.save(delApiparam);
    // 保存接口
    ApiObjPO saveApiObj = this.genApiObjPO(apiBaseId, tableId, tableName, dtoName, null, className,
        datasourcePName, dbname, dbcount, "/save", "Save", Constants.API_PRODUCES, "保存（新增、修改）",
        RequestMethod.POST.toString());
    saveApiObj = apiObjRepo.save(saveApiObj);
    // 保存接口参数
    ApiParamPO saveApiparam = this.genApiParamPO(pageApiObj.getId(),
        CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName), "实体对象参数", ParamIn.BODY,
        DtoType.REFOBJ, className);
    apiParamRepo.save(saveApiparam);
    // jsonschema接口
    ApiObjPO schemaApiObj = this.genApiObjPO(apiBaseId, tableId, tableName,
        BaseDTO.JsonSchema.toString(), null, className, datasourcePName, dbname, dbcount, "s", "s",
        "application/schema+json", "的json-schema", RequestMethod.GET.toString());
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
  private ApiObjPO genApiObjPO(String apiBaseId, String tableId, String tableName, String dtoName,
      String genericName, String className, String datasourcePName, String dbname, Long dbcount,
      String urlPostfix, String namePostfix, String produces, String description,
      String requestMethod) {
    ApiObjPO apiObj = new ApiObjPO();
    apiObj.setApiBaseId(apiBaseId);
    String urlPrefix = "", namePrefix = "";
    if (dbcount > 1) {
      urlPrefix = dbname.concat("/");
      namePrefix = dbname.concat("_");
      apiObj.setPrefixName(
          CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, dbname));
    }
    apiObj.setUri(urlPrefix.concat(tableName).concat(urlPostfix));
    apiObj.setName(CaseFormat.LOWER_UNDERSCORE
        .to(CaseFormat.LOWER_CAMEL, namePrefix.concat(tableName).toLowerCase()).concat(namePostfix)
        .concat(StringUtils.capitalize(requestMethod.toLowerCase())));
    apiObj.setRequestMethod(requestMethod);
    apiObj.setResponseObjName(dtoName);
    apiObj.setResponseObjGeneric(genericName);
    apiObj.setSummary("实体".concat(className).concat(description));
    apiObj.setDescription("实体".concat(className).concat(description));
    apiObj.setProduces(produces);
    apiObj.setTag(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName));
    apiObj.setGenBasedTableId(tableId);
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

  @Override
  public void deleteAutoCrudApi(String tableId) {
    // 获取apiObj的id
    List<String> apiobjIds = apiObjRepo.findIdByGenBasedTableId(tableId);
    if (null != apiobjIds) {
      for (String id : apiobjIds) {
        // 删除param
        apiParamService.deleteByApiObjId(id);
      }
    }
    // 删除apiObj
    apiObjRepo.deleteByGenBasedTableId(tableId);
  }

}
