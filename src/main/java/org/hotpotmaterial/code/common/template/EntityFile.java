package org.hotpotmaterial.code.common.template;

/**
 * 实体类文件
 * @author Administrator
 *
 */
public enum EntityFile {
  
  entity("service/entity.xml");
  
  private String path;
  
  private EntityFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
}
