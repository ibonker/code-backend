package com.changan.code.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;

import com.changan.code.common.BaseDTO;
import com.changan.code.common.Constants;
import com.changan.code.common.BaseType;
import com.changan.code.common.BaseParamIn;
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
    // 查询重复数
    int sameCount = apiObjRepo.countByApiBaseIdAndUriAndDelFlag(apiObj.getApiBaseId(),
        apiObj.getUri(), Constants.DATA_IS_NORMAL);
    if (sameCount == 0) {
      // 处理uri
      String[] strs = apiObj.getUri().split("/");
      StringBuilder name = new StringBuilder();
      for (int i = 1; i < strs.length; i++) {
        // 判断是否带有{
        int j = strs[i].indexOf("{");
        if (j != -1) {
          continue;
        }
        name.append(strs[i] + "_");
      }
      // 通过uri设置名称
      apiObj.setName(CaseFormat.LOWER_UNDERSCORE
          .to(CaseFormat.LOWER_CAMEL, name.toString().replace("/", "_").toLowerCase())
          .concat(StringUtils.capitalize(apiObj.getRequestMethod().toLowerCase())));
      // 保存apiObj
      ApiObjPO returnApiObj = apiObjRepo.save(apiObj.setArrayType());
      // 若参数不为空
      if (apiObj.getApiParams() != null && !apiObj.getApiParams().isEmpty()) {
        // 通过Uri和ApiBaseId查询刚保存的ApiObj
        ApiObjPO findApiObj =
            apiObjRepo.findByApiBaseIdAndUri(apiObj.getApiBaseId(), apiObj.getUri());
        // 批量保存参数
        apiParamService.saveApiParam(apiObj.getApiParams(), findApiObj.getId());
      }
      // 返回对象
      return returnApiObj;
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
      // 处理uri
      String[] strs = updateApiObj.getUri().split("/");
      StringBuilder name = new StringBuilder();
      for (int i = 1; i < strs.length; i++) {
        // 判断是否带有{s
        int j = strs[i].indexOf("{");
        if (j != -1) {
          continue;
        }
        name.append(strs[i] + "_");
      }
      // 通过uri设置名称
      updateApiObj.setName(
          CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name.toString().toLowerCase())
              .concat(StringUtils.capitalize(updateApiObj.getRequestMethod().toLowerCase())));
      // 根据Uri和ApiBaseId查询apiObj
      List<ApiObjPO> apiObjs = apiObjRepo.findByUriAndApiBaseIdAndDelFlag(updateApiObj.getUri(),
          updateApiObj.getApiBaseId(), Constants.DATA_IS_NORMAL);
      // 若数组长度为0则直接更新，若为1则判断id是否相同，若不同则更新失败
      if (apiObjs.isEmpty()) {
        // 批量保存参数
        apiParamService.saveApiParam(apiObj.getApiParams(), updateApiObj.getId());
        // 保存apiObj
        return apiObjRepo.save(updateApiObj.setArrayType());
      } else {
        if (updateApiObj.getId().equals(apiObjs.get(0).getId())) {
          // 批量保存参数
          apiParamService.saveApiParam(apiObj.getApiParams(), updateApiObj.getId());
          // 保存apiObj
          return apiObjRepo.save(updateApiObj.setArrayType());
        } else {
          throw new CodeCommonException("更新失败！Uri重复！");
        }
      }
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
    return apiObjRepo.findByApiBaseIdAndDelFlagOrderByUri(apiBaseId, Constants.DATA_IS_NORMAL);
  }


  /**
   * 根据id查询apiObj
   */
  @Override
  public ApiObjPO findOneApiObj(String id) {
    // 根据id查询apiObj
    return apiObjRepo.findByIdAndDelFlag(id, Constants.DATA_IS_NORMAL);
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
  public void createAutoCrudApi(String apiBaseId, String tableId, String tableName,
      String tableComment, String dtoName, String className, String dbname,
      Long dbcount) {
    // table名称转换
    String tableParamName =
        CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName.toLowerCase());
    // 分页列表列表接口
    ApiObjPO pageApiObj = this.genApiObjPO(apiBaseId, tableId, tableName, tableComment,
        BaseDTO.ResultPageDTO.toString(), className, className, dbname, dbcount,
        "s/pages", Constants.API_PRODUCES, "分页列表", RequestMethod.POST.toString());
    pageApiObj = apiObjRepo.save(pageApiObj);
    // 分页列表接口参数
    ApiParamPO pageApiparam = this.genApiParamPO(pageApiObj.getId(), "searchParams", "分页参数",
        BaseParamIn.BODY, BaseType.DTO, BaseDTO.PageDTO.toString());
    pageApiparam.setSort(0);
    apiParamRepo.save(pageApiparam);
    // 详情接口
    ApiObjPO showApiObj =
        this.genApiObjPO(apiBaseId, tableId, tableName, tableComment, dtoName, null, className,
            dbname, dbcount, "s/{".concat(tableParamName.concat("Id}")),
            Constants.API_PRODUCES, "详情", RequestMethod.GET.toString());
    showApiObj = apiObjRepo.save(showApiObj);
    // 详情接口参数
    ApiParamPO showApiparam = this.genApiParamPO(showApiObj.getId(), tableParamName.concat("Id"),
        tableParamName.concat("对象id"), BaseParamIn.PATH, BaseType.BASE, "String");
    showApiparam.setSort(0);
    apiParamRepo.save(showApiparam);
    // 删除接口
    ApiObjPO delApiObj = this.genApiObjPO(apiBaseId, tableId, tableName, tableComment,
        BaseDTO.ResultDTO.toString(), null, className, dbname, dbcount,
        "s/{".concat(tableParamName.concat("Id}")), Constants.API_PRODUCES, "删除",
        RequestMethod.DELETE.toString());
    showApiObj = apiObjRepo.save(delApiObj);
    // 删除接口参数
    ApiParamPO delApiparam = this.genApiParamPO(delApiObj.getId(), tableParamName.concat("Id"),
        tableParamName.concat("对象id"), BaseParamIn.PATH, BaseType.BASE, "String");
    delApiparam.setSort(0);
    apiParamRepo.save(delApiparam);
    // 新增接口
    ApiObjPO saveApiObj = this.genApiObjPO(apiBaseId, tableId, tableName, tableComment,
        BaseDTO.ResultDTO.toString(), null, className, dbname, dbcount, "s",
        Constants.API_PRODUCES, "新增", RequestMethod.POST.toString());
    saveApiObj = apiObjRepo.save(saveApiObj);
    // 新增接口参数
    ApiParamPO saveApiparam = this.genApiParamPO(saveApiObj.getId(),
        CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName), "实体对象参数",
        BaseParamIn.BODY, BaseType.PO, className);
    saveApiparam.setSort(0);
    apiParamRepo.save(saveApiparam);
    // 更新接口
    ApiObjPO updateApiObj = this.genApiObjPO(apiBaseId, tableId, tableName, tableComment,
        BaseDTO.ResultDTO.toString(), null, className, dbname, dbcount,
        "s/{".concat(tableParamName.concat("Id}")), Constants.API_PRODUCES, "修改",
        RequestMethod.PUT.toString());
    updateApiObj = apiObjRepo.save(updateApiObj);
    // 更新接口参数 - id参数
    ApiParamPO updateIdApiparam =
        this.genApiParamPO(updateApiObj.getId(), tableParamName.concat("Id"),
            tableParamName.concat("对象id"), BaseParamIn.PATH, BaseType.BASE, "String");
    updateIdApiparam.setSort(0);
    apiParamRepo.save(updateIdApiparam);
    // 更新接口参数 - 实体参数
    ApiParamPO updateEntityApiparam = this.genApiParamPO(updateApiObj.getId(),
        CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName), "实体对象参数",
        BaseParamIn.BODY, BaseType.PO, className);
    updateIdApiparam.setSort(1);
    apiParamRepo.save(updateEntityApiparam);
    // jsonschema接口
    ApiObjPO schemaApiObj = this.genApiObjPO(apiBaseId, tableId, tableName, tableComment,
        BaseDTO.ResultJsonSchemaDTO.toString(), className, className, dbname,
        dbcount, "s", "application/schema+json", "的json-schema", RequestMethod.GET.toString());
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
  private ApiObjPO genApiObjPO(String apiBaseId, String tableId, String tableName,
      String tableComment, String dtoName, String genericName, String className, String dbname,
      Long dbcount, String urlPostfix, String produces, String description, String requestMethod) {
    ApiObjPO apiObj = new ApiObjPO();
    apiObj.setApiBaseId(apiBaseId);
    String urlPrefix = "";
    if (dbcount > 1) {
      urlPrefix = dbname.concat("/");
      apiObj.setPrefixName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, dbname));
    }
    apiObj.setUri("/".concat(urlPrefix).concat(tableName).concat(urlPostfix));
    String nameUnderscore = urlPrefix.concat(tableName).concat(urlPostfix).replace("/", "_")
        .replaceAll("\\{((?!\\}).)*\\}", "");
    apiObj.setName(
        CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, nameUnderscore.toLowerCase())
            .concat(StringUtils.capitalize(requestMethod.toLowerCase())));
    apiObj.setRequestMethod(requestMethod);
    apiObj.setResponseObjName(dtoName);
    if (null != genericName) {
      apiObj.setResponseObjGenericType(BaseType.PO.toString().toLowerCase());
      apiObj.setResponseObjGenericFormat(genericName);
    }
    if (StringUtils.isNotBlank(tableComment)) {
      apiObj.setSummary(tableComment.concat(description));
    } else {
      apiObj.setSummary("实体".concat(className).concat(description));
    }
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
  private ApiParamPO genApiParamPO(String apiObjId, String name, String description,
      BaseParamIn form, BaseType type, String format) {
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
