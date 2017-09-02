package com.changan.code.common.template;

public enum DTOFile {
  dto("mapper/dto.xml");
  
  private String path;
  
  private DTOFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
}
