package com.changan.code.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;

import com.changan.code.common.BaseDTO;
import com.changan.code.common.BaseParamIn;
import com.changan.code.common.BaseType;
import com.changan.code.common.Constants;
import com.changan.code.entity.ApiObjPO;
import com.changan.code.entity.ApiParamPO;
import com.changan.code.entity.TableRelationPO;
import com.changan.code.exception.CodeCommonException;
import com.changan.code.repository.ApiObjRepository;
import com.changan.code.repository.ApiParamRepository;
import com.changan.code.service.IApiObjService;
import com.changan.code.service.IApiParamService;
import com.changan.code.utils.GeneralUtils;
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
  public void createAutoCrudApi(String apiBaseId, String tableId, String tableName,
      String tableComment, String dtoName, String className, String dbname, Long dbcount,
      List<String> relations) {
    String urlPrefix = "";
    String prefixName = "";
    if (dbcount > 1) {
      urlPrefix = dbname.concat("/");
      prefixName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, dbname);
    }
    // 参数
    String tableParamName =
        CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName.toLowerCase());
    // 基本uri
    String baseuri = "/".concat(urlPrefix).concat(tableName).concat("s");
    // 基础方法名
    String baseName = tableParamName.concat("s");
    // 是否是高级查询实体crud
    boolean isSeniorCrud = (null != relations && !relations.isEmpty());
    if (isSeniorCrud) {
      for (String relation : relations) {
        // 分页列表列表接口
        ApiObjPO pageApiObj = this.genApiObjPO(apiBaseId, tableId, tableName, prefixName,
            baseuri.concat("/").concat(relation).concat("/pages"),
            baseName
                .concat(
                    CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, relation.toLowerCase()))
                .concat("Pages")
                .concat(GeneralUtils.toCapFirstLetter(RequestMethod.POST.toString())),
            BaseDTO.ResultPageDTO.toString(), "实体".concat(className).concat("分页列表"), BaseType.DTO,
            className, Constants.API_PRODUCES, RequestMethod.POST.toString(),
            Constants.API_SENIOR_TAG);
        pageApiObj = apiObjRepo.save(pageApiObj);
        // 分页列表接口参数
        ApiParamPO pageApiparam = this.genApiParamPO(pageApiObj.getId(), "searchParams", "分页参数",
            BaseParamIn.BODY, BaseType.DTO, BaseDTO.PageDTO.toString());
        pageApiparam.setSort(0);
        apiParamRepo.save(pageApiparam);
      }
    } else {
      // 分页列表列表接口
      ApiObjPO pageApiObj =
          this.genApiObjPO(apiBaseId, tableId, tableName, prefixName, baseuri.concat("/pages"),
              baseName.concat("Pages")
                  .concat(GeneralUtils.toCapFirstLetter(RequestMethod.POST.toString())),
              BaseDTO.ResultPageDTO.toString(), "实体".concat(className).concat("分页列表"), BaseType.PO,
              className, Constants.API_PRODUCES, RequestMethod.POST.toString(), null);
      pageApiObj = apiObjRepo.save(pageApiObj);
      // 分页列表接口参数
      ApiParamPO pageApiparam = this.genApiParamPO(pageApiObj.getId(), "searchParams", "分页参数",
          BaseParamIn.BODY, BaseType.DTO, BaseDTO.PageDTO.toString());
      pageApiparam.setSort(0);
      apiParamRepo.save(pageApiparam);
      // 详情接口
      ApiObjPO showApiObj = this.genApiObjPO(apiBaseId, tableId, tableName, prefixName,
          baseuri.concat("/{").concat(tableParamName).concat("Id}"),
          baseName.concat(GeneralUtils.toCapFirstLetter(RequestMethod.GET.toString())), dtoName,
          "实体".concat(className).concat("详情"), BaseType.PO, null, Constants.API_PRODUCES,
          RequestMethod.GET.toString(), null);
      showApiObj = apiObjRepo.save(showApiObj);
      // 详情接口参数
      ApiParamPO showApiparam = this.genApiParamPO(showApiObj.getId(), tableParamName.concat("Id"),
          tableParamName.concat("对象id"), BaseParamIn.PATH, BaseType.BASE, "String");
      showApiparam.setSort(0);
      apiParamRepo.save(showApiparam);
      // 删除接口
      ApiObjPO delApiObj = this.genApiObjPO(apiBaseId, tableId, tableName, prefixName,
          baseuri.concat("/{").concat(tableParamName).concat("Id}"),
          baseName.concat(GeneralUtils.toCapFirstLetter(RequestMethod.DELETE.toString())), dtoName,
          "实体".concat(className).concat("删除"), BaseType.PO, null, Constants.API_PRODUCES,
          RequestMethod.DELETE.toString(), null);
      delApiObj = apiObjRepo.save(delApiObj);
      // 删除接口参数
      ApiParamPO delApiparam = this.genApiParamPO(delApiObj.getId(), tableParamName.concat("Id"),
          tableParamName.concat("对象id"), BaseParamIn.PATH, BaseType.BASE, "String");
      delApiparam.setSort(0);
      apiParamRepo.save(delApiparam);
      // 新增接口
      ApiObjPO saveApiObj = this.genApiObjPO(apiBaseId, tableId, tableName, prefixName, baseuri,
          baseName.concat(GeneralUtils.toCapFirstLetter(RequestMethod.POST.toString())), dtoName,
          "实体".concat(className).concat("新增"), BaseType.PO, null, Constants.API_PRODUCES,
          RequestMethod.POST.toString(), null);
      saveApiObj = apiObjRepo.save(saveApiObj);
      // 新增接口参数
      ApiParamPO saveApiparam = this.genApiParamPO(saveApiObj.getId(),
          CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName), "实体对象参数",
          BaseParamIn.BODY, isSeniorCrud ? BaseType.DTO : BaseType.PO, className);
      saveApiparam.setSort(0);
      apiParamRepo.save(saveApiparam);
      // 更新接口
      ApiObjPO updateApiObj = this.genApiObjPO(apiBaseId, tableId, tableName, prefixName,
          baseuri.concat("/{").concat(tableParamName).concat("Id}"),
          baseName.concat(GeneralUtils.toCapFirstLetter(RequestMethod.PUT.toString())), dtoName,
          "实体".concat(className).concat("更新"), BaseType.PO, null, Constants.API_PRODUCES,
          RequestMethod.PUT.toString(), null);
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
          BaseParamIn.BODY, isSeniorCrud ? BaseType.DTO : BaseType.PO, className);
      updateEntityApiparam.setSort(1);
      apiParamRepo.save(updateEntityApiparam);
    }
    // jsonschema接口
    ApiObjPO schemaApiObj = this.genApiObjPO(apiBaseId, tableId, tableName, prefixName, baseuri,
        baseName.concat(GeneralUtils.toCapFirstLetter(RequestMethod.GET.toString())),
        BaseDTO.ResultJsonSchemaDTO.toString(), "实体".concat(className).concat("的json-schema"),
        BaseType.PO, null, "application/schema+json", RequestMethod.GET.toString(),
        isSeniorCrud ? Constants.API_SENIOR_TAG : null);
    schemaApiObj = apiObjRepo.save(schemaApiObj);
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

  /**
   * 删除自动创建的crud api
   */
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

  /**
   * 删除自动创建的关联关系crud api
   */
  @Override
  public void deleteRelationCrudApi(String masterTableId, String slaveTableId) {
    // 获取apiObj的id
    List<String> apiobjIds =
        apiObjRepo.findIdByGenBasedTableIdAndGenRelatedTableId(masterTableId, slaveTableId);
    if (null != apiobjIds) {
      for (String id : apiobjIds) {
        // 删除param
        apiParamService.deleteByApiObjId(id);
      }
    }
    // 删除apiObj
    apiObjRepo.deleteByGenBasedTableIdAndGenRelatedTableId(masterTableId, slaveTableId);
  }

  /**
   * 创建关联关系crud api
   */
  @Override
  public void createAutoCruApiForRelation(String apiBaseId, TableRelationPO tableRelation,
      String dtoName, String dbname, Long dbcount) {
    String urlPrefix = "";
    String prefixName = "";
    if (dbcount > 1) {
      urlPrefix = dbname.concat("/");
      prefixName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, dbname);
    }
    // 主表参数
    String masterPathName =
        tableRelation.getMasterTableNameLower().concat(tableRelation.getMasterColumnNameCap());
    // 从表Id
    String slavePathName = tableRelation.getSlaveTableNameLower().concat("Id");
    // 基本uri
    String baseuri = "/".concat(urlPrefix).concat(tableRelation.getMasterTableName()).concat("s")
        .concat("/{").concat(masterPathName).concat("}/").concat(tableRelation.getSlaveTableName())
        .concat("One to One".equals(tableRelation.getRelation()) ? "" : "s");
    // 基础方法名
    String baseName = tableRelation.getMasterTableNameLower().concat("s")
        .concat(tableRelation.getMasterColumnNameCap()).concat(tableRelation.getSlaveTableNameCap())
        .concat("One to One".equals(tableRelation.getRelation()) ? "" : "s");
    if (!"One to One".equals(tableRelation.getRelation())) {
      // page页面
      ApiObjPO pageApiObj = this.genApiObjPO(apiBaseId, tableRelation.getMasterTableId(),
          tableRelation.getMasterTableName(), prefixName, baseuri.concat("/pages"),
          baseName.concat("Pages")
              .concat(GeneralUtils.toCapFirstLetter(RequestMethod.POST.toString())),
          BaseDTO.ResultPageDTO.toString(),
          "从表实体".concat(tableRelation.getSlaveClassName()).concat("分页列表"), BaseType.PO,
          tableRelation.getSlaveClassName(), Constants.API_PRODUCES, RequestMethod.POST.toString(),
          tableRelation.getSlaveTableId());
      pageApiObj = apiObjRepo.save(pageApiObj);
      // 分页列表接口参数 - 主表参数
      ApiParamPO masterApiparam = this.genApiParamPO(pageApiObj.getId(), masterPathName, "主表参数",
          BaseParamIn.PATH, BaseType.BASE, tableRelation.getMasterColumnType());
      masterApiparam.setSort(0);
      apiParamRepo.save(masterApiparam);
      // 分页列表接口参数 - 从表pagedto
      ApiParamPO pageApiparam = this.genApiParamPO(pageApiObj.getId(), "searchParams", "分页参数",
          BaseParamIn.BODY, BaseType.DTO, BaseDTO.PageDTO.toString());
      pageApiparam.setSort(1);
      apiParamRepo.save(pageApiparam);
    }
    // 详情接口
    ApiObjPO showApiObj = this.genApiObjPO(apiBaseId, tableRelation.getMasterTableId(),
        tableRelation.getMasterTableName(), prefixName,
        "One to One".equals(tableRelation.getRelation()) ? baseuri
            : baseuri.concat("/{").concat(tableRelation.getSlaveTableNameLower()).concat("Id}"),
        baseName.concat(GeneralUtils.toCapFirstLetter(RequestMethod.GET.toString())), dtoName,
        "从表实体".concat(tableRelation.getSlaveClassName()).concat("详情"), BaseType.PO, null,
        Constants.API_PRODUCES, RequestMethod.GET.toString(), tableRelation.getSlaveTableId());
    showApiObj = apiObjRepo.save(showApiObj);
    // 详情接口参数 - 主表参数
    ApiParamPO masterApiparamShow = this.genApiParamPO(showApiObj.getId(), masterPathName, "主表参数",
        BaseParamIn.PATH, BaseType.BASE, tableRelation.getMasterColumnType());
    masterApiparamShow.setSort(0);
    apiParamRepo.save(masterApiparamShow);
    if (!"One to One".equals(tableRelation.getRelation())) {
      // 详情接口参数 - 从表id
      ApiParamPO showApiparam = this.genApiParamPO(showApiObj.getId(), slavePathName, "从表id",
          BaseParamIn.PATH, BaseType.BASE, "String");
      showApiparam.setSort(1);
      apiParamRepo.save(showApiparam);
    }
    // 删除接口
    ApiObjPO delApiObj = this.genApiObjPO(apiBaseId, tableRelation.getMasterTableId(),
        tableRelation.getMasterTableName(), prefixName,
        baseuri.concat("/{").concat(tableRelation.getSlaveTableNameLower()).concat("Id}"),
        baseName.concat(GeneralUtils.toCapFirstLetter(RequestMethod.DELETE.toString())), dtoName,
        "从表实体".concat(tableRelation.getSlaveClassName()).concat("删除"), BaseType.PO, null,
        Constants.API_PRODUCES, RequestMethod.DELETE.toString(), tableRelation.getSlaveTableId());
    delApiObj = apiObjRepo.save(delApiObj);
    // 删除接口参数 - 主表参数
    ApiParamPO masterApiparamDel = this.genApiParamPO(delApiObj.getId(), masterPathName, "主表参数",
        BaseParamIn.PATH, BaseType.BASE, tableRelation.getMasterColumnType());
    masterApiparamDel.setSort(0);
    apiParamRepo.save(masterApiparamDel);
    // 删除接口参数 - 从表id
    ApiParamPO delApiparam = this.genApiParamPO(delApiObj.getId(), slavePathName, "从表id",
        BaseParamIn.PATH, BaseType.BASE, "String");
    delApiparam.setSort(1);
    apiParamRepo.save(delApiparam);
    // 新增接口
    ApiObjPO saveApiObj = this.genApiObjPO(apiBaseId, tableRelation.getMasterTableId(),
        tableRelation.getMasterTableName(), prefixName, baseuri,
        baseName.concat(GeneralUtils.toCapFirstLetter(RequestMethod.POST.toString())), dtoName,
        "从表实体".concat(tableRelation.getSlaveClassName()).concat("新增"), BaseType.PO, null,
        Constants.API_PRODUCES, RequestMethod.POST.toString(), tableRelation.getSlaveTableId());
    saveApiObj = apiObjRepo.save(saveApiObj);
    // 新增接口参数 - 主表参数
    ApiParamPO masterApiparamNew = this.genApiParamPO(saveApiObj.getId(), masterPathName, "主表参数",
        BaseParamIn.PATH, BaseType.BASE, tableRelation.getMasterColumnType());
    masterApiparamNew.setSort(0);
    apiParamRepo.save(masterApiparamNew);
    // 新增接口参数 - 实体参数
    ApiParamPO saveApiparam =
        this.genApiParamPO(saveApiObj.getId(), tableRelation.getSlaveTableNameLower(), "从表实体对象参数",
            BaseParamIn.BODY, BaseType.PO, tableRelation.getSlaveClassName());
    saveApiparam.setSort(1);
    apiParamRepo.save(saveApiparam);
    // 更新接口
    ApiObjPO updateApiObj = this.genApiObjPO(apiBaseId, tableRelation.getMasterTableId(),
        tableRelation.getMasterTableName(), prefixName,
        baseuri.concat("/{").concat(tableRelation.getSlaveTableNameLower()).concat("Id}"),
        baseName.concat(GeneralUtils.toCapFirstLetter(RequestMethod.PUT.toString())), dtoName,
        "从表实体".concat(tableRelation.getSlaveClassName()).concat("更新"), BaseType.PO, null,
        Constants.API_PRODUCES, RequestMethod.PUT.toString(), tableRelation.getSlaveTableId());
    updateApiObj = apiObjRepo.save(updateApiObj);
    // 更新接口参数 - 主表参数
    ApiParamPO masterApiparamUpt = this.genApiParamPO(updateApiObj.getId(), masterPathName, "主表参数",
        BaseParamIn.PATH, BaseType.BASE, tableRelation.getMasterColumnType());
    masterApiparamUpt.setSort(0);
    apiParamRepo.save(masterApiparamUpt);
    // 更新接口参数 - 从表id
    ApiParamPO updateIdApiparam = this.genApiParamPO(updateApiObj.getId(), slavePathName, "从表id",
        BaseParamIn.PATH, BaseType.BASE, "String");
    updateIdApiparam.setSort(1);
    apiParamRepo.save(updateIdApiparam);
    // 更新接口参数 - 从表实体参数
    ApiParamPO updateEntityApiparam =
        this.genApiParamPO(updateApiObj.getId(), tableRelation.getSlaveTableNameLower(), "从表实体对象参数",
            BaseParamIn.BODY, BaseType.PO, tableRelation.getSlaveClassName());
    updateEntityApiparam.setSort(2);
    apiParamRepo.save(updateEntityApiparam);
  }

  /**
   * 生成api
   * 
   * @param apiBaseId
   * @param tableId
   * @param uri
   * @param name
   * @param dtoName
   * @param desc
   * @param genericName
   * @param produces
   * @param description
   * @param requestMethod
   * @return
   */
  private ApiObjPO genApiObjPO(String apiBaseId, String tableId, String tableName,
      String prefixName, String uri, String name, String dtoName, String desc, BaseType genericType,
      String genericName, String produces, String requestMethod, String relateTableId) {
    ApiObjPO apiObj = new ApiObjPO();
    apiObj.setApiBaseId(apiBaseId);
    if (StringUtils.isNotBlank(prefixName)) {
      apiObj.setPrefixName(prefixName);
    }
    apiObj.setUri(uri);
    apiObj.setName(name);
    apiObj.setRequestMethod(requestMethod);
    apiObj.setResponseObjName(dtoName);
    if (null != genericName) {
      apiObj.setResponseObjGenericType(genericType.name().toLowerCase());
      apiObj.setResponseObjGenericFormat(genericName);
    }
    apiObj.setSummary(desc);
    apiObj.setDescription(desc);
    apiObj.setProduces(produces);
    apiObj.setTag(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName));
    apiObj.setGenBasedTableId(tableId);
    if (StringUtils.isNotBlank(relateTableId)) {
      apiObj.setGenRelatedTableId(relateTableId);
    }
    return apiObj;
  }

}
