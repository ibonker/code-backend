package org.hotpotmaterial.code.common.template;

public enum ServiceImplFile {
  ServiceImpl("service/serviceImpl.xml");
  
  private String path;
  
  private ServiceImplFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
}
