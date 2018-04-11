/**
 * 
 */
package org.hotpotmaterial.code.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hotpotmaterial.anywhere.common.utils.FileUtils;
import org.hotpotmaterial.anywhere.common.utils.FreeMarkerUtils;
import org.hotpotmaterial.anywhere.common.utils.StringUtils;
import org.hotpotmaterial.code.common.BaseDTO;
import org.hotpotmaterial.code.common.BaseFormat;
import org.hotpotmaterial.code.common.BaseParamIn;
import org.hotpotmaterial.code.common.BaseProfile;
import org.hotpotmaterial.code.common.BaseType;
import org.hotpotmaterial.code.common.Constants;
import org.hotpotmaterial.code.common.ParamerConstant;
import org.hotpotmaterial.code.common.component.Dictionary;
import org.hotpotmaterial.code.common.component.Excel;
import org.hotpotmaterial.code.common.component.UiConfig;
import org.hotpotmaterial.code.common.template.AdviceFile;
import org.hotpotmaterial.code.common.template.ConfigFile;
import org.hotpotmaterial.code.common.template.DAOFile;
import org.hotpotmaterial.code.common.template.DTOFile;
import org.hotpotmaterial.code.common.template.DictFile;
import org.hotpotmaterial.code.common.template.EntityFile;
import org.hotpotmaterial.code.common.template.ExcelFile;
import org.hotpotmaterial.code.common.template.GeneratorConfigFile;
import org.hotpotmaterial.code.common.template.JPAFile;
import org.hotpotmaterial.code.common.template.MvcFile;
import org.hotpotmaterial.code.common.template.ServiceFile;
import org.hotpotmaterial.code.common.template.ServiceImplFile;
import org.hotpotmaterial.code.common.template.UiCode;
import org.hotpotmaterial.code.common.template.UiFile;
import org.hotpotmaterial.code.config.property.GenerateProperties;
import org.hotpotmaterial.code.dto.ApiServiceName;
import org.hotpotmaterial.code.dto.RelationAnnotation;
import org.hotpotmaterial.code.dto.SeniorDtoAttribute;
import org.hotpotmaterial.code.dto.SeniorDtoRelation;
import org.hotpotmaterial.code.dto.Template;
import org.hotpotmaterial.code.entity.ApiBasePO;
import org.hotpotmaterial.code.entity.ApiObjPO;
import org.hotpotmaterial.code.entity.ApiParamPO;
import org.hotpotmaterial.code.entity.ColumnPO;
import org.hotpotmaterial.code.entity.DatasourcePO;
import org.hotpotmaterial.code.entity.ProjectPO;
import org.hotpotmaterial.code.entity.TablePO;
import org.hotpotmaterial.code.entity.TableRelationPO;
import org.hotpotmaterial.code.entity.TableSeniorRelationPO;
import org.hotpotmaterial.code.entity.TransferObjFieldPO;
import org.hotpotmaterial.code.entity.TransferObjPO;
import org.hotpotmaterial.code.service.IGenerateService;
import org.hotpotmaterial.code.utils.GeneratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wenxing
 *
 */
@Service
@Slf4j
public class GenerateServiceImpl implements IGenerateService {

  @Autowired
  private GenerateProperties genProperties;

  /**
   * 
   * @param tpl
   * @param model
   * @param isReplaceFile
   * @param subProjectName
   * @return
   */
  @Override
  public String generateToFile(String pathPostfix, String basePath, Template tpl,
      Map<String, Object> model, boolean isReplaceFile) {
    String fileName;
    // 文件名
    fileName =
        GeneratorUtils.getProjectPath(null == basePath ? genProperties.getProjectPath() : basePath,
            (null == basePath
                ? ((String) model.get(ParamerConstant.CPNS_PROJECT_NAME) + "_" + pathPostfix)
                : Constants.MYBATIS_GEN_CONFIG_ROOT))
            + File.separator
            + StringUtils.replaceEach(FreeMarkerUtils.renderString(tpl.getFilePath() + "/", model),
                new String[] {"//", "/", "."},
                new String[] {File.separator, File.separator, File.separator})
            + FreeMarkerUtils.renderString(tpl.getFileName(), model);
    log.debug(" fileName === " + fileName);

    // 获取生成文件内容
    String content = FreeMarkerUtils.renderString(StringUtils.trimToEmpty(tpl.getContent()), model);
    log.debug(" content === \r\n" + content);

    // 如果选择替换文件，则删除原文件
    if (isReplaceFile) {
      FileUtils.deleteFile(fileName);
    }

    // 创建并写入文件
    if (FileUtils.createFile(fileName)) {
      FileUtils.writeToFile(fileName, content, true);
      log.debug(" file create === " + fileName);
      return "生成成功：" + fileName + "<br/>";
    } else {
      log.debug(" file extents === " + fileName);
      return "文件已存在：" + fileName + "<br/>";
    }
  }

  @Override
  public String generateToUIFile(String basePath, Template tpl, Map<String, Object> model,
      boolean isReplaceFile) {
    String fileName;
    fileName = basePath == null ? genProperties.getProjectUiPath()
        : basePath + File.separator
            + StringUtils.replaceEach(FreeMarkerUtils.renderString(tpl.getFilePath() + "/", model),
                new String[] {"//", "/", "."},
                new String[] {File.separator, File.separator, File.separator})
            + FreeMarkerUtils.renderString(tpl.getFileName(), model);
    log.debug(" fileName === " + fileName);

    // 获取生成文件内容
    String content = FreeMarkerUtils.renderString(StringUtils.trimToEmpty(tpl.getContent()), model);
    log.debug(" content === \r\n" + content);

    // 如果选择替换文件，则删除原文件
    if (isReplaceFile) {
      FileUtils.deleteFile(fileName);
    }

    // 创建并写入文件
    if (FileUtils.createFile(fileName)) {
      FileUtils.writeToFile(fileName, content, true);
      log.debug(" file create === " + fileName);
      return "生成成功：" + fileName + "<br/>";
    } else {
      log.debug(" file extents === " + fileName);
      return "文件已存在：" + fileName + "<br/>";
    }
  }

  @Override
  public void generateConfigFiles(String pathPostfix, ProjectPO project,
      List<DatasourcePO> datasources, List<ApiBasePO> apiBases, ApiBasePO firstApiBase,
      List<String> components) {
    // 构建basePath数组
    List<String> basePaths = new ArrayList<>();
    for (ApiBasePO apiBase : apiBases) {
      basePaths.add(apiBase.getBasePath().toLowerCase());
    }
    // 添加model
    Map<String, Object> model = Maps.newHashMap();
    model.put("packageName", project.getPackages().toLowerCase());
    model.put("basePaths", basePaths);
    model.put("mainpath", firstApiBase == null ? "/"
        : ("/" + firstApiBase.getBasePath().toLowerCase().split("/")[1]));
    model.put("projectDesc", StringUtils.trimToEmpty(project.getDescription()));
    model.put("datasourcelist", datasources);
    if (!datasources.isEmpty()) {
      // 默认第一个
      model.put("datasourceName", datasources.get(0).getPackageName());
      model.put("dbtype", datasources.get(0).getDbtype());
    }
    model.put("moduleName", "");
    model.put("projectName", project.getName());
    model.put("hasMysql", "0");
    model.put("hasOracle", "0");
    model.put("components", project.getComponents().split(","));
    model.put("dbcount", null == datasources ? 0 : datasources.size());
    // 生成数据库配置文件
    if (null != datasources) {
      Template dbTpl =
          GeneratorUtils.fileToObject(ConfigFile.datasourceConfig.getPath(), Template.class);
      int i = 0;
      for (DatasourcePO datasource : datasources) {
        if (Constants.DATASOURCE_MYSQL.equals(datasource.getDbtype())) {
          model.put("hasMysql", "1");
        }
        if (Constants.DATASOURCE_ORACLE.equals(datasource.getDbtype())) {
          model.put("hasOracle", "1");
        }
        model.put("datasource", datasource);
        this.generateToFile(pathPostfix, null, dbTpl, model, true);
        // 只在第一个数据源添加
        if (i == 0) {
          model.put("servuceModuleName", datasource.getPackageName());
        }
        i++;
      }
    }
    // 生成普通配置文件
    for (ConfigFile configFile : ConfigFile.values()) {
      if (!ConfigFile.datasourceConfig.equals(configFile)
          && !ConfigFile.applicationYml.equals(configFile)) {
        this.generateToFile(pathPostfix, null,
            GeneratorUtils.fileToObject(configFile.getPath(), Template.class), model, true);
      }
    }
    // 生成yml配置文件
    for (BaseProfile profile : BaseProfile.values()) {
      model.put("postfix", profile.equals(BaseProfile.main) ? "" : "-".concat(profile.name()));
      this.generateToFile(pathPostfix, null,
          GeneratorUtils.fileToObject(ConfigFile.applicationYml.getPath(), Template.class), model,
          true);
    }
    if (components.contains(UiConfig.uiConfigEnabled.toString())) {
      // 生成ui配置模块文件
      for (UiFile uiFile : UiFile.values()) {
        this.generateToFile(pathPostfix, null,
            GeneratorUtils.fileToObject(uiFile.getPath(), Template.class), model, true);
      }
    }
    if (components.contains(Excel.excelEnabled.toString())) {
      // 生成excel导出模块文件
      for (ExcelFile excelFile : ExcelFile.values()) {
        this.generateToFile(pathPostfix, null,
            GeneratorUtils.fileToObject(excelFile.getPath(), Template.class), model, true);
      }
    }
    if (components.contains(Dictionary.dictionary.toString())) {
      // 生成字典表模块文件
      for (DictFile dictFile : DictFile.values()) {
        this.generateToFile(pathPostfix, null,
            GeneratorUtils.fileToObject(dictFile.getPath(), Template.class), model, true);
      }
    }
  }

  /**
   * 自动生成entity
   */
  @Override
  public void generateEntityFiles(String pathPostfix, String moduleName, String projectName,
      String packageName, TablePO table, List<ColumnPO> columns) {
    // 定义需要传入的包
    HashSet<String> imports = new HashSet<>();
    for (ColumnPO column : columns) {
      if ("createdAt".equals(column.getJavaField()) || "updatedAt".equals(column.getJavaField())
          || "id".equals(column.getJavaField())) {
        continue;
      }
      if ("1".equals(column.getReadOnly())) {
        imports.add("readOnly");
      }
      if ("0".equals(column.getIsNullable())) {
        imports.add("isNullable");
      }
      if (!"".equals(column.getPattern()) && column.getPattern() != null) {
        imports.add("pattern");
      }
      if (!"".equals(column.getDictTypeCode()) && column.getDictTypeCode() != null) {
        imports.add("dict");
      }
      if ("String".equals(column.getJavaType())
          && (column.getMax() != null || column.getMin() != null)) {
        imports.add("size");
      }
      if (!"String".equals(column.getJavaType()) && column.getMax() != null) {
        imports.add("constraintsMax");
      }
      if (!"String".equals(column.getJavaType()) && column.getMin() != null) {
        imports.add("constraintsMin");
      }
      if ("Date".equals(column.getJavaType())) {
        imports.add("Date");
        imports.add("JsonFormat");
      }
      if ("Timestamp".equals(column.getJavaType())) {
        imports.add("Timestamp");
        imports.add("JsonFormat");
      }
      if ("BigDecimal".equals(column.getJavaType())) {
        imports.add("BigDecimal");
      }
    }
    List<RelationAnnotation> relAnns = Lists.newArrayList();
    // 添加主从关系
    if (null != table.getSlaveTableRelations()) {
      for (TableRelationPO tableRelation : table.getSlaveTableRelations()) {
        RelationAnnotation ann = new RelationAnnotation();
        boolean isCollection = true;
        if ("One to One".equals(tableRelation.getRelation())) {
          ann.setRelationType("@OneToOne");
          imports.add("OneToOne");
          isCollection = false;
        } else {
          ann.setRelationType("@OneToMany");
          imports.add("OneToMany");
        }
        imports.add("masterRel");
        ann.setCollection(isCollection);
        ann.setMappedBy(table.getTableAttrNameLower());
        ann.setAttrName(tableRelation.getSlaveTableNameLower().concat(isCollection ? "s" : ""));
        ann.setAttrPOName(tableRelation.getSlaveTableNameCap().concat("PO"));
        relAnns.add(ann);
      }
    }

    // 添加从主关系
    if (null != table.getMasterTableRelations()) {
      for (TableRelationPO tableRelation : table.getMasterTableRelations()) {
        RelationAnnotation ann = new RelationAnnotation();
        boolean isCollection = false;
        if ("One to One".equals(tableRelation.getRelation())) {
          ann.setRelationType("@OneToOne");
          imports.add("OneToOne");
        } else {
          ann.setRelationType("@ManyToOne");
          imports.add("ManyToOne");
        }
        imports.add("slaveRel");
        ann.setCollection(isCollection);
        ann.setAttrName(tableRelation.getMasterTableNameLower().concat(isCollection ? "s" : ""));
        ann.setAttrPOName(tableRelation.getMasterTableNameCap().concat("PO"));
        ann.setColumnName(tableRelation.getSlaveColumnName());
        ann.setRefColumnName(tableRelation.getMasterColumnName());
        relAnns.add(ann);
      }
    }
    // 添加model
    Map<String, Object> model = Maps.newHashMap();
    model.put("packageName", packageName);
    model.put("className", table.getClassName());
    model.put("columns", columns);
    model.put("projectName", projectName);
    model.put("moduleName", moduleName);
    model.put("imports", imports);
    model.put("relAnns", relAnns);
    model.put("tableName", table.getName());
    model.put("tableComments", table.getComments() == null ? table.getClassName().concat("实体")
        : table.getComments().concat("实体"));
    // 生成实体文件
    this.generateToFile(pathPostfix, null,
        GeneratorUtils.fileToObject(EntityFile.entity.getPath(), Template.class), model, true);

  }

  /**
   * 自动生成DTO
   */
  @Override
  public void generateDTOFiles(String pathPostfix, String projectName, String packageName,
      TransferObjPO transferObj, List<TransferObjFieldPO> transferObjFileds,
      List<SeniorDtoRelation> relations, String seniorName) {
    // 添加model
    Map<String, Object> model = Maps.newHashMap();
    // 注解引入包
    HashSet<String> imports = new HashSet<>();
    // dto，entity引入包
    HashSet<String> typeImports = new HashSet<>();
    for (TransferObjFieldPO transferObjFiled : transferObjFileds) {
      if ("1".equals(transferObjFiled.getReadOnly())) {
        imports.add("readOnly");
      }
      if ("1".equals(transferObjFiled.getIsNullable())) {
        imports.add("isNullable");
      }
      if ("".equals(transferObjFiled.getPattern()) || transferObjFiled.getPattern() != null) {
        imports.add("pattern");
      }
      if ("String".equals(transferObjFiled.getType())
          && (transferObjFiled.getMax() != null || transferObjFiled.getMin() != null)) {
        imports.add("size");
      }
      if (!"String".equals(transferObjFiled.getType()) && (transferObjFiled.getMax() != null)) {
        imports.add("constraintsMax");
      }
      if (!"String".equals(transferObjFiled.getType()) && (transferObjFiled.getMin() != null)) {
        imports.add("constraintsMin");
      }
      if (("array".equals(transferObjFiled.getType())
          && "po".equals(transferObjFiled.getArrayType()))
          || ("po").equals(transferObjFiled.getType())) {
        typeImports.add("entity." + transferObjFiled.getFormat());
      }
      if (("array".equals(transferObjFiled.getType())
          && "dto".equals(transferObjFiled.getArrayType()))
          || ("dto").equals(transferObjFiled.getType())) {
        typeImports.add("dto." + transferObjFiled.getFormat());
      }
      if ("base".equals(transferObjFiled.getType())
          && "Date".equals(transferObjFiled.getFormat())) {
        imports.add("Date");
      }
      if ("base".equals(transferObjFiled.getType())
          && "Timestamp".equals(transferObjFiled.getFormat())) {
        imports.add("Timestamp");
      }
      if ("BigDecimal".equals(transferObjFiled.getFormat())) {
        imports.add("BigDecimal");
      }
      if ("array".equals(transferObjFiled.getType())) {
        imports.add("array");
      }
    }
    model.put("packageName", packageName);
    model.put("transferObj", transferObj);
    model.put("transferObjFileds", transferObjFileds);
    model.put("projectName", projectName);
    model.put("BaseDTO", BaseDTO.values());
    model.put("imports", imports);
    model.put("typeImports", typeImports);
    model.put("isSenior", Constants.IS_ACTIVE.equals(transferObj.getIsSenior()));
    model.put("seniorRelations", relations);
    model.put("seniorName", seniorName);
    // 生成DTO文件
    this.generateToFile(pathPostfix, null,
        GeneratorUtils.fileToObject(DTOFile.dto.getPath(), Template.class), model, true);
  }

  /**
   * 自动生成DAO
   */
  @Override
  public void generateDAOFiles(String pathPostfix, String moduleName, String projectName,
      String packageName, String tableName, List<TableSeniorRelationPO> relations,
      List<SeniorDtoAttribute> attrs, List<SeniorDtoRelation> relationMethods) {
    // 添加model
    Map<String, Object> model = Maps.newHashMap();
    String className =
        CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());
    model.put("className", className);
    // 首字母小写驼峰
    model.put("lowerName", StringUtils.uncapitalize(className));
    model.put("packageName", packageName);
    model.put("moduleName", moduleName);
    model.put("projectName", projectName);
    model.put("relations", relations);
    model.put("masterTableName", relations.get(0).getMasterTableName());
    model.put("masterTableAttrName", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,
        relations.get(0).getMasterTableName().toLowerCase()));
    model.put("attrList", attrs);
    model.put("relationMethods", relationMethods);
    // 生成DAO文件
    this.generateToFile(pathPostfix, null,
        GeneratorUtils.fileToObject(DAOFile.dao.getPath(), Template.class), model, true);
    // 生成mapper文件
    this.generateToFile(pathPostfix, null,
        GeneratorUtils.fileToObject(DAOFile.mapper.getPath(), Template.class), model, true);
  }

  /**
   * 自定生成service
   */
  @Override
  public void generateIServiceAndServiceImpl(String pathPostfix, String moduleName,
      String projectName, String packageName, String tableName, List<TableRelationPO> tableRelation,
      List<TableSeniorRelationPO> relations, boolean isSenior, List<String> components) {
    // 添加model
    Map<String, Object> model = Maps.newHashMap();
    // 首字母大写
    String className =
        CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());
    model.put("className", className);
    String lowerName =
        CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName.toLowerCase());
    model.put("lowerName", lowerName);
    model.put("packageName", packageName);
    model.put("projectName", projectName);
    model.put("moduleName", moduleName);
    // 如果为null则置空
    if (null == tableRelation) {
      tableRelation = Lists.newArrayList();
    }
    // 如果为null则置空
    if (null == relations) {
      relations = Lists.newArrayList();
    }
    model.put("tableRelations", tableRelation);
    // 检查是否有一对多关联
    boolean hasOneToMany = false;
    // 检查是否有一对一关联
    boolean hasOneToOne = false;
    for (TableRelationPO relation : tableRelation) {
      if (!"One to One".equals(relation.getRelation())) {
        hasOneToMany = true;
      } else {
        hasOneToOne = true;
      }
    }
    model.put("hasOneToMany", hasOneToMany);
    model.put("hasOneToOne", hasOneToOne);
    model.put("seniorRelations", relations);
    model.put("isSenior", isSenior);
    model.put("enableExcel", components.contains(Excel.excelEnabled.toString()));
    // 生成service文件
    this.generateToFile(pathPostfix, null,
        GeneratorUtils.fileToObject(ServiceFile.Service.getPath(), Template.class), model, true);
    // 生成serviceImpl文件
    this.generateToFile(pathPostfix, null,
        GeneratorUtils.fileToObject(ServiceImplFile.ServiceImpl.getPath(), Template.class), model,
        true);
  }

  /**
   * 生成controller的api和Implement
   */
  @Override
  public void generateControllerFiles(String pathPostfix, ProjectPO project, ApiBasePO apibase,
      String controllerName, List<ApiObjPO> apiobjs) {
    // 添加model
    Map<String, Object> model = Maps.newHashMap();
    // project名称
    model.put("projectName", project.getName());
    model.put("packageName", project.getPackages().toLowerCase());
    model.put("basePath", apibase.getBasePath());
    model.put("controllerName", controllerName);
    model.put("moduleName", "");
    model.put("versionName", apibase.getVersionName());
    model.put("apiObjList", apiobjs);
    // 添加api需import的包名
    Set<String> importIFPackSet = Sets.newHashSet();
    List<String> basedtoNames = BaseDTO.getNameList();
    // ResultDTO包名
    importIFPackSet.add(BaseDTO.ResultDTO.getPackageName());
    // 添加impl需import的包名
    Set<String> importImplPackSet = Sets.newHashSet();
    // 添加Service名称
    Set<ApiServiceName> appServiceNameSet = Sets.newHashSet();
    for (ApiObjPO apiobj : apiobjs) {
      // 返回对象包
      if (basedtoNames.contains(apiobj.getResponseObjName())) {
        importIFPackSet.add(BaseDTO.valueOf(apiobj.getResponseObjName()).getPackageName());
      } else {
        importIFPackSet.add(project.getPackages().toLowerCase().concat(".dto.")
            .concat(apiobj.getResponseObjName()));
      }
      // api param
      for (ApiParamPO apiparam : apiobj.getApiParams()) {
        // 设置api param的包
        if (BaseType.ARRAY.equals(BaseType.valueOf(apiparam.getType().toUpperCase()))) {
          importIFPackSet.add("java.util.List");
          setPackageByTypeAndFormat(project, basedtoNames, importIFPackSet, apiparam.getArrayType(),
              apiparam.getFormat());
        } else {
          setPackageByTypeAndFormat(project, basedtoNames, importIFPackSet, apiparam.getType(),
              apiparam.getFormat());
        }
        // 参数类型包
        importIFPackSet
            .add(BaseParamIn.valueOf(apiparam.getForm().toUpperCase()).getAnnotationPack());
      }
      // service name
      if (StringUtils.isNotBlank(apiobj.getServiceName())) {
        ApiServiceName appservicename =
            new ApiServiceName(apiobj.getServiceNameNoPack(), apiobj.getAttrServiceNameNoPack());
        appServiceNameSet.add(appservicename);
        // 添加service包名
        importImplPackSet.add(project.getPackages().toLowerCase().concat(".service.")
            .concat(apiobj.getServiceName()));
      }
      // 返回值泛型包名
      if (StringUtils.isNotBlank(apiobj.getResponseObjGenericType())) {
        if (BaseType.ARRAY
            .equals(BaseType.valueOf(apiobj.getResponseObjGenericType().toUpperCase()))) {
          importImplPackSet.add("java.util.List");
          setPackageByTypeAndFormat(project, basedtoNames, importImplPackSet,
              apiobj.getResponseObjGenericArrayType(), apiobj.getResponseObjGenericFormat());
        } else {
          setPackageByTypeAndFormat(project, basedtoNames, importImplPackSet,
              apiobj.getResponseObjGenericType(), apiobj.getResponseObjGenericFormat());
        }
      }
    }
    // 获得包名list
    model.put("importIFPackageList", importIFPackSet);
    // 获得service
    model.put("appServiceNameList", appServiceNameSet);
    // 获得实现类包名list
    importImplPackSet.addAll(importIFPackSet);
    model.put("importImplPackageList", importImplPackSet);
    // 生成api文件
    this.generateToFile(pathPostfix, null,
        GeneratorUtils.fileToObject(MvcFile.controllerApi.getPath(), Template.class), model, true);
    // 生成impl文件
    this.generateToFile(pathPostfix, null,
        GeneratorUtils.fileToObject(MvcFile.controllerImpl.getPath(), Template.class), model, true);
  }

  /**
   * 根据type和format添加包
   * 
   * @param importIFPackSet
   * @param type
   * @param format
   */
  private static void setPackageByTypeAndFormat(ProjectPO project, List<String> basedtoNames,
      Set<String> importPackSet, String type, String format) {
    // 不同类型包
    switch (BaseType.valueOf(type.toUpperCase())) {
      case BASE:
        if (StringUtils.isNotBlank(BaseFormat.valueOf(format).getPackageName())) {
          importPackSet.add(BaseFormat.valueOf(format).getPackageName());
        }
        break;
      case DTO:
        if (basedtoNames.contains(format)) {
          importPackSet.add(BaseDTO.valueOf(format).getPackageName());
        } else {
          importPackSet.add(project.getPackages().toLowerCase().concat(".dto.").concat(format));
        }
        break;
      case PO:
        importPackSet.add(project.getPackages().toLowerCase().concat(".entity.").concat(format));
        break;
      default:
        break;
    }
  }

  /**
   * 数据库代码生成文件
   * 
   * @param projectName
   * @param packageName
   * @param datasource
   */
  @Override
  public void generateGeneratorConfigFiles(String pathPostfix, String projectName,
      String packageName, DatasourcePO datasource, List<TablePO> tables) {
    // 修改数据库url
    String url = datasource.getDburl().replace("&", "&amp;");
    datasource.setDburl(url);
    // 添加model
    Map<String, Object> model = Maps.newHashMap();
    model.put("projectName", projectName.concat("_").concat(pathPostfix));
    model.put("packageName", packageName);
    model.put("dataSource", datasource);
    model.put("tables", tables);
    model.put("projectPath", genProperties.getProjectPath());
    // 生成xml文件
    this.generateToFile(pathPostfix, genProperties.getMybatisGenlibsPath(), GeneratorUtils
        .fileToObject(GeneratorConfigFile.generatorConfigFile.getPath(), Template.class), model,
        true);
  }

  /**
   * 通过mybatis generator生成mybatis相关文件
   * 
   * @param projectName
   */
  @Override
  public void generateMybatisFiles(String pathPostfix, String projectName) {
    // 项目配置文件目录
    File projectConfigDir =
        new File(genProperties.getMybatisGenlibsPath() + Constants.MYBATIS_GEN_CONFIG_ROOT
            + File.separator + projectName.concat("_").concat(pathPostfix));
    List<String> configfiles = FileUtils.findChildrenList(projectConfigDir, false);
    // 循环执行mybatis generator
    String os = System.getProperty("os.name");
    String cmd = "";
    String shFile = "";
    for (String filename : configfiles) {
      InputStream inputStream = null;
      // 配置文件路径
      String filepath = Constants.MYBATIS_GEN_CONFIG_ROOT + File.separator
          + projectName.concat("_").concat(pathPostfix) + File.separator + filename;
      try {
        if (os.toLowerCase().startsWith("win")) {
          // windows环境执行代码生成
          shFile = Paths.get(genProperties.getMybatisGenlibsPath() + "rungen.bat").toAbsolutePath()
              .toString();
          cmd = "cmd /c start /b " + shFile + " " + filepath;
        } else {
          // linux环境执行代码生成
          shFile = Paths.get(genProperties.getMybatisGenlibsPath() + "rungen.sh").toAbsolutePath()
              .toString();
          cmd = "/bin/sh " + shFile + " " + filepath;
        }
        log.info("开始进行mybatis代码生成任务: {}", cmd);
        Process ps =
            Runtime.getRuntime().exec(cmd, null, new File(genProperties.getMybatisGenlibsPath()));
        inputStream = ps.getInputStream();
        byte[] by = new byte[1024];
        while (inputStream.read(by) != -1) {
          if ((new String(by, "utf-8")).contains("finished successfully")) {
            break;
          }
        }
        inputStream.close();
        log.info("mybatis代码生成任务完成");
      } catch (IOException e) {
        log.error("任务发生错误", e);
      }
    }
  }

  /**
   * 生成JPA Repository
   */
  @Override
  public void generateRepository(String pathPostfix, String module, String projectName,
      String packageName, String name) {
    // 添加model
    Map<String, Object> model = Maps.newHashMap();
    String str = name.toLowerCase();
    // 首字母大写
    String className = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, str);
    model.put("module", module);
    model.put("projectName", projectName);
    model.put("packageName", packageName);
    model.put("className", className);
    // 生成JPA Repository文件
    this.generateToFile(pathPostfix, null,
        GeneratorUtils.fileToObject(JPAFile.repository.getPath(), Template.class), model, true);
  }

  /**
   * 生成JPAService和JPAServiceImpl
   * 
   */
  @Override
  public void generateJPAServiceAndJPAServiceImpl(String pathPostfix, String moduleName,
      String projectName, String packageName, TablePO table, String DTOPackageName) {
    // 添加model
    Map<String, Object> model = Maps.newHashMap();
    String str = table.getName().toLowerCase();
    // 首字母大写
    String className = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, str);
    model.put("className", className);
    String lowerName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, str);
    model.put("lowerName", lowerName);
    model.put("packageName", packageName);
    model.put("projectName", projectName);
    model.put("moduleName", moduleName);
    // 生成JPAService
    this.generateToFile(pathPostfix, null,
        GeneratorUtils.fileToObject(JPAFile.JPAservice.getPath(), Template.class), model, true);
    // 生成JPAServiceImpl
    this.generateToFile(pathPostfix, null,
        GeneratorUtils.fileToObject(JPAFile.JPAserviceImpl.getPath(), Template.class), model, true);
  }

  /**
   * 生成advice代码
   */
  @Override
  public void generateAdvice(String pathPostfix, String packageName, String projectName) {
    // 添加model
    Map<String, Object> model = Maps.newHashMap();
    model.put("packageName", packageName);
    model.put("projectName", projectName);
    // 生成advice文件
    this.generateToFile(pathPostfix, null,
        GeneratorUtils.fileToObject(AdviceFile.advice.getPath(), Template.class), model, true);
  }

  /**
   * 生成前台util
   */
  @Override
  public void generateUIFiles(String projectDescription, String projectName, String projectTitle,
      String appId) {
    String[] title = projectTitle.split("\\.");
    Map<String, Object> model = Maps.newHashMap();
    model.put("projectDescription", projectDescription);
    model.put("projectTitle", title[title.length - 1]);
    model.put("appId", appId);
    // 生成模板文件
    this.generateToUIFile(genProperties.getProjectUiTempPath().concat(projectName),
        GeneratorUtils.fileToObject(UiCode.uiUitl.getPath(), Template.class), model, true);
  }

}
