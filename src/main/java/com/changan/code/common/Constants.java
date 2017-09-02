/**
 * 
 */
package com.changan.code.common;

/**
 * 
 * @author wenxing
 *
 */
public class Constants {

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
  public final static String DATASOURCE_ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
  // api默认版本号
  public final static String API_BASE_DEFAULT_VERSION_NAME = "v1";
  // api默认版本号
  public final static String API_BASE_DEFAULT_DESCRIPTION = "由代码生成器自动生成";
  // DTO实体类默认包名
  public final static String TRANS_OBJ_DEFAULT_PACKAGE = "other";
  // Api默认包名
  public final static String API_DEFAULT_PREFIX = "";
  // api默认生成类型
  public final static String API_PRODUCES = "application/json";
  // api默认消费类型
  public final static String API_CONSUMES = "application/json";
  // mybatis配置文件根路径
  public final static String MYBATIS_GEN_CONFIG_ROOT = "configfiles";
}
