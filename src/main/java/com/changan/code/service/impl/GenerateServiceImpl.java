/**
 * 
 */
package com.changan.code.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changan.anywhere.common.utils.FileUtils;
import com.changan.anywhere.common.utils.FreeMarkerUtils;
import com.changan.anywhere.common.utils.StringUtils;
import com.changan.code.common.BaseDTO;
import com.changan.code.common.BaseFormat;
import com.changan.code.common.BaseParamIn;
import com.changan.code.common.BaseProfile;
import com.changan.code.common.BaseType;
import com.changan.code.common.Constants;
import com.changan.code.common.ParamerConstant;
import com.changan.code.common.template.AdviceFile;
import com.changan.code.common.template.ConfigFile;
import com.changan.code.common.template.DAOFile;
import com.changan.code.common.template.DTOFile;
import com.changan.code.common.template.EntityFile;
import com.changan.code.common.template.GeneratorConfigFile;
import com.changan.code.common.template.JPAFile;
import com.changan.code.common.template.MvcFile;
import com.changan.code.common.template.ServiceFile;
import com.changan.code.common.template.ServiceImplFile;
import com.changan.code.common.template.UiFile;
import com.changan.code.config.property.GenerateProperties;
import com.changan.code.dto.ApiServiceName;
import com.changan.code.dto.Template;
import com.changan.code.entity.ApiBasePO;
import com.changan.code.entity.ApiObjPO;
import com.changan.code.entity.ApiParamPO;
import com.changan.code.entity.ColumnPO;
import com.changan.code.entity.DatasourcePO;
import com.changan.code.entity.ProjectPO;
import com.changan.code.entity.TablePO;
import com.changan.code.entity.TransferObjFieldPO;
import com.changan.code.entity.TransferObjPO;
import com.changan.code.service.IGenerateService;
import com.changan.code.utils.GeneratorUtils;
import com.google.common.base.CaseFormat;
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
  public String generateToFile(String basePath, Template tpl, Map<String, Object> model,
      boolean isReplaceFile) {
    String fileName;
    fileName =
        GeneratorUtils.getProjectPath(null == basePath ? genProperties.getProjectPath() : basePath,
            (null == basePath ? (String) model.get(ParamerConstant.CPNS_PROJECT_NAME)
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
  public void generateConfigFiles(ProjectPO project, List<DatasourcePO> datasources, String basePath) {
    // 添加model
    Map<String, Object> model = Maps.newHashMap();
    model.put("packageName", project.getPackages().toLowerCase());
    model.put("mainpath", basePath.toLowerCase().split("/")[1]);
    model.put("projectDesc", StringUtils.trimToEmpty(project.getDescription()));
    model.put("datasourcelist", datasources);
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
      for (DatasourcePO datasource : datasources) {
        if (Constants.DATASOURCE_MYSQL.equals(datasource.getDbtype())) {
          model.put("hasMysql", "1");
        }
        if (Constants.DATASOURCE_ORACLE.equals(datasource.getDbtype())) {
          model.put("hasOracle", "1");
        }
        model.put("datasource", datasource);
        this.generateToFile(null, dbTpl, model, true);
      }
    }
    // 生成普通配置文件
    for (ConfigFile configFile : ConfigFile.values()) {
      if (!ConfigFile.datasourceConfig.equals(configFile)
          && !ConfigFile.applicationYml.equals(configFile)) {
        this.generateToFile(null, GeneratorUtils.fileToObject(configFile.getPath(), Template.class),
            model, true);
      }
    }
    // 生成yml配置文件
    for (BaseProfile profile : BaseProfile.values()) {
      model.put("postfix", profile.equals(BaseProfile.main) ? "" : "-".concat(profile.name()));
      this.generateToFile(null,
          GeneratorUtils.fileToObject(ConfigFile.applicationYml.getPath(), Template.class), model,
          true);
    }
    // 生成ui配置文件
    for (UiFile uiFile : UiFile.values()) {
      this.generateToFile(null, GeneratorUtils.fileToObject(uiFile.getPath(), Template.class),
          model, true);
    }
  }

  /**
   * 自动生成entity
   */
  @Override
  public void generateEntityFiles(String moduleName, String projectName, String packageName,
      TablePO table, List<ColumnPO> columns) {
    // 定义需要传入的包
    HashSet<String> imports = new HashSet<>();
    for (ColumnPO column : columns) {
      if("createdAt".equals(column.getJavaField()) || 
          "updatedAt".equals(column.getJavaField()) || "id".equals(column.getJavaField())){
        continue;
      }
      if ("1".equals(column.getReadOnly())) {
        imports.add("readOnly");
      }
      if ("1".equals(column.getIsNullable())) {
        imports.add("isNullable");
      }
      if (!"".equals(column.getPattern()) && column.getPattern() != null) {
        imports.add("pattern");
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
    // 添加model
    Map<String, Object> model = Maps.newHashMap();
    model.put("packageName", packageName);
    model.put("className", table.getClassName());
    model.put("columns", columns);
    model.put("projectName", projectName);
    model.put("moduleName", moduleName);
    model.put("imports", imports);
    // 生成实体文件
    this.generateToFile(null,
        GeneratorUtils.fileToObject(EntityFile.entity.getPath(), Template.class), model, true);

  }

  /**
   * 自动生成DTO
   */
  @Override
  public void generateDTOFiles(String projectName, String packageName, TransferObjPO transferObj,
      List<TransferObjFieldPO> transferObjFileds) {
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
      if (("array".equals(transferObjFiled.getType()) && "po".equals(transferObjFiled.getArrayType()))
          ||("po").equals(transferObjFiled.getType())) {
        typeImports.add("entity." + transferObjFiled.getFormat());
      }
      if (("array".equals(transferObjFiled.getType()) && "dto".equals(transferObjFiled.getArrayType()))
          ||("dto").equals(transferObjFiled.getType())) {
        typeImports.add("dto." + transferObjFiled.getFormat());
      }
      if ("base".equals(transferObjFiled.getType()) && "Date".equals(transferObjFiled.getFormat())) {
        imports.add("Date");
      }
      if ("base".equals(transferObjFiled.getType()) && "Timestamp".equals(transferObjFiled.getFormat())) {
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
    // 生成DTO文件
    this.generateToFile(null, GeneratorUtils.fileToObject(DTOFile.dto.getPath(), Template.class),
        model, true);
  }

  /**
   * 自动生成DAO
   */
  @Override
  public void generateDAOFiles(String projectName, String packageName, TablePO table) {
    // 添加model
    Map<String, Object> model = Maps.newHashMap();
    String str = table.getName();
    // 按下划线拆分
    String[] strs = str.split("_");
    // 首字母大写
    String className = strs[0].substring(0, 1).toUpperCase() + strs[0].substring(1)
        + strs[1].substring(0, 1).toUpperCase() + strs[1].substring(1);
    model.put("className", className);
    // 首字母小写驼峰
    String lowerName = strs[0] + strs[1].substring(0, 1).toUpperCase() + strs[1].substring(1);
    model.put("lowerName", lowerName);
    model.put("packageName", packageName);
    model.put("projectName", projectName);
    // 生成DAO文件
    this.generateToFile(null, GeneratorUtils.fileToObject(DAOFile.dao.getPath(), Template.class),
        model, true);
  }

  /**
   * 自定生成service
   */
  @Override
  public void generateIServiceAndServiceImpl(String moduleName, String projectName,
      String packageName, TablePO table, String DTOPackageName) {
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
    model.put("DTOPackageName", DTOPackageName);
    model.put("moduleName", moduleName);
    // 生成service文件
    this.generateToFile(null,
        GeneratorUtils.fileToObject(ServiceFile.Service.getPath(), Template.class), model, true);
    // 生成serviceImpl文件
    this.generateToFile(null,
        GeneratorUtils.fileToObject(ServiceImplFile.ServiceImpl.getPath(), Template.class), model,
        true);
  }

  /**
   * 生成controller的api和Implement
   */
  @Override
  public void generateControllerFiles(ProjectPO project, ApiBasePO apibase, String controllerName,
      List<ApiObjPO> apiobjs) {
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
        importIFPackSet.add(BaseParamIn.valueOf(apiparam.getForm().toUpperCase()).getAnnotationPack());
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
    this.generateToFile(null,
        GeneratorUtils.fileToObject(MvcFile.controllerApi.getPath(), Template.class), model, true);
    // 生成impl文件
    this.generateToFile(null,
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
  public void generateGeneratorConfigFiles(String projectName, String packageName,
      DatasourcePO datasource, List<TablePO> tables) {
    // 修改数据库url
    String url = datasource.getDburl().replace("&", "&amp;");
    datasource.setDburl(url);
    // 添加model
    Map<String, Object> model = Maps.newHashMap();
    model.put("projectName", projectName);
    model.put("packageName", packageName);
    model.put("dataSource", datasource);
    model.put("tables", tables);
    model.put("projectPath", genProperties.getProjectPath());
    // 生成xml文件
    this.generateToFile(genProperties.getMybatisGenlibsPath(), GeneratorUtils.fileToObject(
        GeneratorConfigFile.generatorConfigFile.getPath(), Template.class), model, true);
  }

  /**
   * 通过mybatis generator生成mybatis相关文件
   * 
   * @param projectName
   */
  @Override
  public void generateMybatisFiles(String projectName) {
    // 项目配置文件目录
    File projectConfigDir = new File(genProperties.getMybatisGenlibsPath()
        + Constants.MYBATIS_GEN_CONFIG_ROOT + File.separator + projectName);
    List<String> configfiles = FileUtils.findChildrenList(projectConfigDir, false);
    // 循环执行mybatis generator
    String os = System.getProperty("os.name");
    String cmd = "";
    String shFile = "";
    for (String filename : configfiles) {
      InputStream inputStream = null;
      String filepath = Constants.MYBATIS_GEN_CONFIG_ROOT + File.separator + projectName
          + File.separator + filename;
      try {
        if (os.toLowerCase().startsWith("win")) {
          shFile = Paths.get(genProperties.getMybatisGenlibsPath() + "rungen.bat").toAbsolutePath()
              .toString();
          cmd = "cmd /c start /b " + shFile + " " + filepath;
        } else {
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
  public void generateRepository(String module, String projectName, String packageName, String name) {
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
    this.generateToFile(null,
        GeneratorUtils.fileToObject(JPAFile.repository.getPath(), Template.class), model, true);
  }

  /**
   * 生成JPAService和JPAServiceImpl
   * 
   */
  @Override
  public void generateJPAServiceAndJPAServiceImpl(String moduleName, String projectName,
      String packageName, TablePO table, String DTOPackageName) {
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
    this.generateToFile(null,
        GeneratorUtils.fileToObject(JPAFile.JPAservice.getPath(), Template.class), model, true);
    // 生成JPAServiceImpl
    this.generateToFile(null,
        GeneratorUtils.fileToObject(JPAFile.JPAserviceImpl.getPath(), Template.class), model, true);
  }

  /**
   * 生成advice代码
   */
  @Override
  public void generateAdvice(String packageName, String projectName) {
    // 添加model
    Map<String, Object> model = Maps.newHashMap();
    model.put("packageName", packageName);
    model.put("projectName", projectName);
    // 生成advice文件
    this.generateToFile(null,
        GeneratorUtils.fileToObject(AdviceFile.advice.getPath(), Template.class), model, true);
  }

}
