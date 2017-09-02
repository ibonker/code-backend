package com.changan.code.common.template;

public enum DAOFile {
  dao("mapper/dao.xml");
  
  private String path;
  
  private DAOFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
}
