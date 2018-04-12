package org.hotpotmaterial.code.common.template;

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
