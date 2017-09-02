package com.changan.code.common.template;

public enum EntityFile {
  
  entity("mapper/entity.xml");
  
  private String path;
  
  private EntityFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
}
