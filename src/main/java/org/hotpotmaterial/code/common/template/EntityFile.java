package org.hotpotmaterial.code.common.template;

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
