package com.changan.code.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.changan.code.common.Constants;
import com.changan.code.config.property.ApiDescriptionModel;
import com.changan.code.config.property.ApiProperties;
import com.changan.code.entity.ApiObjPO;
import com.changan.code.entity.ApiParamPO;
import com.changan.code.entity.TableRelationPO;
import com.changan.code.exception.CodeCommonException;
import com.changan.code.repository.ApiObjRepository;
import com.changan.code.repository.ApiParamRepository;
import com.changan.code.service.IApiObjService;
import com.changan.code.service.IApiParamService;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;

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
   * 注入需要保存的属性
   */
  @Autowired
  private ApiProperties apiProperties;


  /**
   * 保存apiObj,若api_base_id相同，
   */
  @Override
  @Transactional("jpaTransactionManager")
  public ApiObjPO saveApiObj(ApiObjPO apiObj) {
    // 查询重复数
    int sameCount = apiObjRepo.countByApiBaseIdAndUriAndRequestMethodAndDelFlag(apiObj.getApiBaseId(),
        apiObj.getUri(), apiObj.getRequestMethod(), Constants.DATA_IS_NORMAL);
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
            apiObjRepo.findByApiBaseIdAndUriAndRequestMethod(apiObj.getApiBaseId(), apiObj.getUri(), apiObj.getRequestMethod());
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
      // 批量保存参数
      apiParamService.saveApiParam(apiObj.getApiParams(), updateApiObj.getId());
      // 保存apiObj
      return apiObjRepo.save(updateApiObj.setArrayType());
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
   * 根据id删除ApiObj物理删除
   */
  @Override
  @Transactional("jpaTransactionManager")
  public void deleteApiObj(String id) {
    // 删除apiObj下的参数
    apiParamService.deleteByApiObjId(id);
    // 删除apiObj
    apiObjRepo.delete(id);
  }


  /**
   * 自动创建crud的api
   * 
   */
  @Override
  public void createAutoCrudApi(String apiBaseId, String tableId, String tableName,
      String tableComment, String dtoName, String className, String dbname, Long dbcount,
      List<String> relations) {
    String urlPrefix = "";
    String prefixName = "";
    if (dbcount > 1) {
      // 多数据源时
      urlPrefix = "/".concat(dbname);
      prefixName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, dbname);
    }
    // 解析需要用到的参数
    Map<String, String> replaceMap = Maps.newHashMap();
    // uri前缀
    replaceMap.put("urlPrefix", urlPrefix);
    // 驼峰表名
    String tableParamName =
        CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName.toLowerCase());
    replaceMap.put("tableParamName", tableParamName);
    // 下划线表名
    replaceMap.put("tableName", tableName);
    // dto名称
    replaceMap.put("dtoName", dtoName);
    // 驼峰实体名名称(有数据库前缀)
    replaceMap.put("className", className);
    // 是否是高级查询实体crud
    boolean isSeniorCrud = (null != relations && !relations.isEmpty());
    if (isSeniorCrud) {
      // 高级查询实体crud
      for (String relation : relations) {
        // 添加高级查询相关数据
        replaceMap.put("relation", relation);
        // 驼峰
        replaceMap.put("relationUpper",
            CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, relation.toLowerCase()));
        for (ApiDescriptionModel model : apiProperties.getGeneratedModal().getSeniorApiModels()) {
          // 判断是否已经有jsonSchema
          Long apiCount =
              apiObjRepo.countByApiBaseIdAndGenBasedTableIdAndGenRelatedTableIdAndProduces(
                  apiBaseId, tableId, Constants.API_SENIOR_TAG, "application/schema+json");
          // 如果存在jsonSchema路径则跳过jsonSchema生成
          if (apiCount.intValue() > 0 && "entityJsonSchema".equals(model.getApiModelName())) {
            continue;
          }
          // 保存
          this.saveApiData(apiBaseId, tableId, prefixName, Constants.API_SENIOR_TAG, replaceMap,
              model);
        }
      }
    } else {
      // 单表crud
      for (ApiDescriptionModel model : apiProperties.getGeneratedModal().getNormalApiModels()) {
        this.saveApiData(apiBaseId, tableId, prefixName, null, replaceMap, model);
      }
    }
  }


  /**
   * 自动生成关联关系crud api
   * 
   */
  @Override
  public void createAutoCruApiForRelation(String apiBaseId, TableRelationPO tableRelation,
      String dtoName, String dbname, Long dbcount) {
    String urlPrefix = "";
    String prefixName = "";
    if (dbcount > 1) {
      // 多数据源时
      urlPrefix = dbname.concat("/");
      prefixName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, dbname);
    }
    // 解析需要用到的参数
    Map<String, String> replaceMap = Maps.newHashMap();
    // uri前缀
    replaceMap.put("urlPrefix", urlPrefix);
    // 驼峰表名
    replaceMap.put("masterTableParamName", tableRelation.getMasterTableNameLower());
    replaceMap.put("slaveTableParamName", tableRelation.getSlaveTableNameLower());
    replaceMap.put("slaveTableParamNameUpper", tableRelation.getSlaveTableNameCap());
    // 驼峰关联字段名
    replaceMap.put("masterColumnParamName", tableRelation.getMasterColumnNameCap());
    replaceMap.put("slaveColumnParamName", tableRelation.getSlaveColumnNameCap());
    // 下划线表名
    replaceMap.put("masterTableName", tableRelation.getMasterTableName());
    replaceMap.put("slaveTableName", tableRelation.getSlaveTableName());
    // dto名称
    replaceMap.put("dtoName", dtoName);
    // 驼峰实体名名称(有数据库前缀)
    replaceMap.put("slaveClassName", tableRelation.getSlaveClassName());
    // 保存数据
    for (ApiDescriptionModel model : apiProperties.getGeneratedModal().getRestApiModels()) {
      if ("One to One".equals(tableRelation.getRelation())
          && model.getApiModelName().contains("OneToOne")) {
        // 如果是一对一关系则没有列表
        this.saveApiData(apiBaseId, tableRelation.getMasterTableId(), prefixName,
            tableRelation.getSlaveTableId(), replaceMap, model);
      }
      if (!"One to One".equals(tableRelation.getRelation())
          && model.getApiModelName().contains("OneToMany")) {
        // 如果是一对多关系则没有列表
        this.saveApiData(apiBaseId, tableRelation.getMasterTableId(), prefixName,
            tableRelation.getSlaveTableId(), replaceMap, model);
      }
    }
  }

  /**
   * 解析数据
   * 
   * @param replaceMap
   * @param apiElement
   * @return
   */
  private String analyzeApiInfo(Map<String, String> replaceMap, String apiElement) {
    String tempStr = "" + apiElement;
    for (String key : replaceMap.keySet()) {
      tempStr = tempStr.replace("${".concat(key).concat("}"), replaceMap.get(key));
    }
    return tempStr;
  }

  /**
   * 保存api数据
   * 
   * @param apiBaseId
   * @param tableId
   * @param tableName
   * @param prefixName
   * @param dtoName
   * @param replaceMap
   */
  private void saveApiData(String apiBaseId, String tableId, String prefixName,
      String relatedTableId, Map<String, String> replaceMap, ApiDescriptionModel model) {
    // 生成api并保存到数据库
    // 保存api
    ApiObjPO apiobj = this.genApiObjPO(apiBaseId, tableId, prefixName,
        this.analyzeApiInfo(replaceMap, model.getTag()),
        this.analyzeApiInfo(replaceMap, model.getUri()),
        this.analyzeApiInfo(replaceMap, model.getMethodName()),
        this.analyzeApiInfo(replaceMap, model.getResponseDTO()),
        this.analyzeApiInfo(replaceMap, model.getDescription()),
        this.analyzeApiInfo(replaceMap, model.getResponseGenericType()),
        this.analyzeApiInfo(replaceMap, model.getResponseGenericName()),
        this.analyzeApiInfo(replaceMap, model.getProduces()),
        this.analyzeApiInfo(replaceMap, model.getRequestMethod()), relatedTableId,
        this.analyzeApiInfo(replaceMap, model.getConsumes()));
    apiobj = apiObjRepo.save(apiobj);
    // 保存参数
    if (null != model.getApiParams()) {
      for (int i = 0; i < model.getApiParams().size(); i++) {
        ApiParamPO apiparam = this.genApiParamPO(apiobj.getId(),
            this.analyzeApiInfo(replaceMap, model.getApiParams().get(i).getName()),
            this.analyzeApiInfo(replaceMap, model.getApiParams().get(i).getDescription()),
            this.analyzeApiInfo(replaceMap, model.getApiParams().get(i).getParamForm()),
            this.analyzeApiInfo(replaceMap, model.getApiParams().get(i).getParamType()),
            this.analyzeApiInfo(replaceMap, model.getApiParams().get(i).getParamFormat()));
        // 设置顺序
        apiparam.setSort(i);
        apiParamRepo.save(apiparam);
      }
    }
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
  private ApiParamPO genApiParamPO(String apiObjId, String name, String description, String form,
      String type, String format) {
    ApiParamPO apiparam = new ApiParamPO();
    apiparam.setApiObjId(apiObjId);
    apiparam.setName(name);
    apiparam.setDescription(description);
    apiparam.setForm(form);
    apiparam.setType(type);
    apiparam.setFormat(format);
    return apiparam;
  }

  /**
   * 生成api
   * 
   * @param apiBaseId api版本id
   * @param tableId 对应的表id
   * @param tableName 对应的表名
   * @param prefixName 数据库名
   * @param uri
   * @param name 对应controller方法名
   * @param dtoName 返回实体名
   * @param desc 描述
   * @param genericType 泛型类型
   * @param genericName 泛型实体名称
   * @param produces
   * @param requestMethod
   * @param relateTableId 关联的表名
   * @return
   */
  private ApiObjPO genApiObjPO(String apiBaseId, String tableId, String prefixName, String tag,
      String uri, String name, String dtoName, String desc, String genericType, String genericName,
      String produces, String requestMethod, String relateTableId, String consumes) {
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
      apiObj.setResponseObjGenericType(genericType);
      apiObj.setResponseObjGenericFormat(genericName);
    }
    apiObj.setSummary(desc);
    apiObj.setDescription(desc);
    apiObj.setProduces(produces);
    apiObj.setTag(tag);
    apiObj.setGenBasedTableId(tableId);
    apiObj.setConsumes(consumes);
    if (StringUtils.isNotBlank(relateTableId)) {
      apiObj.setGenRelatedTableId(relateTableId);
    }
    return apiObj;
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

  @Override
  public Long deleteByGenBasedTableIdIn(List<String> genBasedTableIds) {
    return apiObjRepo.deleteByGenBasedTableIdIn(genBasedTableIds);
  }

  // 根据table的id获得apiobj列表
  @Override
  public List<ApiObjPO> findAllApiObjByTableId(String tableId) {
    return apiObjRepo.findByGenBasedTableIdAndDelFlag(tableId, Constants.DATA_IS_NORMAL);
  }

}
