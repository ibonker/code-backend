package pers.fj.staffmanage.common;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.BaseRestStatus;

/**
 * 提供返回值和返回值说明的对应，请查看父类BaseRestStatus提供的三种基本返回值（成功、系统错误和数据不存在）
 * 自定义返回值建议以相同格式写在这里
 * @author wenxing
 *
 */
public class RestStatus extends BaseRestStatus {
  
  // 构造方法	
  public RestStatus(String code, String description, String message) {
    super(code, description, message);
  }

}