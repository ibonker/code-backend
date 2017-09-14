/**
 * 
 */
package com.changan.code.service;

import java.util.List;
import java.util.Map;

import com.changan.code.dto.Template;
import com.changan.code.entity.ApiBasePO;
import com.changan.code.entity.ApiObjPO;
import com.changan.code.entity.ColumnPO;
import com.changan.code.entity.DatasourcePO;
import com.changan.code.entity.ProjectPO;
import com.changan.code.entity.TablePO;
import com.changan.code.entity.TransferObjFieldPO;
import com.changan.code.entity.TransferObjPO;

/**
 * @author wenxing
 *
 */
public interface IGenerateService {

  /**
   * 
   * @param tpl
   * @param model
   * @param isReplaceFile
   * @param subProjectName
   * @return
   */
  public String generateToFile(String basePath, Template tpl, Map<String, Object> model,
      boolean isReplaceFile);

  /**
   * 生成配置文件
   * 
   * @param datasources
   * @return
   */
  public void generateConfigFiles(ProjectPO project, List<DatasourcePO> datasources, String basepath);

  /**
   * 生成实体类
   * 
   * @param table
   * @param columns
   */
  public void generateEntityFiles(String moduleName, String projectName, String packageName,
      TablePO table, List<ColumnPO> columns);


  /**
   * 生成DTO文件
   * 
   * @param transferObj
   * @param tansferObjFileds
   */
  public void generateDTOFiles(String projectName, String packageName, TransferObjPO transferObj,
      List<TransferObjFieldPO> transferObjFileds);

  /**
   * 生成DAO文件
   * 
   * @param table
   * @param columns
   */
  public void generateDAOFiles(String projectName, String packageName, TablePO table);

  /**
   * 生成Iservice文件
   * 
   * @param projectName
   * @param packageName
   * @param ClassName
   */
  public void generateIServiceAndServiceImpl(String moduleName, String projectName,
      String packageName, TablePO table, String dtoPackageName);

  /**
   * 生成controller文件
   * 
   * @param project
   * @param apibase
   * @param apiobjs
   */
  public void generateControllerFiles(ProjectPO project, ApiBasePO apibase, String controllerName,
      List<ApiObjPO> apiobjs);

  /**
   * 数据库代码生成xml
   * 
   * @param projectName
   * @param packageName
   * @param datasource
   */
  public void generateGeneratorConfigFiles(String projectName, String packageName,
      DatasourcePO datasource, List<TablePO> tables);
  
  /**
   * 通过mybatis generator生成mybatis相关文件
   * @param projectName
   */
  public void generateMybatisFiles(String projectName);
  
  /**
   * 生成JPA Repository
   */
  public void generateRepository(String moduleName, String projectName, String packageName, String name);
  
  /**
   * 生成JPAService 和 JPAServiceImpl
   * @param moduleName
   * @param projectName
   * @param packageName
   * @param table
   * @param DTOPackageName
   */
  public void generateJPAServiceAndJPAServiceImpl(String module, String projectName,
      String packageName, TablePO table, String dtoPackageName);
  
  /**
   * 生成advice文件
   * @param packageName
   * @param module
   */
  public void generateAdvice(String packageName, String projectName);
}
