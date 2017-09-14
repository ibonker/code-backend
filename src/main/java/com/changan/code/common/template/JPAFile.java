package com.changan.code.common.template;

public enum JPAFile {
  JPAservice("mapper/JPAservice.xml"),
  
  JPAserviceImpl("mapper/JPAserviceImpl.xml"),
  
  repository("mapper/repository.xml");
  
  private String path;
  
  private JPAFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
}
