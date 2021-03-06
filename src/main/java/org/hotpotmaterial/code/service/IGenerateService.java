/**
 * 
 */
package org.hotpotmaterial.code.service;

import java.util.List;
import java.util.Map;

import org.hotpotmaterial.code.dto.SeniorDtoAttribute;
import org.hotpotmaterial.code.dto.SeniorDtoRelation;
import org.hotpotmaterial.code.dto.Template;
import org.hotpotmaterial.code.entity.ApiBasePO;
import org.hotpotmaterial.code.entity.ApiObjPO;
import org.hotpotmaterial.code.entity.ColumnPO;
import org.hotpotmaterial.code.entity.DatasourcePO;
import org.hotpotmaterial.code.entity.ProjectPO;
import org.hotpotmaterial.code.entity.TablePO;
import org.hotpotmaterial.code.entity.TableRelationPO;
import org.hotpotmaterial.code.entity.TableSeniorRelationPO;
import org.hotpotmaterial.code.entity.TransferObjFieldPO;
import org.hotpotmaterial.code.entity.TransferObjPO;

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
  public String generateToFile(String bathpath, String pathPostfix, Template tpl,
      Map<String, Object> model, boolean isReplaceFile);

  /**
   * 生成配置文件
   * 
   * @param datasources
   * @return
   */
  public void generateConfigFiles(String pathPostfix, ProjectPO project,
      List<DatasourcePO> datasources, List<ApiBasePO> apiBases, ApiBasePO firstApiBase,
      List<String> components);

  /**
   * 生成实体类
   * 
   * @param table
   * @param columns
   */
  public void generateEntityFiles(String pathPostfix, String moduleName, String projectName,
      String packageName, TablePO table, List<ColumnPO> columns);


  /**
   * 生成DTO文件
   * 
   * @param transferObj
   * @param tansferObjFileds
   */
  public void generateDTOFiles(String pathPostfix, String projectName, String packageName,
      TransferObjPO transferObj, List<TransferObjFieldPO> transferObjFileds,
      List<SeniorDtoRelation> relations, String seniorName);

  /**
   * 生成DAO文件
   * 
   * @param table
   * @param columns
   */
  public void generateDAOFiles(String dbtype, String pathPostfix, String moduleName, String projectName,
      String packageName, String tableName, List<TableSeniorRelationPO> relations,
      List<SeniorDtoAttribute> attrs, List<SeniorDtoRelation> relationMethods);

  /**
   * 生成Iservice文件
   * 
   * @param projectName
   * @param packageName
   * @param ClassName
   */
  public void generateIServiceAndServiceImpl(String pathPostfix, String moduleName,
      String projectName, String packageName, String tableName, List<TableRelationPO> tableRelation,
      List<TableSeniorRelationPO> relations, boolean isSenior, List<String> components);

  /**
   * 生成controller文件
   * 
   * @param project
   * @param apibase
   * @param apiobjs
   */
  public void generateControllerFiles(String pathPostfix, ProjectPO project, ApiBasePO apibase,
      String controllerName, List<ApiObjPO> apiobjs);

  /**
   * 数据库代码生成xml
   * 
   * @param projectName
   * @param packageName
   * @param datasource
   */
  public void generateGeneratorConfigFiles(String pathPostfix, String projectName,
      String packageName, DatasourcePO datasource, List<TablePO> tables);

  /**
   * 通过mybatis generator生成mybatis相关文件
   * 
   * @param projectName
   */
  public void generateMybatisFiles(String pathPostfix, String projectName);

  /**
   * 生成JPA Repository
   */
  public void generateRepository(String pathPostfix, String moduleName, String projectName,
      String packageName, String name);

  /**
   * 生成JPAService 和 JPAServiceImpl
   * 
   * @param moduleName
   * @param projectName
   * @param packageName
   * @param table
   * @param DTOPackageName
   */
  public void generateJPAServiceAndJPAServiceImpl(String pathPostfix, String module,
      String projectName, String packageName, TablePO table, String dtoPackageName);

  /**
   * 生成advice文件
   * 
   * @param packageName
   * @param module
   */
  public void generateAdvice(String pathPostfix, String packageName, String projectName);

  /**
   * 生成前台文件util、index
   * 
   * @param projectName
   */
  public void generateUIFiles(String projectDescription, String projectName, String projecTitle,
      String appId, List<String> components);

  /**
   * 生成前台文件
   * 
   * @param basePath
   * @param tpl
   * @param model
   * @param isReplaceFile
   * @return
   */
  public String generateToUIFile(String basePath, Template tpl, Map<String, Object> model,
      boolean isReplaceFile);
}
