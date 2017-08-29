/**
 * 
 */
package com.changan.code.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changan.anywhere.common.utils.FileUtils;
import com.changan.anywhere.common.utils.FreeMarkerUtils;
import com.changan.anywhere.common.utils.StringUtils;
import com.changan.code.common.Constants;
import com.changan.code.common.ParamerConstant;
import com.changan.code.common.template.ConfigFile;
import com.changan.code.config.property.GenerateProperties;
import com.changan.code.dto.Template;
import com.changan.code.entity.DatasourcePO;
import com.changan.code.entity.ProjectPO;
import com.changan.code.service.IGenerateService;
import com.changan.code.utils.GeneratorUtils;
import com.google.common.collect.Maps;

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
  public String generateToFile(Template tpl, Map<String, Object> model, boolean isReplaceFile) {
    String fileName;
    fileName = GeneratorUtils.getProjectPath(genProperties.getProjectPath(),
        (String) model.get(ParamerConstant.CPNS_PROJECT_NAME))
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
  public void generateConfigFiles(ProjectPO project, List<DatasourcePO> datasources) {
    // 添加model
    Map<String, Object> model = Maps.newHashMap();
    model.put("packageName", project.getPackages().toLowerCase());
    model.put("datasourcelist", datasources);
    model.put("moduleName", "");
    model.put("projectName", project.getName());
    model.put("hasMysql", "0");
    model.put("hasOracle", "0");
    // 生成数据库配置文件
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
      this.generateToFile(dbTpl, model, true);
    }
    // 生成普通配置文件
    for (ConfigFile configFile : ConfigFile.values()) {
      if (!ConfigFile.datasourceConfig.equals(configFile)) {
        this.generateToFile(GeneratorUtils.fileToObject(configFile.getPath(), Template.class),
            model, true);
      }
    }
  }

}
