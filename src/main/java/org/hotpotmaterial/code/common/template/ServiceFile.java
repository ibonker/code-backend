package org.hotpotmaterial.code.common.template;

/**
 * 接口文件
 * @author Administrator
 *
 */
public enum ServiceFile {
  Service("service/service.xml");
  
  private String path;
  
  private ServiceFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
}
