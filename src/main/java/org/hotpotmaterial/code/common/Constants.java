/**
 * 
 */
package org.hotpotmaterial.code.common;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author wenxing
 *
 */
public class Constants {
  
  /**
   * 构造方法
   */
  private Constants() {

  }

  // api成功编码
  public final static String SUCCESS_API_CODE = "200";
  // api异常编码
  public final static String EXCEPTION_API_CODE = "300";
  // 数据无效标识
  public final static String DATA_IS_INVALID = "1";
  // 数据有效标识
  public final static String DATA_IS_NORMAL = "0";
  // 数据无效标识
  public final static String IS_ACTIVE = "1";
  // 数据有效标识
  public final static String IS_INACTIVE = "0";
  // 后端版本信息标识
  public final static String CODE_VERSION = "1";
  // 前端版本信息标识
  public final static String UI_VERSION = "0";
  //描述内容分隔符
  public final static String DESCRIPTION_SPILT = "@";
  // 数据激活标识
  public final static String IS_CRUD = "1";
  // 数据未激活标识
  public final static String IS_NOT_CRUD = "0";
  // 默认时间格式
  public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  // 数据库测试
  public final static String DATASOURCE_CHECK_QUERY = "SELECT 1 from dual";
  // 数据库mysql类型
  public final static String DATASOURCE_MYSQL = "mysql";
  // 数据库oracle类型
  public final static String DATASOURCE_ORACLE = "oracle";
  // 数据库mysql驱动
  public final static String DATASOURCE_MYSQL_DRIVER = "com.mysql.jdbc.Driver";
  // 数据库oracle驱动
  public final static String DATASOURCE_ORACLE_DRIVER = "oracle.jdbc.OracleDriver";
  // api默认版本号
  public final static String API_BASE_DEFAULT_VERSION_NAME = "v1";
  // api默认版本号
  public final static String API_BASE_DEFAULT_DESCRIPTION = "由代码生成器自动生成";
  // DTO实体类默认包名
  public final static String TRANS_OBJ_DEFAULT_PACKAGE = "other";
  // Api默认包名
  public final static String API_DEFAULT_PREFIX = "";
  // 高级查询默认关联id标识
  public final static String API_SENIOR_TAG = "senior_crud_api";
  // 高级关联crud默认infix命名前缀
  public final static String API_SENIOR_RELATION_INFIX = "relation_";
  // api默认生成类型
  public final static String API_PRODUCES = "application/json";
  // api默认消费类型
  public final static String API_CONSUMES = "application/json";
  // Oracle链接串前缀
  public final static String JDBC_ORACLE_PREFIX = "jdbc:oracle:thin:@";
  // MySQL连接串前缀
  public final static String JDBC_MYSQL_PREFIX = "jdbc:mysql://";
  // Oracle链接串前缀
  public final static String JDBC_ORACLE_POSTFIX = "/";
  // MySQL连接串前缀
  public final static String JDBC_MYSQL_POSTFIX = "/";
  // MySQL连接串超后缀
  public final static String JDBC_MYSQL_POSTFIX_UTF8_ENCODING =
      "?useUnicode=true&characterEncoding=utf-8";
  // mybatis配置文件根路径
  public final static String MYBATIS_GEN_CONFIG_ROOT = "configfiles";
  // dictType和dictValue都存在
  public final static String Type_Value_Exit = "0";
  // dictType存在dictValue不存在
  public final static String Type_Exit = "1";
  // dictType不存在dictValue存在
  public final static String Value_Exit = "2";
  // dictType,dictValue都不存在
  public final static String Type_Value_Unexit = "3";
  // ui_config都存在
  public final static String Ui_Config_Exist = "4";
  // dictType表名
  public final static String Table_Dict_Type = "dict_type";
  // dictValue表名
  public final static String Table_Dict_Value = "dict_value";
  // uiconfig表名
  public final static String Table_UICONFIG = "ui_config";
  // Unknown
  public final static String Unknown = "unknown";
  // x-forwarded-for
  public final static String X_Forwarded_For = "x-forwarded-for";
  // Proxy-Client-IP
  public final static String Proxy_Client_IP = "Proxy-Client-IP";
  // WL-Proxy-Client-IP
  public final static String WL_Proxy_Client_IP = "WL-Proxy-Client-IP";
  
//  public final static String APP_ID = "fc613a5b5f1f42e6b092e5fc7699001a";
//  
//  public final static String APP_PUB_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxxHLlhvmOF6QcGMbyV4YvoMVpzCzXPRJdM2FNgS38CGaAxy23UixVE3pill7bywrAVM2eH0zF9Vx6mWO+kynPPbYdIA0cK4iM7ejf/735YhgeSih6+kBncy43tNVQWHCV+LYkyvV3NLjL6yTRRY3iYZTBCBZhD7fuLDmg3wXYJYrRq2GDOr6ZXVIwnqewXPWapHkXdfxKF+tG9NHQCoRqIF56bMUgZ1hMc7DsF7zSLD53a0DhoBrXMKa6833KjuY/h2hjMI/+K/1wnLSF8RQ1hh9Vj3iSl82EjYSRiQBO4+ofNY/h3tJOU5+ndOo7ReQCbKX/Ak65Vy0BAVlI/p70QIDAQAB";
//  
//  public final static String TEST_APP_ID = "8b376f64d21f474d856916ce0922fb35";
//  
//  public final static String TEST_PUB_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkcjv2jh4oL1cI43wtgcheiityUj6TD5+CjxSzfLpxj6h51HJKLrxXJgq5b0wJ8H4OMJQvLcKxRUQWGivGadQTUrE4J9gL36sZy9sB9FI7gROXPscUo1eXTPLCe7Ng3HgywWaZxE8wKPaUdhmFRtVTaxCcxCBOIrKvt0Rnc2N/iRYDEstzautvfzmf9ZK14/Cer2nYAcx4Go8FtqfbHH2sy47ZO/ACe8O8mCXkYkOoNW2YGipaBXgkG/StHEbv+46cCZi15LETF2PdIzxMDuFpzXaswBzz+1vjStF55Nb632ln+lOFIPRi6pKy+fiJ5aYfl9Hu8xx9vvQbzg0+S8irQIDAQAB";
  /**
   * 不显示的表
   * @return
   */
  public static List<String> getTableUnshown(){
    return Arrays.asList("ui_config", "dict_type",
        "dict_value", "hotpot_function", "hotpot_organization", "hotpot_role", "hotpot_role_function",
        "hotpot_role_user", "hotpot_user");
  }
  
  /**
   * 不创建实体的表
   * @return
   */
  public static List<String> getTableNoEntity(){
    return Arrays.asList("hotpot_function", "hotpot_organization", "hotpot_role", "hotpot_role_function",
        "hotpot_role_user", "hotpot_user");
  }
  
  /**
   * 不创建实体的表
   * @return
   */
  public static List<String> getResTableNoEntity(){
    return  Arrays.asList("hotpot_function", "hotpot_role", "hotpot_role_function",
        "hotpot_role_user");
  }
}
